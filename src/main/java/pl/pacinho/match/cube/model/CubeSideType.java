package pl.pacinho.match.cube.model;

import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public enum CubeSideType {

    FRONT,
    BACK,
    LEFT,
    RIGHT,
    TOP,
    BOTTOM;

    @Getter
    private CubeSideType oppositeSide;
    @Getter
    private int order;

    static {
        createEnum(FRONT, BACK, 0);
        createEnum(BACK, FRONT, 1);
        createEnum(LEFT, RIGHT, 2);
        createEnum(RIGHT, LEFT, 3);
        createEnum(TOP, BOTTOM, 4);
        createEnum(BOTTOM, TOP, 5);
    }

    private static void createEnum(CubeSideType cubeSideType, CubeSideType oppositeSide, int order) {
        cubeSideType.oppositeSide = oppositeSide;
        cubeSideType.order = order;
    }

    public static List<CubeSideType> getCubeSidesOrdered() {
        return Stream.of(CubeSideType.values())
                .sorted(Comparator.comparing(CubeSideType::getOrder))
                .toList();
    }

    public static CubeSideType getCubeSideByOrder(int _order) {
        return Stream.of(CubeSideType.values())
                .filter(cubeSideType -> cubeSideType.order == _order)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("CubeSideType not found by given order: " + _order));
    }
}