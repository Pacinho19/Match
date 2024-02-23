package pl.pacinho.match.game.model.dto;

import pl.pacinho.match.cube.model.CubeSideType;

public record Move(int x, int y, CubeSideType cubeSideType)  {
}
