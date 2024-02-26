package pl.pacinho.match.game.tools;

import org.junit.jupiter.api.Test;
import pl.pacinho.match.game.model.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GamePlayerToolsTest {

    @Test
    void emptyOptionalShouldBeReturnedWhenListNotContainingPlayerWithGivenId() {
        //given
        List<Player> players = getTwoPlayersList();

        //when
        Optional<Player> opt = GamePlayerTools.getPlayerByIndex(players, 3);

        //then
        assertThat(opt.isEmpty(), is(true));
    }

    @Test
    void emptyOptionalShouldBeReturnedWhenGivenIdIsNull() {
        //given
        List<Player> players = getTwoPlayersList();

        //when
        Optional<Player> opt = GamePlayerTools.getPlayerByIndex(players, null);

        //then
        assertThat(opt.isEmpty(), is(true));
    }

    @Test
    void playerObjectShouldBeReturnedWhenListContainingPlayerWithGivenIndex() {
        //given
        Player player = new Player("1", 1);
        List<Player> players = List.of(player);

        //when
        Optional<Player> opt = GamePlayerTools.getPlayerByIndex(players, 1);

        //then
        assertThat(opt.isPresent(), is(true));
        assertThat(opt.get(), is(player));
        assertThat(opt.get(), sameInstance(player));
    }

    @Test
    void emptyListShouldBeReturnedWhenGivenPlayerListIsEmpty() {
        //given
        List<Player> players = Collections.emptyList();

        //when
        List<String> playersNames = GamePlayerTools.getPlayersNames(players);

        //then
        assertThat(playersNames, hasSize(0));
        assertThat(playersNames, empty());
    }

    @Test
    void listOfPlayerNamesShouldBeReturnedWhenListContainingAnyPlayers() {
        //given
        List<Player> players = getTwoPlayersList();

        //when
        List<String> playersNames = GamePlayerTools.getPlayersNames(players);

        //then
        assertThat(playersNames, hasSize(2));
        assertThat(playersNames, containsInAnyOrder("2", "1"));
    }

    @Test
    void nullPointerExceptionShouldBeThrownWhenListContainingPlayerWithNullName() {
        //given
        Player player = new Player(null, 1);
        List<Player> players = List.of(player);

        //when
        //then
        assertThrows(NullPointerException.class, () -> GamePlayerTools.getPlayerIndex(players, "1"));
    }

    @Test
    void nullPlayerIndexShouldBeReturnedWhenListNotContainingPlayerWithGivenName() {
        //given
        List<Player> players = getTwoPlayersList();

        //when
        Integer playerIndex = GamePlayerTools.getPlayerIndex(players, "3");

        //then
        assertThat(playerIndex, nullValue());
    }

    @Test
    void playerIndexShouldBeReturnedWhenListContainingPlayerWithGivenName() {
        //given
        List<Player> players = getTwoPlayersList();

        //when
        Integer playerIndex = GamePlayerTools.getPlayerIndex(players, "2");

        //then
        assertThat(playerIndex, not(nullValue()));
        assertThat(playerIndex, equalTo(2));
    }

    @Test
    void illegalStateExceptionShouldBeThrownWhenListNotContainingPlayerWithGivenIndex() {
        //given
        List<Player> players = Collections.emptyList();

        //when
        //then
        assertThrows(IllegalStateException.class, () -> GamePlayerTools.getOppositePlayerIndex(players, 3));
    }

    @Test
    void correctOppositePlayerIndexShouldBeReturnedWhenPlayerListContainingTwoPlayers(){
        //given
        List<Player> players = getTwoPlayersList();

        //when
        Integer oppositePlayerIndex = GamePlayerTools.getOppositePlayerIndex(players, 1);

        //then
        assertThat(oppositePlayerIndex, not(nullValue()));
        assertThat(oppositePlayerIndex, equalTo(2));
    }

    @Test
    void nextPlayerIndexShouldBeEqual2WhenGivenPlayerIndexEqualTo1(){
        //given
        int actualPlayerIndex = 1;

        //when
        Integer nextPlayer = GamePlayerTools.getNextPlayer(actualPlayerIndex);

        assertThat(nextPlayer, not(equalTo(actualPlayerIndex)));
        assertThat(nextPlayer, equalTo(2));
    }

    @Test
    void nextPlayerIndexShouldBeEqual1WhenGivenPlayerIndexEqualTo2(){
        //given
        int actualPlayerIndex = 2;

        //when
        Integer nextPlayer = GamePlayerTools.getNextPlayer(actualPlayerIndex);

        assertThat(nextPlayer, not(equalTo(actualPlayerIndex)));
        assertThat(nextPlayer, equalTo(1));
    }

    private static List<Player> getTwoPlayersList() {
        Player player = new Player("1", 1);
        Player player2 = new Player("2", 2);
        return List.of(player, player2);
    }

}