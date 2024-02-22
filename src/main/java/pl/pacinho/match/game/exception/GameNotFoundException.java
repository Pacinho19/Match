package pl.pacinho.match.game.exception;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String gameId) {
        super("Game " + gameId + " not found!");
    }
}