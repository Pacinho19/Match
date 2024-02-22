package pl.pacinho.match.game.repository;

import org.springframework.stereotype.Repository;
import pl.pacinho.match.game.model.entity.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GameRepository {

    private final Map<String, Game> games = new HashMap<>();

    public List<Game> getGames() {
        return games.values()
                .stream()
                .toList();
    }

    public String save(Game game) {
        games.put(game.getId(), game);
        return game.getId();
    }

    public Optional<Game> findById(String gameId) {
       return Optional.ofNullable(games.get(gameId));
    }
}
