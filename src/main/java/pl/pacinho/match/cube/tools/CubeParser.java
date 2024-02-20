package pl.pacinho.match.cube.tools;

import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;

import java.util.List;
import java.util.stream.IntStream;

public class CubeParser {

    public static Cube parseCube(String[] values) {
        List<CubeSide> cubeSides = getCubeSides(values);
        return new Cube(cubeSides);
    }

    private static List<CubeSide> getCubeSides(String[] values) {
        return IntStream.range(0, CubeSideType.values().length)
                .boxed()
                .map(i -> new CubeSide(CubeSideType.getCubeSideByOrder(i), CubeSideImage.valueOf(values[i])))
                .toList();
    }
}