package battleship;

/**
 * Представление клетки - ячейки поля.
 */
public class Cell {
    /**
     * Возможность заполнить клетку.
     */
    private boolean canBeFilled;
    /**
     * Заполненность клетки.
     */
    private boolean isFilled;
    /**
     * Простреленность клетки.
     */
    private boolean isShot;

    /**
     * Состояния, видимые пользователем.
     */
    enum State {
        /**
         * Нетронутая клетка.
         */
        UNKNOWN,
        /**
         * Пустая простреленная клетка.
         */
        EMPTY,
        /**
         * Простреленная клетка с частью корабля.
         */
        SHOT,
        /**
         * Простреленная клетка с утопленным кораблём.
         */
        SUNK
    }

    /**
     * Состояние клетки.
     */
    private State state;

    /**
     * Создание пустой нетронутой клетки.
     */
    Cell() {
        canBeFilled = true;
        isFilled = false;
        isShot = false;
        state = State.UNKNOWN;
    }

    /**
     * Возможность заполнить клетку.
     *
     * @return возможность заполнения
     */
    public boolean canBeFilled() {
        return canBeFilled;
    }

    /**
     * Заполнение клетки.
     */
    public void fill() {
        isFilled = true;
    }

    /**
     * Запрет на заполнение клетки.
     */
    public void forbidFilling() {
        canBeFilled = false;
    }

    /**
     * Передача символа-состояния клетки.
     *
     * @return символ для вывода состояния
     */
    public char getStateChar() {
        switch (state) {
            // Пусто при неизвестном состоянии.
            case UNKNOWN -> {
                return ' ';
            }

            // Точка при "мимо".
            case EMPTY -> {
                return '·';
            }

            // Крест при "попал".
            case SHOT -> {
                return 'x';
            }

            // Крупный крест при "убил".
            case SUNK -> {
                return 'X';
            }
        }
        return 0;
    }

    /**
     * Простреленность клетки.
     *
     * @return простреленность
     */
    public boolean isShot() {
        return isShot;
    }

    /**
     * Заполненность клетки.
     *
     * @return заполненность
     */
    public boolean isFilled() {
        return isFilled;
    }

    /**
     * Выстрел по клетке.
     */
    public void shoot() {
        isShot = true;
        if (state != State.SUNK) {
            if (isFilled) {
                state = State.SHOT;
            } else {
                state = State.EMPTY;
            }
        }
    }

    /**
     * Установка состояния "потоплен" на клетке.
     */
    public void setSunk() {
        state = State.SUNK;
    }
}
