package battleship;

import java.util.Scanner;

public class UserInteraction {
    /**
     * Принятие ввода с консоли.
     */
    private static final Scanner INPUT = new Scanner(System.in);

    /**
     * Вывод приветствия и правил ввода через командную строку при необходимости.
     */
    public static void printIntro() {
        printSeparation();
        System.out.println("Welcome to Battleship!");
        System.out.println("If you want to know how to start the game from the command line, " +
                "please press 'Y', or any other letter if you don't.");
        String choice = INPUT.nextLine();
        if (choice.equals("Y")) {
            printCommandsInfo();
        }
    }

    /**
     * Получение начальных условий.
     *
     * @return массив начальных параметров размера поля и количества кораблей каждого типа.
     */
    public static int[] getInitialCondition() {
        printSeparation();

        // Начальные параметры.
        int[] data = new int[7];
        // Получение размеров поля.
        System.out.println("We need the size of the Ocean.");
        System.out.print("Enter the quantity of cells vertically: ");
        data[0] = getInteger(1, 100);
        System.out.print("Enter the quantity of cells horizontally: ");
        data[1] = getInteger(1, 100);
        System.out.println();

        // Вывод требования к количеству кораблей и минимально возможной суммы их размеров.
        System.out.println("We need the quantity of ships for each type.");
        System.out.println("Remember that the sum of the cells must take no more than 25% " +
                "of the square of Ocean, so - " + data[0] * data[1] / 4 + " is maximum.");
        System.out.println("This condition ensures that the game will not be too easy and the Ocean " +
                "will be rarefied enough.");
        System.out.println("So, classically there is an Ocean 10x10 with one battleship, " +
                "two cruisers, three destroyers, and four submarines - 20% " +
                "(4 + 2*3 + 3*2 + 4 = 20) of the Ocean (10 * 10 = 100).");
        System.out.println();

        // Получение количества кораблей каждого типа.
        System.out.print("Enter the quantity of Carriers (5 cells for each): ");
        data[2] = getInteger(0, 100);
        System.out.print("Enter the quantity of Battleships (4 cells for each): ");
        data[3] = getInteger(0, 100);
        System.out.print("Enter the quantity of Cruisers (3 cells for each): ");
        data[4] = getInteger(0, 100);
        System.out.print("Enter the quantity of Destroyers (2 cells for each): ");
        data[5] = getInteger(0, 100);
        System.out.print("Enter the quantity of Submarines (1 cell for each): ");
        data[6] = getInteger(0, 100);
        System.out.println();
        return data;
    }

    /**
     * Вывод ошибки при недопустимом количестве кораблей.
     */
    public static void printIncorrectDataError() {
        System.out.println("The data does not match the rules! Remember the rule of 25%.");
    }

    /**
     * Вывод результатов генерации.
     *
     * @param n  размер поля по вертикали
     * @param m  размер поля по горизонтали
     * @param ca количество кораблей типа Carrier
     * @param ba количество кораблей типа Battleship
     * @param cr количество кораблей типа Cruiser
     * @param de количество кораблей типа Destroyer
     * @param su количество кораблей типа Submarine
     */
    public static void printGenerationFinished(int n, int m,
                                               int ca, int ba, int cr, int de, int su) {
        printSeparation();
        // Формирование вывода ненулевого количества кораблей каждого типа.
        String[] info = new String[5];
        info[0] = ca > 0 ? ca + " Carriers " : "";
        info[1] = ba > 0 ? ba + " Battleships " : "";
        info[2] = cr > 0 ? cr + " Cruisers " : "";
        info[3] = de > 0 ? de + " Destroyers " : "";
        info[4] = su > 0 ? su + " Submarines " : "";

        // Вывод размещённых кораблей и их количества.
        System.out.println("An Ocean of size " + n + "x" + m + " with ");
        for (String string : info) {
            if (!string.equals("")) {
                System.out.println(string);
            }
        }
        System.out.println("has been created successfully!");
    }

