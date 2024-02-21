package pl.pacinho.match.cube.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;
import pl.pacinho.match.utils.ClassicFileReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

class FileCubeReaderTest {

    private ClassicFileReader classicFileReader;
    private File inputFile;

    @BeforeEach
    void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        classicFileReader = new ClassicFileReader();
        inputFile = new File(Objects.requireNonNull(classLoader.getResource("static/data/test_cubes.txt")).getFile());
    }

    @Test
    void listOfCubesShouldBeLoadedFromFile() throws IOException {
        //given
        FileCubeReader fileCubeReader = new FileCubeReader(classicFileReader, inputFile);

        List<Cube> expectedCubes = List.of(
                new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.SKY), new CubeSide(CubeSideType.BACK, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.LEFT, CubeSideImage.SKY_2)
                        , new CubeSide(CubeSideType.RIGHT, CubeSideImage.TRACKER), new CubeSide(CubeSideType.TOP, CubeSideImage.RYDER), new CubeSide(CubeSideType.BOTTOM, CubeSideImage.CHASE))),

                new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.SKY), new CubeSide(CubeSideType.BACK, CubeSideImage.RUBBLE), new CubeSide(CubeSideType.LEFT, CubeSideImage.ZUMA)
                        , new CubeSide(CubeSideType.RIGHT, CubeSideImage.RYDER), new CubeSide(CubeSideType.TOP, CubeSideImage.SKY_2), new CubeSide(CubeSideType.BOTTOM, CubeSideImage.RUBBLE))),

                new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.CHASE_2), new CubeSide(CubeSideType.BACK, CubeSideImage.RUBBLE_2), new CubeSide(CubeSideType.LEFT, CubeSideImage.MARSHALL_2)
                        , new CubeSide(CubeSideType.RIGHT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.TOP, CubeSideImage.ZUMA), new CubeSide(CubeSideType.BOTTOM, CubeSideImage.RUBBLE)))
        );

        //when
        List<Cube> cubes = fileCubeReader.parseCubes();

        //then
        assertThat(cubes, not(empty()));
        assertThat(cubes, hasSize(3));
        assertThat(cubes, is(expectedCubes));
    }
}