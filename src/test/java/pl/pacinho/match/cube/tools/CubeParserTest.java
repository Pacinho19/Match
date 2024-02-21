package pl.pacinho.match.cube.tools;

import org.junit.jupiter.api.Test;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CubeParserTest {

    private final String[] CUBE_SIDES_ARRAY = "SKY,MARSHALL,SKY_2,TRACKER,RYDER,CHASE".split(",");

    @Test
    void cubeShouldBeCreatedWhenGivenStringArrayIsCorrect() {
        //given

        //when
        Cube cube = CubeParser.parseCube(CUBE_SIDES_ARRAY);

        //then
        assertThat(cube, not(nullValue()));
    }

    @Test
    void createdCubeShouldHasCorrectSizeOfSides() {
        //given

        //when
        Cube cube = CubeParser.parseCube(CUBE_SIDES_ARRAY);

        //then
        assertThat(cube.getCubeSide().size(), equalTo(CubeSideType.values().length));
    }

    @Test
    void createdCubeShouldHasAllTypesOfSides() {
        //given

        //when
        Cube cube = CubeParser.parseCube(CUBE_SIDES_ARRAY);
        CubeSideType[] cubeSideTypes = cube.getCubeSide().stream().map(CubeSide::sideType).toArray(CubeSideType[]::new);

        //then
        assertArrayEquals(cubeSideTypes, CubeSideType.values());
        assertThat(cubeSideTypes, is(CubeSideType.values()));
    }

    @Test
    void createdCubeShouldHasCorrectCubeSideImages() {
        //given

        //when
        Cube cube = CubeParser.parseCube(CUBE_SIDES_ARRAY);
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