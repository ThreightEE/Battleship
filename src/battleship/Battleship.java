package battleship;

/**
 * Представление корабля типа Battleship.
 */
public class Battleship extends Ship {
    Battleship() {
        super();
        name = "Battleship";
        length = 4;
        cells = new Cell[4];
    }
}
