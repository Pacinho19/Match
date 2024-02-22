package pl.pacinho.match.game.repository;

import org.springframework.stereotype.Repository;
import pl.pacinho.match.game.exception.GameNotFoundException;
import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.entity.Game;
import pl.pacinho.match.game.model.enums.GameStatus;
import pl.pacinho.match.game.model.dto.mapper.GameDtoMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GameRepository {

    private final Map<String, Game> games = new HashMap<>();

    public List<GameDto> getAvailableGames() {
        return games.values()
                .stream()
                .filter(game -> game.getStatus() != GameStatus.FINISHED)
                .map(g -> GameDtoMapper.parse(g, null))
                .toList();
    }

    public String newGame(String playerName) {
        Game game = new Game(playerName);
        games.put(game.getId(), game);
        return game.getId();
    }

    public Game findById(String gameId) {
        Game game = games.get(gameId);
        if (game == null)
            throw new GameNotFoundException(gameId);
        return game;
    }
}
