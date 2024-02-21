package pl.pacinho.match.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "match")
public class GameConfiguration {

    private Board board;

    public static class Board {
        @Setter
        @Getter
        private int size;
    }
}
