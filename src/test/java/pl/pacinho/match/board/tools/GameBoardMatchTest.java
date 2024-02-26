package pl.pacinho.match.board.tools;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;
import pl.pacinho.match.game.model.entity.Match;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class GameBoardMatchTest {

    @Test
    void noMatchShouldBeReturnedWhenGameBoardNotContainingMatch() {
        //given
        BoardCube[][] board = getBoardWithoutMatch();

        //when
        Match match = GameBoardMatch.isMatch(board);

        //then
        assertThat(match, not(nullValue()));
        assertThat(match.isMatch(), is(false));
        assertThat(match.playerIndex(), equalTo(-1));
        assertThat(match.cells(), empty());
    }

    @ParameterizedTest
    @MethodSource("getBoardsWithMatch")
    void matchShouldBeReturnedWhenGameBoardContainingMatch(BoardCube[][] board) {
        //given

        //when
        Match match = GameBoardMatch.isMatch(board);

        //then
        assertThat(match, not(nullValue()));
        assertThat(match.isMatch(), is(true));
        assertThat(match.playerIndex(), equalTo(1));
        assertThat(match.cells(), hasSize(3));
    }

    @Test
    void matchShouldBeReturnedWhenGameBoardContainingMatchOnSecondSide() {
        //given
        BoardCube[][] board = getBoardWithRowMatchOnSecondSide();

        //when
        Match match = GameBoardMatch.isMatch(board);

        //then
        assertThat(match, not(nullValue()));
        assertThat(match.isMatch(), is(true));
        assertThat(match.playerIndex(), equalTo(2));
        assertThat(match.cells(), hasSize(3));
        assertThat(match.cells(), containsInAnyOrder("2,0", "2,1", "2,2"));
    }


    @Test
    void matchCellsLocationShouldBeReturnedWhenGameBoardContainingRowMatch() {
        //given
        BoardCube[][] board = getBoardWithRowMatch();

        //when
        Match match = GameBoardMatch.isMatch(board);

        //then
        assertThat(match, not(nullValue()));
        assertThat(match.isMatch(), is(true));
        assertThat(match.playerIndex(), equalTo(1));
        assertThat(match.cells(), containsInAnyOrder("0,0", "0,1", "0,2"));
    }

    private static Stream<Arguments> getBoardsWithMatch() {
        return Stream.of(
                Arguments.of((Object) getBoardWithRowMatch()),
                Arguments.of((Object) getBoardWithColumnMatch())
        );
    }


    private static BoardCube[][] getBoardWithoutMatch() {
        /** Board Side
         * MA ZU MA
         * SK RU TR
         * RO EV RY
         */

        /** Opposite Board Side
         * SK RY EV
         * RO EV MA
         * RU TR RO
         */

        return new BoardCube[][]{
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ZUMA), new CubeSide(CubeSideType.BACK, CubeSideImage.RYDER))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.SKY), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RUBBLE), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.TRACKER), new CubeSide(CubeSideType.BACK, CubeSideImage.MARSHALL))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ROCKY), new CubeSide(CubeSideType.BACK, CubeSideImage.RUBBLE))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST), new CubeSide(CubeSideType.BACK, CubeSideImage.TRACKER))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RYDER), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT)}
        };
    }

    private static BoardCube[][] getBoardWithColumnMatch() {
        /** Board Side
         * MA ZU MA
         * MA RU TR
         * MA EV RY
         */

        /** Opposite Board Side
         * SK RY EV
         * RO EV MA
         * RU TR RO
         */

        return new BoardCube[][]{
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ZUMA), new CubeSide(CubeSideType.BACK, CubeSideImage.RYDER))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RUBBLE), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.TRACKER), new CubeSide(CubeSideType.BACK, CubeSideImage.MARSHALL))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.RUBBLE))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST), new CubeSide(CubeSideType.BACK, CubeSideImage.TRACKER))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RYDER), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT)}
        };
    }


    private static BoardCube[][] getBoardWithRowMatch() {
        /** Board Side
         * MA MA MA
         * SK RU TR
         * RO EV RY
         */

        /** Opposite Board Side
         * SK RY EV
         * RO EV MA
         * RU TR RO
         */

        return new BoardCube[][]{
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY))), CubeSideType.FRONT),
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

    private static BoardCube[][] getBoardWithRowMatchOnSecondSide() {
        /** Board Side
         * MA ZU MA
         * SK RU TR
         * RO EV RY
         */

        /** Opposite Board Side
         * SK RY EV
         * RO EV MA
         * RO RO RO
         */

        return new BoardCube[][]{
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ZUMA), new CubeSide(CubeSideType.BACK, CubeSideImage.RYDER))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.SKY), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RUBBLE), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.TRACKER), new CubeSide(CubeSideType.BACK, CubeSideImage.MARSHALL))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ROCKY), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RYDER), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT)}
        };
    }

}