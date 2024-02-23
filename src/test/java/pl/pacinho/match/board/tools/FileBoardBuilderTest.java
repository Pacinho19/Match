package pl.pacinho.match.board.tools;

import org.junit.jupiter.api.Test;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.config.MatchConfiguration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class FileBoardBuilderTest {
    @Test
    void cubeBoardArrayShouldBeCreatedFromFile() throws IOException {
        //given
        MatchConfiguration matchConfiguration = mock(MatchConfiguration.class);

        MatchConfiguration.Cube cubeMock = mock(MatchConfiguration.Cube.class);
        MatchConfiguration.Board boardMock = mock(MatchConfiguration.Board.class);
        given(matchConfiguration.getCube()).willReturn(cubeMock);
        given(matchConfiguration.getBoard()).willReturn(boardMock);
        given(cubeMock.getFilePath()).willReturn("static/data/test_cubes_2.txt");
        given(boardMock.getSize()).willReturn(5);

        FileBoardBuilder fileBoardBuilder = new FileBoardBuilder(matchConfiguration);

        //when
        BoardCube[][] boardCubes = fileBoardBuilder.buildBoard();
        List<BoardCube> boardCubesList = Arrays.stream(boardCubes)
                .flatMap(Arrays::stream)
                .toList();
        //then
        assertThat(boardCubes, notNullValue());
        assertThat(boardCubes.length, is(5));
        assertThat(boardCubes[0].length, is(5));
        assertThat(boardCubesList, not(hasItem(nullValue())));
    }

    @Test
    void nullPointerExceptionShouldBeThrownWhenFileWithCubesNotExists() throws IOException {
        //given
        MatchConfiguration matchConfiguration = mock(MatchConfiguration.class);

        MatchConfiguration.Cube cubeMock = mock(MatchConfiguration.Cube.class);
        MatchConfiguration.Board boardMock = mock(MatchConfiguration.Board.class);
        given(matchConfiguration.getCube()).willReturn(cubeMock);
        given(matchConfiguration.getBoard()).willReturn(boardMock);
        given(cubeMock.getFilePath()).willReturn("static/data/fake_file.txt");
        given(boardMock.getSize()).willReturn(5);

        //when
        //then
        assertThrows(NullPointerException.class, () -> new FileBoardBuilder(matchConfiguration));
    }

}