package pl.pacinho.match.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pacinho.match.home.config.HomeEndpointsConfig;

@Controller
public class HomeController {

    @GetMapping
    String home() {
        return "redirect:/" + HomeEndpointsConfig.HOME;
    }

    @GetMapping(HomeEndpointsConfig.HOME)
    String home2() {
        return "home";
    }
}
