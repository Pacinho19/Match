package pl.pacinho.match.cube.tools;

import pl.pacinho.match.cube.model.Cube;

import java.io.IOException;
import java.util.List;

interface CubeReader {

    List<String[]> getRawLines() throws IOException;

    default List<Cube> parseCubes() throws IOException {
        return getRawLines()
                .stream()
                .map(CubeParser::parseCube)
                .toList();
    }
}