package pl.pacinho.match.cube.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class Cube implements Serializable {

    private final List<CubeSide> cubeSide;

    public Map<CubeSideType, CubeSideImage> getCubeSideMap() {
        return cubeSide.stream()
                .collect(Collectors.toMap(CubeSide::sideType, CubeSide::cubeSideImage));
    }
}