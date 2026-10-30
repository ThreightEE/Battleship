package battleship;

/**
 * Представление флотилии входящими в неё кораблями.
 */
public class Fleet {
    /**
     * Корабли флотилии.
     */
    private final Ship[] ships;

    /**
     * Создание флотилии с заданным количеством кораблей каждого типа.
     *
     * @param ca количество кораблей типа Carrier
     * @param ba количество кораблей типа Battleship
     * @param cr количество кораблей типа Cruiser
     * @param de количество кораблей типа Destroyer
     * @param su количество кораблей типа Submarine
     */
    Fleet(int ca, int ba, int cr, int de, int su) {
        // Общее количество кораблей.
        int size = ca + ba + cr + de + su;
        ships = new Ship[size];
        // Номер корабля, вносящегося в массив.
        int current = 0;

        // Добавление кораблей типа Carrier.
        for (int s = 0; s < ca; ++s, ++current) {
            ships[current] = new Carrier();
        }

        // Добавление кораблей типа Battleship.
        for (int s = 0; s < ba; ++s, ++current) {
            ships[current] = new Battleship();
        }

        // Добавление кораблей типа Cruiser.
        for (int s = 0; s < cr; ++s, ++current) {
            ships[current] = new Cruiser();
        }

        // Добавление кораблей типа Destroyer.
        for (int s = 0; s < de; ++s, ++current) {
            ships[current] = new Destroyer();
        }

        // Добавление кораблей типа Submarine.
        for (int s = 0; s < su; ++s, ++current) {
            ships[current] = new Submarine();
        }
    }

    /**
     * Передача массива кораблей.
     *
     * @return корабли флотилии
     */
    public Ship[] getShips() {
        return ships;
    }

    /**
     * Флотилия уничтожена.
     *
     * @return флот побеждён
     */
    public boolean isDestroyed() {
        for (Ship ship : ships) {
            for (Cell cell : ship.cells) {
                if (!cell.isShot()) {
                    return false;
                }
            }
        }
        return true;
    }
}
