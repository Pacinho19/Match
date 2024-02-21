package pl.pacinho.match.board.tools;

import org.junit.jupiter.api.Test;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class BoardValidatorTest {

    @Test
    void boardShouldBeNotValidWhenInOneRowIsMoreThanTwoTheSameCubeImage() {
        //given
        /**
         * MA MA MA
         * SK RU TR
         * RO EV RY
         */
        BoardCube[][] board = {
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.SKY))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RUBBLE))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.TRACKER))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ROCKY))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RYDER))), CubeSideType.FRONT)}
        };

        //when
        boolean isValid = BoardValidator.isValid(board);

        //then
        assertThat(isValid).isFalse();
    }

    @Test
    void boardShouldBeNotValidWhenInOneColumnIsMoreThanTwoTheSameCubeImage() {
        //given
        /**
         * MA SK RO
         * MA RU MA
         * MA EV RY
         */
        BoardCube[][] board = {
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.SKY))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ROCKY))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RUBBLE))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RYDER))), CubeSideType.FRONT)}
        };

        //when
        boolean isValid = BoardValidator.isValid(board);

        //then
        assertThat(isValid).isFalse();
    }

    @Test
    void boardShouldBeNotValidWhenIsEmpty() {
        //given
        BoardCube[][] board = {};

        //when
        boolean isValid = BoardValidator.isValid(board);

        //then
        assertThat(isValid).isFalse();

    }

    @Test
    void boardShouldBeValidWhenInEachRowAndColIsMaxTwoTheSameCubeImage() {
        //given
        /**
         * MA ZU MA
         * SK RU TR
         * RO EV RY
         */
        BoardCube[][] board = {
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ZUMA))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.SKY))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RUBBLE))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.TRACKER))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ROCKY))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST))), CubeSideType.FRONT), new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RYDER))), CubeSideType.FRONT)}
        };

        //when
        boolean isValid = BoardValidator.isValid(board);

        //then
        assertThat(isValid).isTrue();
    }
}