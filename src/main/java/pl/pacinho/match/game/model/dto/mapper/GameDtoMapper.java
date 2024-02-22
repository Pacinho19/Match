package pl.pacinho.match.game.model.dto.mapper;

import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.entity.Game;
import pl.pacinho.match.game.model.entity.Player;

import java.util.LinkedList;
import java.util.Optional;

public class GameDtoMapper {
    public static GameDto parse(Game game, String name) {
        return GameDto.builder()
                .id(game.getId())
                .players(game.getPlayers().stream().map(Player::getName).toList())
                .playerIndex(
                        getPlayerIndex(game.getPlayers(), name)
                )
                .status(game.getStatus())
                .actualPlayer(game.getActualPlayer())
                .startTime(game.getStartTime())
                .build();
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
