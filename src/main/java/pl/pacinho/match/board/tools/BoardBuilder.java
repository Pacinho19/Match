package pl.pacinho.match.board.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.config.GameConfiguration;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSideType;
import pl.pacinho.match.cube.tools.CubeReader;
import pl.pacinho.match.utils.RandomUtils;

import java.io.IOException;
import java.util.List;

@Service
public class BoardBuilder {

    private final GameConfiguration gameConfiguration;
    private final int BOARD_SIZE;

    public BoardBuilder(GameConfiguration gameConfiguration) {
        this.gameConfiguration = gameConfiguration;
        this.BOARD_SIZE = gameConfiguration.getBoard().getSize();
    }

    public BoardCube[][] buildBoard(CubeReader cubeReader) throws IOException, IllegalStateException {
        List<Cube> cubes = cubeReader.parseCubes();
        if (cubes.size() < BOARD_SIZE * BOARD_SIZE)
            throw new IllegalStateException("To few cubes to create board!");

        BoardCube[][] board;
        do {
            board = generateBoard(cubes);
        } while (!BoardValidator.isValid(board));

        return board;
    }

    private BoardCube[][] generateBoard(List<Cube> cubes) {
        BoardCube[][] board = new BoardCube[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                CubeSideType randomCubeSideType = CubeSideType.getRandom();
                Cube randomCube = getRandomCube(cubes);
                board[i][j] = new BoardCube(randomCube, randomCubeSideType);
            }
        }
        return board;
    }

    private Cube getRandomCube(List<Cube> cubes) {
        int index = cubes.size() == 1 ? 0 : RandomUtils.getRandom(0, cubes.size());
        Cube cube = cubes.get(index);
        cubes.remove(index);
        return cube;
    }
}
