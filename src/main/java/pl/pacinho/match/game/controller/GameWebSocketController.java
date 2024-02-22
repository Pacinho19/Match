package pl.pacinho.match.game.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import pl.pacinho.match.game.logic.GameLogic;
import pl.pacinho.match.game.model.dto.GameActionDto;
import pl.pacinho.match.game.model.dto.JoinGameDto;
import pl.pacinho.match.game.service.GameService;

@RequiredArgsConstructor
@Controller
public class GameWebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GameLogic gameLogic;

    @MessageMapping("/join")
    public void join(@Payload GameActionDto gameActionDto, Authentication authentication) {
        String exception = null;
        try {
            gameLogic.joinGame(gameActionDto.gameId(), authentication.getName());
        } catch (Exception ex) {
            exception = ex.getMessage();
        }
        simpMessagingTemplate.convertAndSend("/join/" + gameActionDto.gameId(),
                new JoinGameDto(authentication.getName(), true, exception));
    }

}