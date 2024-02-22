package pl.pacinho.match.game.service;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import pl.pacinho.match.config.MatchConfiguration;
import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.entity.Game;
import pl.pacinho.match.game.model.enums.GameStatus;
import pl.pacinho.match.game.repository.GameRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    void availableGamesListShouldHasOneItemWhenNotAllGamesHasFinishedStatus() {
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
        List<Game> games = List.of(mock(Game.class));
        given(matchConfiguration.getGame()).willReturn(gameConfig);
        given(gameConfig.getMaxActiveGames()).willReturn(1);
        given(gameRepository.getGames()).willReturn(games);

        //when
        //then
        Exception exception = assertThrows(IllegalStateException.class, () -> gameService.newGame("1"));
        assertThat(exception.getMessage(), containsString("Cannot create new Game! Active game count"));

        verify(simpMessagingTemplate, never()).convertAndSend("/game-created", "");
        verify(gameRepository, never()).save(ArgumentMatchers.any(Game.class));
    }

    @Test
    void gameIdShouldBeReturnedWhenGameCreatedSuccessfully() {
        //given
        MatchConfiguration.Game gameConfig = mock(MatchConfiguration.Game.class);
        given(matchConfiguration.getGame()).willReturn(gameConfig);
        given(gameConfig.getMaxActiveGames()).willReturn(1);
        given(gameRepository.getGames()).willReturn(Collections.emptyList());
        given(gameRepository.save(ArgumentMatchers.any(Game.class))).willReturn("test-id");

        //when
        String gameId = gameService.newGame("1");
        //then
        assertThat(gameId, is("test-id"));
        verify(simpMessagingTemplate, times(1)).convertAndSend("/game-created", "");
    }

    @Test
    void gameNotFoundExceptionShouldBeThrownWhenGivenGameIdNotExists(){
        //TODO
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