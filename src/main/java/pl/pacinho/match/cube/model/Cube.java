package pl.pacinho.match.cube.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class Cube {

    private final List<CubeSide> cubeSide;
}