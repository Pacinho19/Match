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

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class BoardCubeTransformationTest {

    @ParameterizedTest
    @MethodSource("createBoardCubesArrayIndexes")
    void oppositeBoardShouldBeCorrectSideAfterTransformation(int row, int col){
        //given
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
        BoardCube[][] board = {
                { new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ZUMA), new CubeSide(CubeSideType.BACK, CubeSideImage.RYDER))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT)},
                { new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.SKY), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RUBBLE), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.TRACKER), new CubeSide(CubeSideType.BACK, CubeSideImage.MARSHALL))), CubeSideType.FRONT)},
                { new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ROCKY), new CubeSide(CubeSideType.BACK, CubeSideImage.RUBBLE))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST), new CubeSide(CubeSideType.BACK, CubeSideImage.TRACKER))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RYDER), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT)}
        };

        //when
        BoardCube[][] oppositeBoard = BoardCubeTransformation.getOppositeBoard(board);

        //then
        assertThat(board[row][col].activeSide().getOppositeSide(), is(oppositeBoard[row][col].activeSide()));
        assertThat(board[row][col].getCubeSideImageBySide(board[row][col].activeSide().getOppositeSide()), is(oppositeBoard[row][col].getActiveCubeSideImage()));
    }

    private static Stream<Arguments> createBoardCubesArrayIndexes() {
        return Stream.of(
                Arguments.of(0,0),
                Arguments.of(1,1),
                Arguments.of(2,2)
        );
    }
}