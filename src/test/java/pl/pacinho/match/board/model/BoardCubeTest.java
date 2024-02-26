package pl.pacinho.match.board.model;

import org.junit.jupiter.api.Test;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardCubeTest {

    @Test
    void exceptionShouldBeThrownWhenCubeSidesListNotContainingActiveSide() {
        //given
        Cube cube = new Cube(List.of(new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST)));
        BoardCube boardCube = new BoardCube(cube, CubeSideType.FRONT);

        //then
        assertThrows(IllegalStateException.class, boardCube::getActiveCubeSideImage);
    }

    @Test
    void correctCubeSideImageShouldBeReturnedFromGetActiveCubeSide() {
        //given
        Cube cube = new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST)));
        BoardCube boardCube = new BoardCube(cube, CubeSideType.FRONT);

        //when
        CubeSideImage activeCubeSideImage = boardCube.getActiveCubeSideImage();

        //then
        assertThat(activeCubeSideImage, is(CubeSideImage.EVEREST));
    }

    @Test
    void exceptionShouldBeThrownWhenCubeSidesListNotContainingGivenSide() {
        //given
        Cube cube = new Cube(List.of(new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST)));
        BoardCube boardCube = new BoardCube(cube, CubeSideType.FRONT);

        //then
        assertThrows(IllegalStateException.class, () -> boardCube.getCubeSideImageBySide(CubeSideType.BOTTOM));
    }

    @Test
    void correctCubeSideImageShouldBeReturnedFromGetCubeSideImageBySide() {
        //given
        Cube cube = new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY)));
        BoardCube boardCube = new BoardCube(cube, CubeSideType.FRONT);

        //when
        CubeSideImage activeCubeSideImage = boardCube.getCubeSideImageBySide(CubeSideType.BACK);

        //then
        assertThat(activeCubeSideImage, is(CubeSideImage.ROCKY));
    }

    @Test
    void boardCubeSideImageNameShouldBeReturnedFromToStringSimple() {
        //given
        Cube cube = new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY)));
        BoardCube boardCube = new BoardCube(cube, CubeSideType.FRONT);

        //when
        String stringSimple = boardCube.toStringSimple();

        //then
        assertThat(stringSimple, equalTo(CubeSideImage.EVEREST.name()));
    }

}