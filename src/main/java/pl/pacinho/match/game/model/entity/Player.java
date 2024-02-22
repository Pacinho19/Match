package pl.pacinho.match.game.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Player {

    private final String name;
    private final int index;

    public Player(String name, int index) {
        this.name = name;
        this.index = index;
    }


}
