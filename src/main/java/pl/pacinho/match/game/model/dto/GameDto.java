package pl.pacinho.match.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.pacinho.match.game.model.enums.GameStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GameDto {

    private String id;
    private GameStatus status;
    private List<String> players;
    private Integer playerIndex;
    private Integer actualPlayer;
    private LocalDateTime startTime;
}