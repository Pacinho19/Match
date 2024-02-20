package pl.pacinho.match.cube.tools;

import org.junit.jupiter.api.Test;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CubeParserTest {

    @Test
    void cubeShouldBeCreatedWhenGivenStringArrayIsCorrect() {
        //given
        String[] array = "SKY,MARSHALL,SKY_2,TRACKER,RYDER,CHASE".split(",");

        //when
        Cube cube = CubeParser.parseCube(array);

        //then
        assertThat(cube, not(nullValue()));
    }

    @Test
    void createdCubeShouldHasCorrectSizeOfSides() {
        //given
        String[] array = "SKY,MARSHALL,SKY_2,TRACKER,RYDER,CHASE".split(",");

        //when
        Cube cube = CubeParser.parseCube(array);

        //then
        assertThat(cube.getCubeSide().size(), equalTo(CubeSideType.values().length));
    }

    @Test
    void createdCubeShouldHasAllTypesOfSides() {
        //given
        String[] array = "SKY,MARSHALL,SKY_2,TRACKER,RYDER,CHASE".split(",");

        //when
        Cube cube = CubeParser.parseCube(array);
        CubeSideType[] cubeSideTypes = cube.getCubeSide().stream().map(CubeSide::sideType).toArray(CubeSideType[]::new);

        //then
        assertArrayEquals(cubeSideTypes, CubeSideType.values());
        assertThat(cubeSideTypes, is(CubeSideType.values()));
    }

    @Test
    void createdCubeShouldHasCorrectCubeSideImages() {
        //given
        String[] array = "SKY,MARSHALL,SKY_2,TRACKER,RYDER,CHASE".split(",");

        //when
        Cube cube = CubeParser.parseCube(array);
        CubeSideImage[] cubeSideImages = cube.getCubeSide()
                .stream()
                .map(CubeSide::cubeSideImage)
                .toArray(CubeSideImage[]::new);

        //then
        CubeSideImage[] expectedImages = {
                CubeSideImage.SKY,
                CubeSideImage.MARSHALL,
                CubeSideImage.SKY_2,
                CubeSideImage.TRACKER,
                CubeSideImage.RYDER,
                CubeSideImage.CHASE
        };

        assertArrayEquals(cubeSideImages, expectedImages);
        assertThat(cubeSideImages, is(expectedImages));
    }

}