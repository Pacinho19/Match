package pl.pacinho.match.board.tools;

import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.cube.model.CubeSideImage;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameBoardMatch {
    public static boolean isMatch(BoardCube[][] board) {

        boolean firstSideMatch = checkMatch(board);
        if (firstSideMatch)
            return true;

        return checkMatch(BoardCubeTransformation.getOppositeBoard(board));
    }

    private static boolean checkMatch(BoardCube[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (isDirectionMatch(board[i]))
                return true;
        }

        for (int j = 0; j < board[0].length; j++) {
            if (isDirectionMatch(BoardCubeTransformation.getBoardColumn(board, j)))
                return true;
        }

        return false;
    }

    private static boolean isDirectionMatch(BoardCube[] cubes) {
        List<CubeSideImage> rowImages = Arrays.stream(cubes)
                .filter(Objects::nonNull)
                .map(BoardCube::getActiveCubeSideImage).toList();

        return rowImages.stream()
                .collect(Collectors.groupingBy(Function.identity()))
                .values()
                .stream()
                .anyMatch(images -> images.size() == cubes.length);
    }
}
