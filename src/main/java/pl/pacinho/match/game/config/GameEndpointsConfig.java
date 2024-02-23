package pl.pacinho.match.game.config;

import pl.pacinho.match.home.config.HomeEndpointsConfig;

public class GameEndpointsConfig {

    public static final String GAMES = HomeEndpointsConfig.HOME + "/games";
    public static final String GAME_NEW = GAMES + "/new";
    public static final String GAME_PAGE = GAMES + "/{gameId}";
    public static final String GAME_ROOM = GAME_PAGE + "/room";
    public static final String GAME_PLAYERS = GAME_ROOM + "/players";
    public static final String GAME_BOARD = GAME_PAGE + "/board";
    public static final String GAME_BOARD_RELOAD = GAME_BOARD + "/reload";
}
