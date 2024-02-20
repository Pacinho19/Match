package pl.pacinho.match.cube.tools;

import lombok.RequiredArgsConstructor;
import pl.pacinho.match.utils.FileReader;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor
public class FileCubeReader implements CubeReader {

    private final FileReader fileReader;
    private final File file;

    @Override
    public List<String[]> getRawLines() {
        return fileReader.readTxt(file)
                .stream()
                .map(s -> s.split(","))
                .toList();
    }
}