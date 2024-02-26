package pl.pacinho.match.board.tools;

import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.game.model.entity.Match;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameBoardMatch {

    public static Match isMatch(BoardCube[][] board) {
        List<String> cells = checkMatch(board);
        if (cells != null)
            return new Match(true, cells, 1);

        cells = checkMatch(BoardCubeTransformation.getOppositeBoard(board));
        if (cells != null)
            return new Match(true, cells, 2);

        return new Match(false, Collections.emptyList(), -1);
    }

    private static List<String> checkMatch(BoardCube[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (isDirectionMatch(board[i]))
                return getCellsByRow(i, board.length);
        }

        for (int j = 0; j < board[0].length; j++) {
            if (isDirectionMatch(BoardCubeTransformation.getBoardColumn(board, j)))
                return getCellsByColumn(j, board[0].length);
        }

        return null;
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

    private static List<String> getCellsByColumn(int col, int length) {
        return IntStream.range(0, length)
                .boxed()
                .map(row -> getPositionString(col, row))
                .toList();
    }

    private static List<String> getCellsByRow(int row, int length) {
        return IntStream.range(0, length)
                .boxed()
                .map(col -> getPositionString(col, row))
                .toList();
    }

    private static String getPositionString(int col, Integer row) {
        return row + "," + col;
    }
}
