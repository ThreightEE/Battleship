package battleship;

/**
 * Представление корабля типа Destroyer.
 */
public class Destroyer extends Ship {
    Destroyer() {
        super();
        name = "Destroyer";
        length = 2;
        cells = new Cell[2];
    }
}
