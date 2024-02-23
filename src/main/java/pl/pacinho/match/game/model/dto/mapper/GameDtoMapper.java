package pl.pacinho.match.game.model.dto.mapper;

import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.board.model.GameBoard;
import pl.pacinho.match.board.tools.BoardCubeTransformation;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.entity.Game;
import pl.pacinho.match.game.model.entity.Player;

import java.util.*;

public class GameDtoMapper {
    public static GameDto parse(Game game, String name) {
        Integer playerIndex = getPlayerIndex(game.getPlayers(), name);
        return GameDto.builder()
                .id(game.getId())
                .players(getPlayersNames(game))
                .playerIndex(playerIndex)
                .status(game.getStatus())
                .actualPlayer(game.getActualPlayer())
                .startTime(game.getStartTime())
                .playerBoard(getPlayersBoard(game.getGameBoard(), playerIndex))
                .moveCube(Objects.equals(playerIndex, game.getActualPlayer()) ? game.getMoveCube() : null)
                .bonusImages(getBonusImagesForPlayer(game.getPlayers(), playerIndex))
                .build();
    }

    private static List<CubeSideImage> getBonusImagesForPlayer(LinkedList<Player> players, Integer playerIndex) {
        return players.stream()
                .filter(p -> Objects.equals(playerIndex, p.getIndex()))
                .findFirst()
                .map(Player::getBonusImages)
                .orElse(Collections.emptyList());
    }

    private static CubeSideImage[][] getPlayersBoard(GameBoard gameBoard, Integer playerIndex) {
        BoardCube[][] board = Objects.equals(playerIndex, 1)
                ? gameBoard.getBoard()
                : BoardCubeTransformation.getOppositeBoard(gameBoard.getBoard());

        return Arrays.stream(board)
                .map(GameDtoMapper::getCubeImageArray)
                .toArray(CubeSideImage[][]::new);
    }

    private static CubeSideImage[] getCubeImageArray(BoardCube[] boardCellArr) {
        return Arrays.stream(boardCellArr)
                .map(bc -> bc == null ? null : bc.getActiveCubeSideImage())
                .toArray(CubeSideImage[]::new);
    }

    private static List<String> getPlayersNames(Game game) {
        return game.getPlayers()
                .stream()
                .map(Player::getName)
                .toList();
    }

    private static Integer getPlayerIndex(LinkedList<Player> players, String name) {
        Optional<Player> playerOpt = players.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();

        if (playerOpt.isEmpty()) return null;
        return playerOpt.get()
                .getIndex();
    }
}