    /**
     * Вывод текущего состояния поля.
     *
     * @param ocean текущее поле
     */
    public static void printField(Ocean ocean) {
        printSeparation();
        // Вывод шапки поля.
        int[] size = ocean.getSize();
        printFieldTable(size);

        // Вывод состояний клеток.
        Cell[][] cells = ocean.getCells();
        for (int line = 0; line < size[0]; ++line) {
            System.out.print((line + 1) + "\t" + "|");
            for (int cell = 0; cell < size[1]; ++cell) {
                System.out.print(" " + cells[line][cell].getStateChar() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Вывод шапки поля.
     *
     * @param size массив размера
     */
    private static void printFieldTable(int[] size) {
        // Вывод координат по горизонтали.
        System.out.print("\t|");
        for (int let = 0; let < size[1]; ++let) {
            if (let < 26) {
                System.out.print(" " + (char) (let + 97) + " ");
            } else if (let < 52) {
                System.out.print(" " + (char) (let + 39) + " ");
            } else if (let < 62) {
                System.out.print(" " + (let - 52) + " ");
            } else {
                System.out.print((let - 52) + " ");
            }
        }
        System.out.println();

        // Вывод разделителя.
        System.out.print("----+-");
        for (int sep = 0; sep < size[1] - 1; ++sep) {
            System.out.print("---");
        }
        System.out.println();
    }

    /**
     * Вывод номера выстрела.
     *
     * @param shot выстрел
     */
    public static void printShotNumber(int shot) {
        printSeparation();
        System.out.println("Shot #" + shot);
    }

    /**
     * Получение координат выстрела по полю.
     *
     * @param n высота поля
     * @param m ширина поля
     * @return массив с координатами клетки выстрела
     */
    public static int[] getCoordinates(int n, int m) {
        int[] coords = new int[2];
        do {
            System.out.print("The vertical coordinate: ");
            coords[0] = getInteger(1, n) - 1;
            System.out.print("The horizontal coordinate: ");
            coords[1] = getHorizontalCoordinate(getCharCoordinate(m));
        } while (Program.cellIsShot(coords));
        return coords;
    }

    public static void printAllField(Ocean ocean) {
        int[] size = ocean.getSize();
        printFieldTable(size);

        Cell[][] cells = ocean.getCells();
        for (int line = 0; line < size[0]; ++line) {
            System.out.print((line + 1) + "\t" + "|");
            for (int cell = 0; cell < size[1]; ++cell) {
                if (cells[line][cell].isFilled()) {
                    System.out.print(" x ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Вывод "мимо".
     */
    public static void printMiss() {
        System.out.println("Miss.");
    }

    /**
     * Вывод попадания.
     */
    public static void printHit() {
        System.out.println("Hit!");
    }

    /**
     * Вывод сообщения о победе над кораблём.
     *
     * @param name название типа корабля
     */
    public static void printShipIsDestroyed(String name) {
        System.out.println("Congrats! You just have sunk a " + name + ".");
    }

    /**
     * Желание сдаться и прекратить текущую игру.
     *
     * @param shots номер будущего хода
     * @return желание сдаться
     */
    public static boolean wantToSurrender(int shots) {
        System.out.println("You did already " + (shots - 1) + " shots.");
        System.out.println("Do you want to surrender? (enter 'Y' if so)");
        String choice = INPUT.nextLine();
        return choice.equals("Y");
    }

    /**
     * Желание завершить программу.
     *
     * @param games номер будущей игры
     * @return желание выйти
     */
    public static boolean wantToQuit(int games) {
        System.out.println("You played already " + (games - 1) + " games.");
        System.out.println("Do you want to quit? (enter 'Y' if so)");
        String choice = INPUT.nextLine();
        return choice.equals("Y");
    }

    /**
     * Вывод сообщения о победе.
     *
     * @param shots номер будущего выстрела
     */
    public static void printVictory(int shots) {
        System.out.println("VICTORY");
        System.out.println("You've destroyed the fleet with " + (shots - 1) + " shots.");
    }

    /**
     * Вывод строки-разделителя.
     */
    private static void printSeparation() {
        for (int i = 0; i < 100; ++i) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Вывод правил ввода аргументов через командную строку.
     */
    private static void printCommandsInfo() {
        printSeparation();
        System.out.println("To start the game you have to specify the size of the Ocean " +
                "(quantity of cells vertically and horizontally), " +
                "and the quantity of ships for each type (Carrier, Battleship, Cruiser, " +
                "Destroyer, and Submarine) as command line arguments.");
        System.out.println("So to be: <quantity of cells vertically> " +
                "<quantity of cells horizontally> <quantity of Carriers> <quantity of  Battleships> " +
                "<quantity of Cruisers> <quantity of Destroyers> <quantity of Submarines>");
        System.out.println("For example: '7 9 3 0 0 0 8' would make to create an Ocean " +
                "with size of 7x9 cells where to place 3 carriers and 8 submarines.");
        System.out.println("Remember that the sum of the ship sizes must not take more than 25% of " +
                "the square of Ocean or the data will be processed as incorrect.");
        System.out.println("So '6 4 10 0 0 0 0' will be ignored and you will be pleased to " +
                "re-enter the data.");
    }

    /**
     * Получение целого числа в заданном диапазоне.
     *
     * @param min минимальное возвращаемое число
     * @param max максимальное возвращаемое число
     * @return целое число в рамках диапазона
     */
    private static int getInteger(int min, int max) {
        // Число.
        int number = 0;
        // Флаг корректности.
        boolean correct;
        // Попытки записать вводимые строки, как числа в заданном диапазоне.
        do {
            try {
                number = INPUT.nextInt();
                correct = number >= min && number <= max;
            } catch (Exception notInt) {
                INPUT.next();
                correct = false;
            }

            if (!correct) {
                System.out.print("This data must be an integer between " + min + " and " + max +
                        "! Try again: ");
            }
        } while (!correct);
        INPUT.nextLine();
        return number;
    }

    /**
     * Получение валидной горизонтальной координаты выстрела.
     *
     * @param max ширина поля
     * @return символ-координата по горизонтали
     */
    private static String getCharCoordinate(int max) {
        // Пользовательский ввод.
        String input = INPUT.nextLine();
        // Введённый символ.
        char input_symbol = input.charAt(0);
        // Подходящее число-координата.
        int input_number;
        // Подходящий символ-координата
        char correct_symbol;

        while (true) {
            for (int i = 0; i < max && i < 52; ++i) {
                // Получение корректных символов для поля.
                if (i < 26) {
                    correct_symbol = (char) (i + 97);
                } else {
                    correct_symbol = (char) (i + 39);
                }

                // Проверка введённого символа на корректность.
                if (input_symbol == correct_symbol && input.length() == 1) {
                    return Character.toString(input_symbol);
                }
            }

            // Попытка получения и передачи введённого числа, если оно корректно.
            try {
                input_number = Integer.parseInt(input);
                if (input_number >= 0 && input_number < max - 52) {
                    return input;
                }
            } catch (Exception e) {
            }

            // Обработка некорректного ввода.
            System.out.print("There is no such horizontal coordinate! Try again: ");
            input = INPUT.nextLine();
            input_symbol = input.charAt(0);
        }
    }

    /**
     * Получение целочисленной горизонтальной координаты по символу-обозначению.
     *
     * @param coord символьная координата
     * @return целочисленная координата
     */
    private static int getHorizontalCoordinate(String coord) {
        char symbol = coord.charAt(0);
        // Рассмотрения случаев символа-координаты и числа-координаты.
        if (symbol >= 'a' && symbol <= 'z') {
            return (int) symbol - 97;
        }

        if (symbol >= 'A' && symbol <= 'Z') {
            return (int) symbol - 39;
        }
        return Integer.parseInt(coord) + 52;
    }
}
