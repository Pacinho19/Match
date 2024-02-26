package pl.pacinho.match.game.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.pacinho.match.cube.model.CubeSideImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return index == player.index && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, index);
    }
}
