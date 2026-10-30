package battleship;

/**
 * Представление корабля типа Carrier.
 */
public class Carrier extends Ship {
    Carrier() {
        super();
        name = "Carrier";
        length = 5;
        cells = new Cell[5];
    }
}
