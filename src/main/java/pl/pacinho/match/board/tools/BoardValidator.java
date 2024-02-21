package pl.pacinho.match.board.tools;

import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.cube.model.CubeSideImage;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardValidator {

    public static boolean isValid(BoardCube[][] board) {
        if(board.length==0)
            return false;

        for (int i = 0; i < board.length; i++) {
            if (!checkDirectionIsValid(board[i]))
                return false;
        }

        for (int j = 0; j < board[0].length; j++) {
            if (!checkDirectionIsValid(getBoardColumn(board, j)))
                return false;
        }

        return true;
    }

    private static BoardCube[] getBoardColumn(BoardCube[][] board, int j) {
        return IntStream.range(0, board.length)
                .boxed()
                .map(row -> board[row][j])
                .toArray(BoardCube[]::new);
    }

    private static boolean checkDirectionIsValid(BoardCube[] cubes) {
        List<CubeSideImage> rowImages = Arrays.stream(cubes).map(BoardCube::getActiveCubeSideImage).toList();
        return rowImages.stream()
                .collect(Collectors.groupingBy(Function.identity()))
                .values()
                .stream()
                .noneMatch(images -> images.size() > 2);
    }
}
