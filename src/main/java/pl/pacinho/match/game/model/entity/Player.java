package pl.pacinho.match.game.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.pacinho.match.cube.model.CubeSideImage;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Getter
public class Player {

    private final String name;
    @Getter
    private final int index;
    @Setter
    private List<CubeSideImage> bonusImages;

    public Player(String name, int index) {
        this.name = name;
        this.index = index;
        this.bonusImages = new ArrayList<>();
    }


}
