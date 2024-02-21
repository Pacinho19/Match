package pl.pacinho.match.board.model;

import lombok.Getter;

@Getter
public class BoardCubeSimple {

    private String cube;

    public BoardCubeSimple(BoardCube boardCube) {
        this.cube = boardCube.getActiveCubeSideImage().name();
    }

    @Override
    public String toString() {
        return cube;
    }
}
