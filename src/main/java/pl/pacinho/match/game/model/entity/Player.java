package pl.pacinho.match.game.model.entity;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Player {

    private final String name;
    private final int index;

    public Player(String name, int index) {
        this.name = name;
        this.index = index;
    }


}
