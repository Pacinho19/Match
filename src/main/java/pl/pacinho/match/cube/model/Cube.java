package pl.pacinho.match.cube.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Cube {

    private final List<CubeSide> cubeSide;
}