package pl.pacinho.match.board.model;

import lombok.EqualsAndHashCode;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;

import java.io.Serializable;

@EqualsAndHashCode
public class BoardCube implements Serializable {

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

    public CubeSideImage getCubeSideImageBySide(CubeSideType side) {
        return cube.getCubeSide()
                .stream()
                .filter(cubeSide -> cubeSide.sideType() == side)
                .findFirst()
                .map(CubeSide::cubeSideImage)
                .orElseThrow(() -> new IllegalStateException("Cannot find cube side image by given side: " + side));
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
