package pl.pacinho.match.game.model.entity;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.match.game.model.enums.GameStatus;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
public class Game {

    private String id;
    private GameStatus status;
    private LinkedList<Player> players;
    private Integer actualPlayer;
    private LocalDateTime startTime;

    public Game(String player1) {
        players = new LinkedList<>();
        players.add(new Player(player1, 1));
        this.id = UUID.randomUUID().toString();
        this.status = GameStatus.NEW;
        this.startTime = LocalDateTime.now();
        this.actualPlayer = 1;
    }

}
