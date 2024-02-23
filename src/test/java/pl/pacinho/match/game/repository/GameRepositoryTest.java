package pl.pacinho.match.game.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pacinho.match.game.model.entity.Game;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class GameRepositoryTest {

    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameRepository = new GameRepository();
    }

    @Test
    void emptyListShouldBeReturnedFromGetGamesBeforeAddAnyGame() {
        //given

        //when
        List<Game> games = gameRepository.getGames();

        //then
        assertThat(games, empty());
    }

    @Test
    void gamesListSizeShouldBeEqualTo1WhenGameWasSaved() {
        //given
        int initialSize = gameRepository.getGames().size();
        gameRepository.save(new Game("1"));

        //when
        List<Game> games = gameRepository.getGames();

        //then
        assertThat(games, hasSize(initialSize + 1));
    }

    @Test
    void gameShouldBeAddedToGamesListAfterSaveGame() {
        //given
        Game game = new Game("1");

        //when
        gameRepository.save(game);
        List<Game> games = gameRepository.getGames();

        //then
        assertThat(games, hasItem(game));
    }

    @Test
    void emptyOptionalShouldBeReturnedWhenGameListNotContainingGameWithGivenId() {
        //given
        Game game = new Game("1");
        gameRepository.save(game);

        //when
        Optional<Game> gameOptional = gameRepository.findById("fake-id");

        //then
        assertThat(gameOptional, equalTo(Optional.empty()));
    }

    @Test
    void gameShouldBeReturnedWhenGameListContainingGameWithGivenId() {
        //given
        Game game = new Game("1");
        String gameId = gameRepository.save(game);

        //when
        Optional<Game> gameOptional = gameRepository.findById(gameId);

        //then
        assertThat(gameOptional.isPresent(), is(true));
        assertThat(gameOptional.get(), equalTo(game));
    }

}