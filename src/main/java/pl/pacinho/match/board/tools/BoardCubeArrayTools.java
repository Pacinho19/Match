package pl.pacinho.match.board.tools;

import org.apache.commons.lang3.SerializationUtils;
import pl.pacinho.match.board.model.BoardCube;

public class BoardCubeArrayTools {

    public static BoardCube[][] copyOf(BoardCube[][] array) {
        return SerializationUtils.clone(array);
    }
}
