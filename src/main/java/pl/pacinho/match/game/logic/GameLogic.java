package pl.pacinho.match.game.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.match.game.exception.GameNotFoundException;
import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.entity.Game;
import pl.pacinho.match.game.model.entity.Player;
import pl.pacinho.match.game.model.enums.GameStatus;
import pl.pacinho.match.game.repository.GameRepository;


@RequiredArgsConstructor
@Service
public class GameLogic {

    private final GameRepository gameRepository;

    public boolean canJoin(GameDto game, String name) {
        return game.getPlayers().size() == 1
               && game.getPlayers().stream().noneMatch(p -> p.equals(name));

    }

    public void joinGame(String gameId, String playerName) {
        Game game = gameRepository.findById(gameId);
        if (game == null)
            throw new GameNotFoundException(gameId);

        if (game.getStatus() != GameStatus.NEW)
            throw new IllegalStateException("Cannot join to " + gameId + ". Game status : " + game.getStatus());

        if (game.getPlayers().get(0).getName().equals(playerName))
            throw new IllegalStateException("Game " + gameId + " was created by you!");

        Player player = new Player(playerName, game.getPlayers().size() + 1);
        game.getPlayers().add(player);
        game.setStatus(GameStatus.IN_PROGRESS);
    }
}
