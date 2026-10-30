package battleship;

/**
 * Общее представление корабля с занимаемыми клетками.
 */
public class Ship {
    /**
     * Название корабля.
     */
    protected String name;
    /**
     * Количество клеток, занимаемых кораблём.
     */
    protected int length;
    /**
     * Занимаемые кораблём клетки.
     */
    protected Cell[] cells;

    /**
     * Добавление корабля.
     */
    Ship() {
    }

    /**
     * Получение названия корабля.
     *
     * @return название
     */
    public String getName() {
        return name;
    }

    /**
     * Наличие части корабля на заданной клетке.
     *
     * @param cell клетка
     * @return наличие у корабля
     */
    public boolean hasCell(Cell cell) {
        for (Cell c : cells) {
            if (c == cell) {
                return true;
            }
        }
        return false;
    }

    /**
     * Победа над кораблём.
     *
     * @return корабль утоплен
     */
    public boolean isSunk() {
        for (Cell cell : cells) {
            if (!cell.isShot()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Фиксирование клеток корабля как утопленных.
     */
    public void makeSunk() {
        for (Cell cell : cells) {
            cell.setSunk();
        }
    }
}
