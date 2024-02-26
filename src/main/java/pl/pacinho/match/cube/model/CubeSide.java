package pl.pacinho.match.cube.model;

import java.io.Serializable;

public record CubeSide(CubeSideType sideType, CubeSideImage cubeSideImage) implements Serializable {
}