package pl.pacinho.match.board.tools;

import pl.pacinho.match.board.model.BoardCube;

import java.util.Arrays;
import java.util.stream.IntStream;

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

    public static BoardCube[] getBoardColumn(BoardCube[][] board, int j) {
        return IntStream.range(0, board.length)
                .boxed()
                .map(row -> board[row][j])
                .toArray(BoardCube[]::new);
    }
}
