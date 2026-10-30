package battleship;

public class Program {
    /**
     * Текущее поле.
     */
    private static Ocean ocean;
    /**
     * Текущая флотилия.
     */
    private static Fleet fleet;
    /**
     * Номер текущего выстрела.
     */
    private static int shot;

    public static void main(String[] args) {
        // Входные параметры размера поля и количества кораблей разного типа.
        int[] data = new int[7];
        // Флаг корректности введённых аргументов командной строки.
        boolean correct;
        // Попытка создания поля по аргументам командной строки.
        try {
            data[0] = Integer.parseInt(args[0]);
            data[1] = Integer.parseInt(args[1]);
            data[2] = Integer.parseInt(args[2]);
            data[3] = Integer.parseInt(args[3]);
            data[4] = Integer.parseInt(args[4]);
            data[5] = Integer.parseInt(args[5]);
            data[6] = Integer.parseInt(args[6]);
            correct = args.length == 7 && canCreateField(data[0], data[1],
                    data[2], data[3], data[4], data[5], data[6]);
        } catch (Exception exception) {
            correct = false;
        }

        if (!correct) {
            UserInteraction.printIntro();
        }

        // Номер текущей игры.
        int game = 1;
        // Ведение игрового процесса до выхода из игры.
        do {
            if (!correct || game > 1) {
                data = getCondition();
            }
            gameProcess(data);
            ++game;
        } while (!UserInteraction.wantToQuit(game));
    }

    /**
     * Простреленность клетки по координатам.
     *
     * @param coords массив координат
     * @return простреленность
     */
    public static boolean cellIsShot(int[] coords) {
        return ocean.getCell(coords[0], coords[1]).isShot();
    }

    /**
     * Проверка площадей кораблей на не превышение 25% площади поля.
     *
     * @param n  количество клеток по вертикали
     * @param m  количество клеток по горизонтали
     * @param ca количество кораблей типа Carrier
     * @param ba количество кораблей типа Battleship
     * @param cr количество кораблей типа Cruiser
     * @param de количество кораблей типа Destroyer
     * @param su количество кораблей типа Submarine
     * @return корректность начальных условий
     */
    private static boolean canCreateField(int n, int m, int ca, int ba, int cr, int de, int su) {
        if (ca + ba + cr + de + su != 0) {
            return n * m / 4 >= 5 * ca + 4 * ba + 3 * cr + 2 * de + su;
        }
        return false;
    }

    /**
     * Получение от пользователя начальных данных через консоль.
     *
     * @return массив начальных параметров размера поля и количества кораблей каждого типа.
     */
    private static int[] getCondition() {
        int[] data = UserInteraction.getInitialCondition();
        while (!canCreateField(data[0], data[1], data[2], data[3], data[4], data[5], data[6])) {
            UserInteraction.printIncorrectDataError();
            data = UserInteraction.getInitialCondition();
        }
        return data;
    }

    /**
     * Ход игры.
     *
     * @param data начальные параметры
     */
    private static void gameProcess(int[] data) {
        // Создание поля и его вывод (пустой).
        createField(data);
        UserInteraction.printGenerationFinished(data[0], data[1],
                data[2], data[3], data[4], data[5], data[6]);
        UserInteraction.printField(ocean);

        shot = 1;
        while (true) {
            gameMove(data[0], data[1]);
            // Прекращение игры при желании сдаться на большом количестве ходов.
            if (shot % 25 == 0 && UserInteraction.wantToSurrender(shot)) {
                UserInteraction.printAllField(ocean);
                break;
            }

            // Прекращение игры при уничтожении всей флотилии.
            if (fleet.isDestroyed()) {
                UserInteraction.printVictory(shot);
                break;
            }
        }
    }

    /**
     * Создание поля на основе начальных условий.
     *
     * @param data параметры игры
     */
    private static void createField(int[] data) {
        ocean = new Ocean(data[0], data[1]);
        fleet = new Fleet(data[2], data[3], data[4], data[5], data[6]);
        // Размещение каждого корабля на поле.
        Ship[] ships = fleet.getShips();
        for (Ship ship : ships) {
            ocean.placeShip(ship);
        }
        ////----------------- Раскомментировать для тестирования - вывод сгенерированного поля.
        ocean.printAll();
    }

    /**
     * Ход игры.
     *
     * @param n высота поля
     * @param m ширина поля
     */
    private static void gameMove(int n, int m) {
        // Выстрел по заданной пользователем клетке.
        UserInteraction.printShotNumber(shot);
        int[] coords = UserInteraction.getCoordinates(n, m);
        Cell shot_cell = ocean.getCell(coords[0], coords[1]);
        shot_cell.shoot();

        // Регистрация и информирование о состоянии простреленной клетки.
        if (!shot_cell.isFilled()) {
            UserInteraction.printMiss();
        } else {
            UserInteraction.printHit();
            for (Ship ship : fleet.getShips()) {
                if (ship.hasCell(shot_cell) && ship.isSunk()) {
                    ship.makeSunk();
                    UserInteraction.printShipIsDestroyed(ship.getName());
                    ocean.shootAroundShip(coords[0], coords[1]);
                    break;
                }
            }
        }
        ++shot;
        UserInteraction.printField(ocean);
    }
}
