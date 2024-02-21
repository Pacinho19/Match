package pl.pacinho.match.board.model;

import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSideType;

public record BoardCube(Cube cube, CubeSideType activeSide) {
}
