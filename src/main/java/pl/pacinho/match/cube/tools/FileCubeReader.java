package pl.pacinho.match.cube.tools;

import lombok.RequiredArgsConstructor;
import pl.pacinho.match.utils.FileReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class FileCubeReader implements CubeReader {

    private final FileReader fileReader;
    private final File file;

    @Override
    public List<String[]> getRawLines() throws IOException {
        return fileReader.readFile(file)
                .stream()
                .map(s -> s.split(","))
                .toList();
    }
}