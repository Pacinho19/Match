package pl.pacinho.match.cube.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class CubeSideTypeTest {

    @Test
    void oppositeForFrontSideShouldBeBackSide(){
        //given
        CubeSideType front = CubeSideType.FRONT;

        //when
        CubeSideType opposite = front.getOppositeSide();

        //then
        assertThat(opposite, is(CubeSideType.BACK));
    }

    @Test
    void orderOfBottomSideShouldBeEqualTo5(){
        //given
        CubeSideType bottom = CubeSideType.BOTTOM;

        //when
        int order = bottom.getOrder();

        //then
        assertThat(order, equalTo(5));
    }

    @Test
    void getCubeSidesOrderedShouldReturnAscendingNumbers(){
        //given
        List<Integer> cubeSideTypesOrders = CubeSideType.getCubeSidesOrdered().stream().map(CubeSideType::getOrder).toList();
        List<Integer> ascendingNumbers = List.of(0,1,2,3,4,5);

        //then
        //when
        assertThat(cubeSideTypesOrders, is(ascendingNumbers));
    }

}