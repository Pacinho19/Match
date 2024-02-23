package pl.pacinho.match.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.pacinho.match.board.model.GameBoard;
import pl.pacinho.match.board.tools.BoardBuilder;
import pl.pacinho.match.board.tools.FileBoardBuilder;
import pl.pacinho.match.config.MatchConfiguration;
import pl.pacinho.match.cube.tools.FileCubeReader;
import pl.pacinho.match.game.exception.GameNotFoundException;
import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.dto.mapper.GameDtoMapper;
import pl.pacinho.match.game.model.entity.Game;
import pl.pacinho.match.game.model.enums.GameStatus;
import pl.pacinho.match.game.repository.GameRepository;
import pl.pacinho.match.utils.ClassicFileReader;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final MatchConfiguration matchConfiguration;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final FileBoardBuilder boardBuilder;

    public List<GameDto> getAvailableGames() {
        return gameRepository.getGames()
                .stream()
                .filter(game -> game.getStatus() != GameStatus.FINISHED)
                .map(g -> GameDtoMapper.parse(g, null))
                .toList();
    }

    public String newGame(String playerName) throws IOException {
        List<GameDto> activeGames = getAvailableGames();
        if (activeGames.size() >= matchConfiguration.getGame().getMaxActiveGames())
            throw new IllegalStateException("Cannot create new Game! Active game count : " + activeGames.size());

        Game game = new Game(playerName);
        game.setGameBoard(new GameBoard(boardBuilder.buildBoard()));
//        game.setMoveCube(game.getGameBoard().getBoard()[0][0].cube()); //TEST
        String gameId = gameRepository.save(game);

        simpMessagingTemplate.convertAndSend("/game-created", "");
        return gameId;
    }

    public GameDto findDtoById(String gameId, String name) {
        return gameRepository.findById(gameId)
                .map(game -> GameDtoMapper.parse(game, name))
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

}
