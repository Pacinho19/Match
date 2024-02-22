package pl.pacinho.match.board.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.config.MatchConfiguration;
import pl.pacinho.match.cube.tools.FileCubeReader;
import pl.pacinho.match.utils.ClassicFileReader;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        MatchConfiguration matchConfiguration = mock(MatchConfiguration.class);
        MatchConfiguration.Board boardConfig = mock(MatchConfiguration.Board.class);

        given(matchConfiguration.getBoard()).willReturn(boardConfig);
        given(boardConfig.getSize()).willReturn(10);
        FileCubeReader fileCubeReader = new FileCubeReader(classicFileReader, inputFile);

        BoardBuilder boardBuilder = new BoardBuilder(matchConfiguration);

        //when
        //then
        assertThrows(IllegalStateException.class, () -> boardBuilder.buildBoard(fileCubeReader));
    }

    @Test
    void boardCubesArrayShouldBeCreatedWhenIsEnoughDifferentCubesForBoard3x3() throws IOException {
        //given
        MatchConfiguration matchConfiguration = mock(MatchConfiguration.class);
        MatchConfiguration.Board boardConfig = mock(MatchConfiguration.Board.class);

        given(matchConfiguration.getBoard()).willReturn(boardConfig);
        given(boardConfig.getSize()).willReturn(3);
        FileCubeReader fileCubeReader = new FileCubeReader(classicFileReader, inputFile);

        BoardBuilder boardBuilder = new BoardBuilder(matchConfiguration);

        //when
        BoardCube[][] boardCubes = boardBuilder.buildBoard(fileCubeReader);

        //then
        assertThat(boardCubes, notNullValue());
        assertThat(boardCubes.length, equalTo(boardConfig.getSize()));
        assertThat(boardCubes[0].length, equalTo(boardConfig.getSize()));
    }

    @Test
    void boardCubesArrayShouldBeCreatedWhenIsEnoughDifferentCubesForBoard5x5() throws IOException {
        //given
        MatchConfiguration matchConfiguration = mock(MatchConfiguration.class);
        MatchConfiguration.Board boardConfig = mock(MatchConfiguration.Board.class);

        given(matchConfiguration.getBoard()).willReturn(boardConfig);
        given(boardConfig.getSize()).willReturn(5);
        FileCubeReader fileCubeReader = new FileCubeReader(classicFileReader, inputFile);

        BoardBuilder boardBuilder = new BoardBuilder(matchConfiguration);

        //when
        BoardCube[][] boardCubes = boardBuilder.buildBoard(fileCubeReader);

        //then
        assertThat(boardCubes, notNullValue());
        assertThat(boardCubes.length, equalTo(boardConfig.getSize()));
        assertThat(boardCubes[0].length, equalTo(boardConfig.getSize()));
    }
}