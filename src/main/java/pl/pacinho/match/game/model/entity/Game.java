package pl.pacinho.match.game.model.entity;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.board.model.GameBoard;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.game.model.enums.GameStatus;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
public class Game {

    private String id;
    @Setter
    private GameStatus status;
    private LinkedList<Player> players;

    @Setter
    private Integer actualPlayer;
    private LocalDateTime startTime;
    @Setter
    private GameBoard gameBoard;
    @Setter
    private Cube moveCube;
    @Setter
    private String previousMove;
    @Setter
    private Match match;

    public Game(String player1) {
        players = new LinkedList<>();
        players.add(new Player(player1, 1));
        this.id = UUID.randomUUID().toString();
        this.status = GameStatus.NEW;
        this.startTime = LocalDateTime.now();
        this.actualPlayer = 1;
        this.gameBoard = new GameBoard(new BoardCube[][]{});
        this.previousMove = "";
        this.match = Match.emptyMatch();
    }

}
