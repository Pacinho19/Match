package pl.pacinho.match.cube.tools;

import pl.pacinho.match.cube.model.Cube;

import java.util.List;

public interface CubeReader {

    List<String[]> getRawLines();

    default List<Cube> parseCubes() {
        return getRawLines()
                .stream()
                .map(CubeParser::parseCube)
                .toList();
    }
}