package pl.pacinho.match.cube.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CubeSideTest {

    @Test
    void recordsWithTheSameParamsShouldBeEquals(){
        //given
        CubeSide cubeSide1 = new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL);
        CubeSide cubeSide2 = new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL);

        //when
        //then
        assertThat(cubeSide1, equalTo(cubeSide2));
        assertThat(cubeSide1, is(cubeSide2));
        assertThat(cubeSide1.hashCode(), equalTo(cubeSide2.hashCode()));
        assertThat(cubeSide1, not(sameInstance(cubeSide2)));
    }

}