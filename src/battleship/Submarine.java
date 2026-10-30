package battleship;

/**
 * Представление корабля типа Submarine.
 */
public class Submarine extends Ship {
    Submarine() {
        super();
        name = "Submarine";
        length = 1;
        cells = new Cell[1];
    }
}
