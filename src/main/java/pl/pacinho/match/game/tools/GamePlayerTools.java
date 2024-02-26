package pl.pacinho.match.game.tools;

import pl.pacinho.match.game.model.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GamePlayerTools {
    public static Optional<Player> getPlayerByIndex(List<Player> players, Integer index) {
        return players.stream()
                .filter(p -> Objects.equals(p.getIndex(), index))
                .findFirst();
    }

    public static List<String> getPlayersNames(List<Player> players) {
        return players
                .stream()
                .map(Player::getName)
                .toList();
    }

    public static Integer getPlayerIndex(List<Player> players, String name) {
        Optional<Player> playerOpt = players.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();

        if (playerOpt.isEmpty()) return null;
        return playerOpt.get()
                .getIndex();
    }

    public static Integer getOppositePlayerIndex(List<Player> players, Integer index) {
        return players.stream()
                .filter(p -> p.getIndex() != index)
                .findFirst()
                .map(Player::getIndex)
                .orElseThrow(() -> new IllegalStateException("Opposite player with given player index" + index + " not found!"));
    }

    public static Integer getNextPlayer(Integer actualPlayer) {
        return actualPlayer == 1 ? 2 : 1;
    }
}
