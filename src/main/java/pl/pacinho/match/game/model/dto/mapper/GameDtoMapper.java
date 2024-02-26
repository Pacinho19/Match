package pl.pacinho.match.game.model.dto.mapper;

import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.board.model.GameBoard;
import pl.pacinho.match.board.tools.BoardCubeTransformation;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.dto.MatchDto;
import pl.pacinho.match.game.model.dto.PlayerDto;
import pl.pacinho.match.game.model.entity.Game;
import pl.pacinho.match.game.model.entity.Match;
import pl.pacinho.match.game.model.entity.Player;
import pl.pacinho.match.game.tools.GamePlayerTools;

import java.util.*;

public class GameDtoMapper {
    public static GameDto parse(Game game, String name) {
        Integer playerIndex = GamePlayerTools.getPlayerIndex(game.getPlayers(), name);
        return GameDto.builder()
                .id(game.getId())
                .players(GamePlayerTools.getPlayersNames(game.getPlayers()))
                .playerIndex(playerIndex)
                .status(game.getStatus())
                .actualPlayer(game.getActualPlayer())
                .startTime(game.getStartTime())
                .playerBoard(getPlayersBoard(game.getGameBoard(), playerIndex))
                .moveCube(Objects.equals(playerIndex, game.getActualPlayer()) ? game.getMoveCube() : null)
                .bonusImages(getBonusImagesForPlayer(game.getPlayers(), playerIndex))
                .previousMove(game.getPreviousMove())
                .match(getMatch(game.getPlayers(), game.getMatch()))
                .oppositePlayerBoard(game.getMatch().isMatch() ? getPlayersBoard(game.getGameBoard(), GamePlayerTools.getOppositePlayerIndex(game.getPlayers(), playerIndex)) : null)
                .build();
    }

    private static MatchDto getMatch(LinkedList<Player> players, Match match) {
        PlayerDto playerDto = GamePlayerTools.getPlayerByIndex(players, match.playerIndex())
                .map(player -> new PlayerDto(player.getIndex(), player.getName()))
                .orElse(null);

        return new MatchDto(
                match.isMatch(),
                match.cells(),
                playerDto
        );
    }

    private static List<CubeSideImage> getBonusImagesForPlayer(LinkedList<Player> players, Integer playerIndex) {
        return GamePlayerTools.getPlayerByIndex(players, playerIndex)
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

}
