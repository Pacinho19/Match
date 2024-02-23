package pl.pacinho.match.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "match")
public class MatchConfiguration {

    private Board board;
    private Game game;
    private Cube cube;


    public static class Board {
        @Setter
        @Getter
        private int size;
    }

    public static class Game {
        @Setter
        @Getter
        @Value("${max-active-games")
        private int maxActiveGames;
    }

    public static class Cube {
        @Setter
        @Getter
        @Value("${file-path")
        private String filePath;
    }
}
