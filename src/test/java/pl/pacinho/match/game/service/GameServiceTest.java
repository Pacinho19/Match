package pl.pacinho.match.game.service;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.board.model.GameBoard;
import pl.pacinho.match.board.tools.FileBoardBuilder;
import pl.pacinho.match.config.MatchConfiguration;
import pl.pacinho.match.game.exception.GameNotFoundException;
import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.entity.Game;
import pl.pacinho.match.game.model.enums.GameStatus;
import pl.pacinho.match.game.repository.GameRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Mock
    private MatchConfiguration matchConfiguration;
    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @Mock
    private FileBoardBuilder fileBoardBuilder;

    @Test
    void availableGamesListShouldBeEmptyWhenAllGamesHasFinishedStatus() {
        //given
        List<Game> games = getGamesWithFinishedStatus();
        given(gameRepository.getGames()).willReturn(games);

        //when
        List<GameDto> availableGames = gameService.getAvailableGames();

        //then
        assertThat(availableGames, empty());
    }

    @Test
    void availableGamesListShouldHaveOneItemWhenNotAllGamesHasFinishedStatus() {
        //given
        List<Game> games = List.of(new Game("1"));
        given(gameRepository.getGames()).willReturn(games);

        //when
        List<GameDto> availableGames = gameService.getAvailableGames();

        //then
        assertThat(availableGames, hasSize(1));
    }

    @Test
    void illegalStateExceptionShouldBeThrownWhenActiveGamesCountIsGreaterThanLimit() {
        //given
        MatchConfiguration.Game gameConfig = mock(MatchConfiguration.Game.class);
        List<Game> games = List.of(new Game("1"));
        given(gameRepository.getGames()).willReturn(games);
        given(matchConfiguration.getGame()).willReturn(gameConfig);
        given(gameConfig.getMaxActiveGames()).willReturn(1);

        //when
        //then
        Exception exception = assertThrows(IllegalStateException.class, () -> gameService.newGame("1"));
        assertThat(exception.getMessage(), containsString("Cannot create new Game! Active game count"));

        verify(simpMessagingTemplate, never()).convertAndSend("/game-created", "");
        verify(gameRepository, never()).save(ArgumentMatchers.any(Game.class));
    }

    @Test
    void gameIdShouldBeReturnedWhenGameCreatedSuccessfully() throws IOException {
        //given
        MatchConfiguration.Game gameConfig = mock(MatchConfiguration.Game.class);
        given(matchConfiguration.getGame()).willReturn(gameConfig);
        given(gameConfig.getMaxActiveGames()).willReturn(1);
        given(gameRepository.getGames()).willReturn(Collections.emptyList());
        given(gameRepository.save(ArgumentMatchers.any(Game.class))).willReturn("test-id");
        given(fileBoardBuilder.buildBoard()).willReturn(new BoardCube[][]{});

        //when
        String gameId = gameService.newGame("1");
        //then
        assertThat(gameId, is("test-id"));
        verify(fileBoardBuilder, times(1)).buildBoard();
    }

    @Test
    void gameNotFoundExceptionShouldBeThrownWhenGivenGameIdNotExists() {
        //given
        given(gameRepository.findById(anyString())).willReturn(Optional.empty());
        String gameId = "test-id";

        //when
        //then
        Exception exception = assertThrows(GameNotFoundException.class, () -> gameService.findDtoById(gameId, null));
        assertThat(exception.getMessage(), containsString(gameId));
    }

    @Test
    void gameDtoShouldBeReturnedWhenGameExists() {
        //given
        Game game = new Game("1");
        given(gameRepository.findById(anyString())).willReturn(Optional.of(game));
        String gameId = "test-id";

        //when
        GameDto gameDto = gameService.findDtoById(gameId, null);

        //then
        assertThat(gameDto, notNullValue());
    }

    private List<Game> getGamesWithFinishedStatus() {
        List<Game> games = new ArrayList<>();
        games.add(new Game("1"));
        games.add(new Game("2"));
        games.add(new Game("3"));

        return games.stream()
                .peek(game -> game.setStatus(GameStatus.FINISHED))
                .toList();
    }

}