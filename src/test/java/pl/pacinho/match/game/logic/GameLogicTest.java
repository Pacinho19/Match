package pl.pacinho.match.game.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pacinho.match.game.exception.GameNotFoundException;
import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.entity.Game;
import pl.pacinho.match.game.model.entity.Player;
import pl.pacinho.match.game.model.enums.GameStatus;
import pl.pacinho.match.game.repository.GameRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GameLogicTest {

    @InjectMocks
    private GameLogic gameLogic;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameDto gameDto;

    @Test
    void cannotJoinGameWhenPlayersSizeIsEqual2() {
        //given
        given(gameDto.getPlayers()).willReturn(List.of("1", "2"));

        //when
        boolean canJoin = gameLogic.canJoin(gameDto, "Test");

        //then
        assertThat(canJoin, is(false));
    }

    @Test
    void cannotJoinGameWhenPlayerAlreadyJoined() {
        //given
        given(gameDto.getPlayers()).willReturn(List.of("1"));

        //when
        boolean canJoin = gameLogic.canJoin(gameDto, "1");

        //then
        assertThat(canJoin, is(false));
    }

    @Test
    void canJoinGameWhenPlayersSizeIsEqual1() {
        //given
        given(gameDto.getPlayers()).willReturn(List.of("1"));

        //when
        boolean canJoin = gameLogic.canJoin(gameDto, "2");

        //then
        assertThat(canJoin, is(true));
    }

    @Test
    void gameNotFoundExceptionShouldBeThrownWhenGameIdNotExists() {
        //given
        given(gameRepository.findById(anyString())).willReturn(Optional.empty());

        //when
        //then
        assertThrows(GameNotFoundException.class, () -> gameLogic.joinGame("test-id", "1"));
    }

    @Test
    void illegalStateExceptionShouldBeThrownWhenGameStateIsDistinctFromNEW() {
        //given
        Game game = mock(Game.class);
        given(gameRepository.findById(anyString())).willReturn(Optional.of(game));
        given(game.getStatus()).willReturn(GameStatus.FINISHED);

        //when
        //then
        Exception exception = assertThrows(IllegalStateException.class, () -> gameLogic.joinGame("test-id", "1"));
        assertThat(exception.getMessage(), containsString("Cannot join to"));
        assertThat(exception.getMessage(), containsString("Game status :"));
    }

    @Test
    void illegalStateExceptionShouldBeThrownWhenFirstPlayerFromListEqualToJoiningPlayer() {
        //given
        Game game = new Game("1");
        given(gameRepository.findById(anyString())).willReturn(Optional.of(game));

        //when
        //then
        Exception exception = assertThrows(IllegalStateException.class, () -> gameLogic.joinGame("test-id", "1"));
        assertThat(exception.getMessage(), containsString("was created by you!"));
    }

    @Test
    void playerShouldBeAddedToPlayersListWhenPlayerCanJoinToGame() {
        //given
        Game game = new Game("1");
        given(gameRepository.findById(anyString())).willReturn(Optional.of(game));

        //when
        gameLogic.joinGame("test-id", "2");

        //then
        LinkedList<Player> expectedPlayers = new LinkedList<>(List.of(new Player("1", 1), new Player("2", 2)));
        assertThat(game.getPlayers(), hasSize(2));
        assertThat(game.getPlayers(), is(expectedPlayers));
        assertThat(game.getStatus(), is(GameStatus.IN_PROGRESS));
    }

    @Test
    void illegalStateExceptionShouldBeThrownWhenGameStatusIsFinished() {
        //given
        GameDto gameDto = GameDto.builder()
                .status(GameStatus.FINISHED)
                .build();

        //when
        //then
        Exception exception = assertThrows(IllegalStateException.class, () -> gameLogic.checkOpenGamePage(gameDto, null));
        assertThat(exception.getMessage(), containsString("finished!"));
    }

    @Test
    void illegalStateExceptionShouldBeThrownWhenGameStatusIsNew() {
        //given
        GameDto gameDto = GameDto.builder()
                .status(GameStatus.NEW)
                .build();

        //when
        //then
        Exception exception = assertThrows(IllegalStateException.class, () -> gameLogic.checkOpenGamePage(gameDto, null));
        assertThat(exception.getMessage(), containsString("has not started!"));
    }

    @Test
    void illegalStateExceptionShouldBeThrownWhenGameInProgressAndGamePlayersNotContainingGivenPlayer() {
        //given
        GameDto gameDto = getInProgressGameWithTwoPlayers();

        //when
        //then
        Exception exception = assertThrows(IllegalStateException.class, () -> gameLogic.checkOpenGamePage(gameDto, "3"));
        assertThat(exception.getMessage(), containsString("You can't open game page!"));
    }

    @Test
    void canOpenGamePageShouldBeReturnedTrueWhenGameIsInProgressAndGamePlayersContainingGivenPlayer() {
        //given
        GameDto gameDto = getInProgressGameWithTwoPlayers();

        //when
        boolean canOpenGamePage = gameLogic.checkOpenGamePage(gameDto, "1");

        //then
        assertThat(canOpenGamePage, is(true));
    }

    @Test
    void playerShouldBeActiveInGameWhenGamePlayersContainingGivenPlayer() {
        //given
        GameDto gameDto = getInProgressGameWithTwoPlayers();

        //when
        boolean isActivePlayer = gameLogic.isPlayerActiveInGame("1", gameDto);

        //then
        assertThat(isActivePlayer, is(true));
    }

    @Test
    void playerShouldBeNotActiveInGameWhenGamePlayersNotContainingGivenPlayer() {
        //given
        GameDto gameDto = getInProgressGameWithTwoPlayers();

        //when
        boolean isActivePlayer = gameLogic.isPlayerActiveInGame("3", gameDto);

        //then
        assertThat(isActivePlayer, is(false));
    }


    private static GameDto getInProgressGameWithTwoPlayers() {
        return GameDto.builder()
                .status(GameStatus.IN_PROGRESS)
                .players(List.of("1", "2"))
                .build();
    }


}