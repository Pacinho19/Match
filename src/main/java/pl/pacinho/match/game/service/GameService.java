package pl.pacinho.match.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.pacinho.match.config.MatchConfiguration;
import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.dto.mapper.GameDtoMapper;
import pl.pacinho.match.game.repository.GameRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final MatchConfiguration matchConfiguration;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public List<GameDto> getAvailableGames() {
        return gameRepository.getAvailableGames();
    }

    public String newGame(String playerName) {
        List<GameDto> activeGames = getAvailableGames();
        if (activeGames.size() >= matchConfiguration.getGame().getMaxActiveGames())
            throw new IllegalStateException("Cannot create new Game! Active game count : " + activeGames.size());

        String gameId = gameRepository.newGame(playerName);

        simpMessagingTemplate.convertAndSend("/game-created", "");
        return gameId;
    }

    public GameDto findDtoById(String gameId, String name) {
        return GameDtoMapper.parse(gameRepository.findById(gameId), name);
    }

}
