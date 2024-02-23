package pl.pacinho.match.board.tools;

import pl.pacinho.match.board.model.BoardCube;

import java.util.Arrays;

public class BoardCubeTransformation {
    public static BoardCube[][] getOppositeBoard(BoardCube[][] board) {
        return Arrays.stream(board)
                .map(BoardCubeTransformation::getOppositeBoardRow)
                .toArray(BoardCube[][]::new);
    }

    private static BoardCube[] getOppositeBoardRow(BoardCube[] boardCubeArray) {
        return Arrays.stream(boardCubeArray)
                .map(boardCube -> boardCube == null
                        ? null
                        : new BoardCube(boardCube.cube(), boardCube.activeSide().getOppositeSide()))
                .toArray(BoardCube[]::new);
    }
}
