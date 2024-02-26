package pl.pacinho.match.board.tools;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

class BoardCubeArrayToolsTest {

    private BoardCube[][] array2d;

    @BeforeEach
    void setUp() {
        array2d = new BoardCube[][]{
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST))), CubeSideType.FRONT),new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.BACK, CubeSideImage.MARSHALL))), CubeSideType.BACK)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.LEFT, CubeSideImage.RUBBLE))), CubeSideType.LEFT),new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.TOP, CubeSideImage.SKY))), CubeSideType.TOP)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.RIGHT, CubeSideImage.ROCKY))), CubeSideType.RIGHT),new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.BOTTOM, CubeSideImage.RYDER))), CubeSideType.BOTTOM)}
        };
    }

    @Test
    void copyOf2dArrayShouldReturnedAnotherInstanceOfArray() {
        //given

        //when
        BoardCube[][] copyOf2dArray = SerializationUtils.clone(array2d);

        //then
        assertThat(array2d, not(sameInstance(copyOf2dArray)));
        assertThat(array2d[0][0], not(sameInstance(copyOf2dArray[0][0])));
        assertThat(array2d[0][0].cube(), not(sameInstance(copyOf2dArray[0][0].cube())));
    }

    @Test
    void copyOf2dArrayShouldReturnedTheSameValues() {
        //given

        //when
        BoardCube[][] copyOf2dArray = SerializationUtils.clone(array2d);

        //then
        assertThat(array2d, is(copyOf2dArray));
        assertThat(array2d[0][0], equalTo(copyOf2dArray[0][0]));
        assertThat(array2d[0][0].cube(), equalTo(copyOf2dArray[0][0].cube()));
    }
}