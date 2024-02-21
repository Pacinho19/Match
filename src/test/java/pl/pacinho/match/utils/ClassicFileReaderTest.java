package pl.pacinho.match.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClassicFileReaderTest {

    private ClassicFileReader classicFileReader;
    private File inputFile;


    @BeforeEach
    void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        inputFile = new File(Objects.requireNonNull(classLoader.getResource("static/data/test_cubes.txt")).getFile());
        classicFileReader = new ClassicFileReader();
    }

    @Test
    void exceptionShouldBeThrownWhenFileNotExists() {
        //given
        File fakeFile = new File("fake_file.txt");

        //when
        //then
        assertThrows(IOException.class, () -> classicFileReader.readFile(fakeFile));
    }

    @Test
    void listOfStringsShouldBeReturnedWhenReadFile() throws IOException {
        //given

        //when
        List<String> lines = classicFileReader.readFile(inputFile);
        //then
        assertThat(lines, not(empty()));
        assertThat(lines, hasSize(3));
    }

}