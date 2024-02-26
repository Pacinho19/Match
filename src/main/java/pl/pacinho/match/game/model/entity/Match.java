package pl.pacinho.match.game.model.entity;

import java.util.Collections;
import java.util.List;

public record Match(boolean isMatch, List<String> cells, int playerIndex) {

    public static Match emptyMatch(){
        return new Match(false, Collections.emptyList(), -1 );
    }
}
