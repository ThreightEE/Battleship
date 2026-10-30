package battleship;

/**
 * Представление корабля типа Cruiser.
 */
public class Cruiser extends Ship {
    Cruiser() {
        super();
        name = "Cruiser";
        length = 3;
        cells = new Cell[3];
    }
}
