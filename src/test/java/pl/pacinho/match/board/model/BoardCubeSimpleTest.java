package pl.pacinho.match.board.model;

import org.junit.jupiter.api.Test;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class BoardCubeSimpleTest {

    @Test
    void boardCubeActiveSideImageNameShouldBeReturnedFromToString() {
        //given
        Cube cube = new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY)));
        BoardCube boardCube = new BoardCube(cube, CubeSideType.FRONT);

        //when
        BoardCubeSimple boardCubeSimple = new BoardCubeSimple(boardCube);

        //then
        assertThat(boardCubeSimple.toString(), equalTo(CubeSideImage.EVEREST.name()));
    }
}