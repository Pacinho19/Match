package pl.pacinho.match.board.model;

import lombok.Setter;
import lombok.ToString;

@ToString
public class GameBoard {

    @Setter
    private BoardCube[][] board;
}
