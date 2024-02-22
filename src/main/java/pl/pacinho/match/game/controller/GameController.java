package pl.pacinho.match.game.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pacinho.match.game.config.GameEndpointsConfig;
import pl.pacinho.match.game.logic.GameLogic;
import pl.pacinho.match.game.model.dto.GameDto;
import pl.pacinho.match.game.model.enums.GameStatus;
import pl.pacinho.match.game.service.GameService;
import pl.pacinho.match.home.config.HomeEndpointsConfig;

@RequiredArgsConstructor
@Controller
public class GameController {

    private final GameService gameService;
    private final GameLogic gameLogic;

    @PostMapping(GameEndpointsConfig.GAMES)
    public String availableGames(Model model) {
        model.addAttribute("games", gameService.getAvailableGames());
        return "fragments/available-games :: availableGamesFrag";
    }

    @PostMapping(GameEndpointsConfig.GAME_NEW)
    public String newGame(Authentication authentication,
                          RedirectAttributes redirectAttrs) {
        try {
            String gameId = gameService.newGame(authentication.getName());
            return "redirect:" + GameEndpointsConfig.GAMES + "/" + gameId + "/room";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/" + HomeEndpointsConfig.HOME;
        }
    }

    @GetMapping(GameEndpointsConfig.GAME_ROOM)
    public String gameRoom(@PathVariable(value = "gameId") String gameId,
                           Model model,
                           Authentication authentication,
                           RedirectAttributes redirectAttrs) {
        try {
            GameDto game = gameService.findDtoById(gameId, authentication.getName());
            if (game.getStatus() == GameStatus.IN_PROGRESS)
                return "redirect:" + GameEndpointsConfig.GAMES + "/" + gameId;
            if (game.getStatus() == GameStatus.FINISHED)
                throw new IllegalStateException("Game " + gameId + " finished!");

            model.addAttribute("game", game);
            model.addAttribute("joinGame", gameLogic.canJoin(game, authentication.getName()));
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/" + HomeEndpointsConfig.HOME;
        }
        return "game-room";
    }

    @PostMapping(GameEndpointsConfig.GAME_PLAYERS)
    public String players(@PathVariable(value = "gameId") String gameId,
                          Model model,
                          Authentication authentication) {
        GameDto game = gameService.findDtoById(gameId, authentication.getName());
        model.addAttribute("game", game);
        return "fragments/game-players :: gamePlayersFrag";
    }


}
