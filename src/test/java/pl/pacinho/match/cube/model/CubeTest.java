package pl.pacinho.match.cube.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CubeTest {

    @Test
    void recordsWithTheSameParamsShouldBeEquals(){
        //given
        Cube cube1 = new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL)));
        Cube cube2 = new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL)));

        //when
        //then
        assertThat(cube1, equalTo(cube2));
        assertThat(cube1, is(cube2));
        assertThat(cube1.hashCode(), equalTo(cube2.hashCode()));
        assertThat(cube1, not(sameInstance(cube2)));
    }

}