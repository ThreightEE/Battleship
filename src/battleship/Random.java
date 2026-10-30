package battleship;

/**
 * Получение случайного числа.
 */
public class Random {
    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
