package pl.pacinho.match.game.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.board.tools.GameBoardMatch;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.game.exception.GameNotFoundException;
import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.dto.Move;
import pl.pacinho.match.game.model.entity.Game;
import pl.pacinho.match.game.model.entity.Match;
import pl.pacinho.match.game.model.entity.Player;
import pl.pacinho.match.game.model.enums.GameStatus;
import pl.pacinho.match.game.repository.GameRepository;
import pl.pacinho.match.game.tools.GamePlayerTools;
import pl.pacinho.match.utils.RandomUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;


@RequiredArgsConstructor
@Service
public class GameLogic {

    private final GameRepository gameRepository;

    public boolean canJoin(GameDto game, String name) {
        return game.getPlayers().size() == 1
               && game.getPlayers().stream().noneMatch(p -> p.equals(name));

    }

    public void joinGame(String gameId, String playerName) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));

        if (game.getStatus() != GameStatus.NEW)
            throw new IllegalStateException("Cannot join to " + gameId + ". Game status : " + game.getStatus());

        if (game.getPlayers().get(0).getName().equals(playerName))
            throw new IllegalStateException("Game " + gameId + " was created by you!");

        Player player = new Player(playerName, game.getPlayers().size() + 1);
        game.getPlayers().add(player);
        game.setStatus(GameStatus.IN_PROGRESS);
        setBonusImages(game.getPlayers());
    }

    public boolean checkOpenGamePage(GameDto gameDto, String playerName) {
        if (gameDto.getStatus() == GameStatus.FINISHED)
            throw new IllegalStateException("Game " + gameDto.getId() + " finished!");

        if (gameDto.getStatus() == GameStatus.NEW)
            throw new IllegalStateException("Game " + gameDto.getId() + " has not started!");

        if (!isPlayerActiveInGame(playerName, gameDto))
            throw new IllegalStateException("Game " + gameDto.getId() + " in progress! You can't open game page!");

        return true;
    }

    public boolean isPlayerActiveInGame(String name, GameDto game) {
        return game.getPlayers()
                .stream()
                .anyMatch(p -> p.equals(name));
    }

    public void move(String gameId, Move moveDto) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));

        if (game.getPreviousMove().equals(moveDto.x() + "," + moveDto.y()))
            throw new IllegalStateException("This cube was previous pushed!");

        BoardCube boardCube = game.getGameBoard().getBoard()[moveDto.y()][moveDto.x()];
        if (boardCube == null && game.getMoveCube() != null) {
            if (!GameBoardMatch.isMatchAfterPutCubeInEmptySlot(game.getGameBoard().getBoard(), game.getMoveCube(), moveDto, game.getActualPlayer()))
                throw new IllegalStateException("Cannot put the cube in empty slot! It is possible only when the move will completed match!");
        }

        if (moveDto.cubeSideType() != null) {
            game.getGameBoard().getBoard()[moveDto.y()][moveDto.x()] = new BoardCube(game.getMoveCube(), game.getActualPlayer() == 1 ? moveDto.cubeSideType() : moveDto.cubeSideType().getOppositeSide());
        } else {
            game.getGameBoard().getBoard()[moveDto.y()][moveDto.x()] = null;
        }

        game.setMoveCube(boardCube == null ? null : boardCube.cube());
        game.setActualPlayer(GamePlayerTools.getNextPlayer(game.getActualPlayer()));
        game.setPreviousMove(moveDto.x() + "," + moveDto.y());

        checkFinishGame(game);
    }

    private boolean checkFinishGame(Game game) {
        Match match = GameBoardMatch.isMatch(game.getGameBoard().getBoard());
        if (match.isMatch()) {
            game.setStatus(GameStatus.FINISHED);
        }
        game.setMatch(match);
        return match.isMatch();
    }


    private void setBonusImages(LinkedList<Player> players) {
        List<CubeSideImage> images = new ArrayList<>(Arrays.asList(CubeSideImage.values()));

        players.forEach(
                player -> IntStream.rangeClosed(1, 2)
                        .boxed()
                        .forEach(i -> {
                            int idx = RandomUtils.getRandom(0, images.size());
                            player.getBonusImages()
                                    .add(images.get(idx));
                            images.remove(idx);
                        })
        );
    }
}
