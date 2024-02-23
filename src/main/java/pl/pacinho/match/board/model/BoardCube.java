package pl.pacinho.match.board.model;

import lombok.EqualsAndHashCode;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;

@EqualsAndHashCode
public class BoardCube {

    private Cube cube;
    private CubeSideType activeSide;

    public BoardCube(Cube cube, CubeSideType activeSide) {
        this.cube = cube;
        this.activeSide = activeSide;
    }

    public CubeSideImage getActiveCubeSideImage() {
        return cube.getCubeSide()
                .stream()
                .filter(cubeSide -> cubeSide.sideType() == activeSide)
                .findFirst()
                .map(CubeSide::cubeSideImage)
                .orElseThrow(() -> new IllegalStateException("Cannot find active cube side image!"));
    }

    public CubeSideType activeSide() {
        return activeSide;
    }

    public Cube cube() {
        return cube;
    }

    public String toStringSimple() {
        return getActiveCubeSideImage().name();
    }

}
