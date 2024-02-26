package pl.pacinho.match.game.model.dto.mapper;

import org.junit.jupiter.api.Test;
import pl.pacinho.match.board.model.BoardCube;
import pl.pacinho.match.board.model.GameBoard;
import pl.pacinho.match.cube.model.Cube;
import pl.pacinho.match.cube.model.CubeSide;
import pl.pacinho.match.cube.model.CubeSideImage;
import pl.pacinho.match.cube.model.CubeSideType;
import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.entity.Game;
import pl.pacinho.match.game.model.entity.Match;
import pl.pacinho.match.game.model.entity.Player;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GameDtoMapperTest {

    @Test
    void gameDtoShouldHaveCorrectPlayerBoardArray() {
        //given
        Game game = new Game("1");
        game.setGameBoard(new GameBoard(getBoardWithoutMatch()));
        //when
        GameDto gameDto = GameDtoMapper.parse(game, "1");

        //then
        CubeSideImage[][] expectedPlayerBoard = {
                {CubeSideImage.ZUMA, CubeSideImage.MARSHALL, CubeSideImage.MARSHALL},
                {CubeSideImage.SKY, CubeSideImage.RUBBLE, CubeSideImage.TRACKER},
                {CubeSideImage.ROCKY, CubeSideImage.EVEREST, CubeSideImage.RYDER}
        };

        assertThat(gameDto.getPlayerBoard(), is(expectedPlayerBoard));
    }

    @Test
    void gameDtoShouldHaveCorrectOppositePlayerBoardArray() {
        //given
        Game game = new Game("1");
        game.setGameBoard(new GameBoard(getBoardWithoutMatch()));

        //when
        GameDto gameDto = GameDtoMapper.parse(game, null);

        //then
        CubeSideImage[][] expectedPlayerBoard = {
                {CubeSideImage.SKY, CubeSideImage.RYDER, CubeSideImage.EVEREST},
                {CubeSideImage.ROCKY, CubeSideImage.EVEREST, CubeSideImage.MARSHALL},
                {CubeSideImage.RUBBLE, CubeSideImage.TRACKER, CubeSideImage.ROCKY}
        };

        assertThat(gameDto.getPlayerBoard(), is(expectedPlayerBoard));
    }

    @Test
    void oppositePlayerBoardFiledShouldBeNullWhenNonMatch() {
        //given
        Game game = new Game("1");
        game.setGameBoard(new GameBoard(getBoardWithoutMatch()));

        //when
        GameDto gameDto = GameDtoMapper.parse(game, null);

        //then
        assertThat(gameDto.getOppositePlayerBoard(), nullValue());
    }

    @Test
    void oppositePlayerBoardFiledShouldHaveOppositePlayerBoardValueWhenMatch() {
        //given
        Game game = new Game("1");
        game.getPlayers().add(new Player("2", 2));

        game.setGameBoard(new GameBoard(getBoardWithoutMatch()));
        game.setMatch(new Match(true, Collections.emptyList(), 1));

        //when
        GameDto gameDto = GameDtoMapper.parse(game, "1");

        //then
        CubeSideImage[][] expectedPlayerBoard = {
                {CubeSideImage.SKY, CubeSideImage.RYDER, CubeSideImage.EVEREST},
                {CubeSideImage.ROCKY, CubeSideImage.EVEREST, CubeSideImage.MARSHALL},
                {CubeSideImage.RUBBLE, CubeSideImage.TRACKER, CubeSideImage.ROCKY}
        };

        assertThat(gameDto.getOppositePlayerBoard(), is(expectedPlayerBoard));
    }

    @Test
    void bonusImagesFieldShouldHaveCorrectValueByPlayer() {
        //given
        Game game = new Game("1");
        game.getPlayers().get(0).setBonusImages(List.of(CubeSideImage.MARSHALL, CubeSideImage.SKY));

        //when
        GameDto gameDto = GameDtoMapper.parse(game, "1");

        //then
        assertThat(gameDto.getBonusImages(), hasItems(CubeSideImage.MARSHALL, CubeSideImage.SKY));
    }

    private static BoardCube[][] getBoardWithoutMatch() {
        /** Board Side
         * ZU MA MA
         * SK RU TR
         * RO EV RY
         */

        /** Opposite Board Side
         * SK RY EV
         * RO EV MA
         * RU TR RO
         */

        return new BoardCube[][]{
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ZUMA), new CubeSide(CubeSideType.BACK, CubeSideImage.SKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.RYDER))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.MARSHALL), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.SKY), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RUBBLE), new CubeSide(CubeSideType.BACK, CubeSideImage.EVEREST))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.TRACKER), new CubeSide(CubeSideType.BACK, CubeSideImage.MARSHALL))), CubeSideType.FRONT)},
                {new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.ROCKY), new CubeSide(CubeSideType.BACK, CubeSideImage.RUBBLE))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.EVEREST), new CubeSide(CubeSideType.BACK, CubeSideImage.TRACKER))), CubeSideType.FRONT),
                        new BoardCube(new Cube(List.of(new CubeSide(CubeSideType.FRONT, CubeSideImage.RYDER), new CubeSide(CubeSideType.BACK, CubeSideImage.ROCKY))), CubeSideType.FRONT)}
        };
    }


}