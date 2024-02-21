package pl.pacinho.match.board.model;

import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;

public record BoardCube(Cube cube, CubeSideType activeSide) {
    public CubeSideImage getActiveCubeSideImage() {
        return cube.getCubeSide()
                .stream()
                .filter(cubeSide -> cubeSide.sideType() == activeSide)
                .findFirst()
                .map(CubeSide::cubeSideImage)
                .orElseThrow(() -> new IllegalStateException("Cannot find active cube side image!"));
    }
}
