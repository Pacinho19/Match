package pl.pacinho.match.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class GameBoard {

    @Getter
    @Setter
    private BoardCube[][] board;
}
