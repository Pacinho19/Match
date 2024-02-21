package pl.pacinho.match.utils;

import java.util.Random;

public class RandomUtils {

    private static Random random = new Random();

    public static int getRandom(int min, int max){
        return random.nextInt(max - min) + min;
    }
}
