package pl.pacinho.match.game.model.dto;

import pl.pacinho.match.cube.model.CubeSideImage;

import java.util.List;

public record MatchDto(boolean isMatch, List<String> cells, PlayerDto player) {
}
