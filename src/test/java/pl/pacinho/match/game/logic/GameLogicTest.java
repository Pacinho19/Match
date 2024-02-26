package pl.pacinho.match.game.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.board.model.GameBoard;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;
import pl.pacinho.match.game.exception.GameNotFoundException;
import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.dto.Move;
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

    @Test
    void illegalStateExceptionShouldBeThrownWhenPreviousMoveCoordinatesIsEqualToGivenMoveCoordinates() {
        //given
        Game game = new Game("1");
        game.setPreviousMove("0,0");
        given(gameRepository.findById(anyString())).willReturn(Optional.of(game));

        //when
        //then
        assertThrows(IllegalStateException.class, () -> gameLogic.move("test-id", new Move(0, 0, null)));
    }

    @Test
    void selectedBoardCellShouldBeNullWhenMoveCubeIsNull() {
        //given
        Game game = new Game("1");
        game.setGameBoard(new GameBoard(getBoardWithoutMatch()));
        given(gameRepository.findById(anyString())).willReturn(Optional.of(game));

        //when
        gameLogic.move("test-id", new Move(0, 0, null));

        //then
        assertThat(game.getGameBoard().getBoard()[0][0], nullValue());
    }

    @Test
    void selectedBoardCellShouldBeReplacedWithMoveCube() {
        //given
        Cube cube = new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY)));

        Game game = new Game("1");
        game.setGameBoard(new GameBoard(getBoardWithoutMatch()));
        game.setMoveCube(cube);
        given(gameRepository.findById(anyString())).willReturn(Optional.of(game));

        //when
        gameLogic.move("test-id", new Move(0, 0, CubeSideType.FRONT));

        //then
        assertThat(game.getGameBoard().getBoard()[0][0].cube(), is(cube));
    }

    @Test
    void moveCubeShouldBeReplacedWithBoardCellWithGivenCoordinates() {
        //given
        Cube moveCube = new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY)));

        Game game = new Game("1");
        game.setGameBoard(new GameBoard(getBoardWithoutMatch()));
        game.setMoveCube(moveCube);
        given(gameRepository.findById(anyString())).willReturn(Optional.of(game));

        Cube cubeBeforeMove = game.getGameBoard().getBoard()[0][0].cube();

        //when
        gameLogic.move("test-id", new Move(0, 0, CubeSideType.FRONT));

        //then
        assertThat(game.getMoveCube(), is(cubeBeforeMove));
    }

    @Test
    void actualPlayerShouldBeChangedAfterMove() {
        //given
        Cube moveCube = new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY)));
        Game game = new Game("1");
        game.setGameBoard(new GameBoard(getBoardWithoutMatch()));
        game.setMoveCube(moveCube);
        given(gameRepository.findById(anyString())).willReturn(Optional.of(game));

        //when
        gameLogic.move("test-id", new Move(0, 0, CubeSideType.FRONT));

        //then
        assertThat(game.getActualPlayer(), equalTo(2));
    }

    @Test
    void previousMoveShouldBeUpdatedAfterMove() {
        //given
        String previousMoveBeforeMove = "1,0";

        Cube moveCube = new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY)));
        Game game = new Game("1");
        game.setGameBoard(new GameBoard(getBoardWithoutMatch()));
        game.setMoveCube(moveCube);
        game.setPreviousMove(previousMoveBeforeMove);
        given(gameRepository.findById(anyString())).willReturn(Optional.of(game));

        //when
        gameLogic.move("test-id", new Move(0, 0, CubeSideType.FRONT));

        //then
        assertThat(game.getPreviousMove(), equalTo("0,0"));
        assertThat(game.getPreviousMove(), not(equalTo(previousMoveBeforeMove)));
    }

    @Test
    void gameStatusShouldBeChangedToFinishedAfterMatch() {
        Cube moveCube = new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY)));
        Game game = new Game("1");
        game.setGameBoard(new GameBoard(getBoardWithoutMatch()));
        game.setMoveCube(moveCube);
        given(gameRepository.findById(anyString())).willReturn(Optional.of(game));

        //when
        gameLogic.move("test-id", new Move(0, 0, CubeSideType.FRONT));

        //then
        assertThat(game.getStatus(), equalTo(GameStatus.FINISHED));
    }

    @Test
    void resultMatchObjectShouldHaveWinningPropertiesAfterMatch() {
        Cube moveCube = new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY)));
        Game game = new Game("1");
        game.setGameBoard(new GameBoard(getBoardWithoutMatch()));
        game.setMoveCube(moveCube);
        given(gameRepository.findById(anyString())).willReturn(Optional.of(game));

        //when
        gameLogic.move("test-id", new Move(0, 0, CubeSideType.FRONT));

        //then
        assertThat(game.getMatch(), not(nullValue()));
        assertThat(game.getMatch().isMatch(), is(true));
        assertThat(game.getMatch().playerIndex(), equalTo(1));
        assertThat(game.getMatch().cells(), containsInAnyOrder("0,0", "0,1", "0,2"));
    }

    @Test
    void illegalStateExceptionShouldBeThrownWhenPlayerWantsToPutCubeInEmptyCellWhenItNotCompletedMatch(){
        Cube moveCube = new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ROCKY), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY)));
        Game game = new Game("1");
        game.setGameBoard(new GameBoard(getBoardWitEmptyCell()));
        game.setMoveCube(moveCube);
        given(gameRepository.findById(anyString())).willReturn(Optional.of(game));

        //when
        //then
        assertThrows(IllegalStateException.class, () ->   gameLogic.move("test-id", new Move(0, 0, CubeSideType.FRONT)));
    }


    private static GameDto getInProgressGameWithTwoPlayers() {
        return GameDto.builder()
                .status(GameStatus.IN_PROGRESS)
                .players(List.of("1", "2"))
                .build();
    }

    private static BoardCube[][] getBoardWithoutMatch() {
        /** Board Side
         * ZU MA MA
         * SK RU TR
         * RO EV RY
         */

        /** Opposite Board Side
         * SK RY EV
         * RO EV MA
         * RU TR RO
         */

        return new BoardCube[][]{
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ZUMA), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.RYDER))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.SKY), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RUBBLE), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.TRACKER), new CubeSide(CubeSideType.BACK, CubeSideImage.MARSHALL))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ROCKY), new CubeSide(CubeSideType.BACK, CubeSideImage.RUBBLE))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST), new CubeSide(CubeSideType.BACK, CubeSideImage.TRACKER))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RYDER), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT)}
        };
    }

    private static BoardCube[][] getBoardWitEmptyCell() {
        /** Board Side
         * NULL MA MA
         * SK RU TR
         * RO EV RY
         */

        /** Opposite Board Side
         * NULL RY EV
         * RO EV MA
         * RU TR RO
         */

        return new BoardCube[][]{
                {null,
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.RYDER))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.SKY), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RUBBLE), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.TRACKER), new CubeSide(CubeSideType.BACK, CubeSideImage.MARSHALL))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ROCKY), new CubeSide(CubeSideType.BACK, CubeSideImage.RUBBLE))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST), new CubeSide(CubeSideType.BACK, CubeSideImage.TRACKER))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RYDER), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT)}
        };
    }


}