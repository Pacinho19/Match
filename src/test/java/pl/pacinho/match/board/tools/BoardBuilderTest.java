package pl.pacinho.match.board.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.config.GameConfiguration;
import pl.pacinho.match.cube.tools.FileCubeReader;
import pl.pacinho.match.utils.ClassicFileReader;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BoardBuilderTest {


    private ClassicFileReader classicFileReader;
    private File inputFile;

    @BeforeEach
    void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        classicFileReader = new ClassicFileReader();
        inputFile = new File(Objects.requireNonNull(classLoader.getResource("static/data/test_cubes_2.txt")).getFile());
    }

    @Test
    void exceptionShouldBeThrownWhenCountOfAvailableCubesIsLessThanBoardSize() {
        //given
        GameConfiguration gameConfiguration = mock(GameConfiguration.class);
        GameConfiguration.Board boardConfig = mock(GameConfiguration.Board.class);

        given(gameConfiguration.getBoard()).willReturn(boardConfig);
        given(boardConfig.getSize()).willReturn(10);
        FileCubeReader fileCubeReader = new FileCubeReader(classicFileReader, inputFile);

        BoardBuilder boardBuilder = new BoardBuilder(gameConfiguration);

        //when
        //then
        assertThrows(IllegalStateException.class, () -> boardBuilder.buildBoard(fileCubeReader));
    }

    @Test
    void boarCubesArrayShouldBeCreatedWhenIsEnoughDifferentCubes() throws IOException {
        //given
        GameConfiguration gameConfiguration = mock(GameConfiguration.class);
        GameConfiguration.Board boardConfig = mock(GameConfiguration.Board.class);

        given(gameConfiguration.getBoard()).willReturn(boardConfig);
        given(boardConfig.getSize()).willReturn(3);
        FileCubeReader fileCubeReader = new FileCubeReader(classicFileReader, inputFile);

        BoardBuilder boardBuilder = new BoardBuilder(gameConfiguration);

        //when
        BoardCube[][] boardCubes = boardBuilder.buildBoard(fileCubeReader);

        //then
        assertThat(boardCubes, notNullValue());
    }
}