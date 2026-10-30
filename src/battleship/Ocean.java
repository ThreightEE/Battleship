package battleship;

/**
 * Представление поля с помощью клеток.
 */
public class Ocean {
    /**
     * Клетки поля.
     */
    private final Cell[][] cells;
    /**
     * Высота поля.
     */
    private final int n;
    /**
     * Ширина поля.
     */
    private final int m;

    /**
     * Создание пустого поля заданного размера.
     *
     * @param n количество клеток по вертикали
     * @param m количество клеток по горизонтали
     */
    Ocean(int n, int m) {
        // Добавление клеток в поле.
        this.n = n;
        this.m = m;
        cells = new Cell[n][m];

        // Заполнение поля пустыми клетками.
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                cells[i][j] = new Cell();
            }
        }
    }

    /**
     * Установка произвольного корабля на поле.
     *
     * @param ship корабль
     */
    public void placeShip(Ship ship) {
        // Заполнение клеток корабля и запрет на заполнение соседних.
        int[][] coords = getCoordinatesForShip(ship.length);
        for (int c = 0; c < ship.length; ++c) {
            cells[coords[c][0]][coords[c][1]].fill();
            cells[coords[c][0]][coords[c][1]].forbidFilling();
            forbidFillingAroundCell(coords[c][0], coords[c][1]);
            ship.cells[c] = cells[coords[c][0]][coords[c][1]];
        }
    }

    /**
     * Получение координат возможного расположения произвольного корабля.
     *
     * @param length длина корабля
     * @return координаты подходящего расположения корабля
     */
    private int[][] getCoordinatesForShip(int length) {
        // Флаг на корректность расположения корабля.
        boolean correct;
        // Координаты расположения.
        int[][] coords;
        // Координаты рассматриваемой клетки.
        int x, y;

        do {
            // Получение координат случайной клетки.
            correct = true;
            coords = new int[length][2];
            x = Random.getRandomNumber(0, n - 1);
            y = Random.getRandomNumber(0, m - 1);

            // Рассмотрение клеток от выбранной.
            if (!cells[x][y].canBeFilled()) {
                correct = false;
            } else {
                coords[0][0] = x;
                coords[0][1] = y;
                // Попытка установить корабль вверх.
                if (length <= coords[0][0] + 1) {
                    for (int up = 1; up < length; ++up) {
                        x = coords[0][0] - up;
                        y = coords[0][1];
                        if (!cells[x][y].canBeFilled()) {
                            correct = false;
                            break;
                        } else {
                            coords[up][0] = x;
                            coords[up][1] = y;
                        }
                    }
                } else {
                    correct = false;
                }

                // Попытка установить корабль вправо.
                if (!correct && length <= m - coords[0][1]) {
                    correct = true;
                    for (int rgt = 1; rgt < length; ++rgt) {
                        x = coords[0][0];
                        y = coords[0][1] + rgt;
                        if (!cells[x][y].canBeFilled()) {
                            correct = false;
                            break;
                        } else {
                            coords[rgt][0] = x;
                            coords[rgt][1] = y;
                        }
                    }
                }

                // Попытка установить корабль влево.
                if (!correct && length <= coords[0][1] + 1) {
                    correct = true;
                    for (int lft = 1; lft < length; ++lft) {
                        x = coords[0][0];
                        y = coords[0][1] - lft;
                        if (!cells[x][y].canBeFilled()) {
                            correct = false;
                            break;
                        } else {
                            coords[lft][0] = x;
                            coords[lft][1] = y;
                        }
                    }
                }

                // Попытка установить корабль вниз.
                if (!correct && length <= n - coords[0][0]) {
                    correct = true;
                    for (int dwn = 1; dwn < length; ++dwn) {
                        x = coords[0][0] + dwn;
                        y = coords[0][1];
                        if (!cells[x][y].canBeFilled()) {
                            correct = false;
                            break;
                        } else {
                            coords[dwn][0] = x;
                            coords[dwn][1] = y;
                        }
                    }
                }
            }
        } while (!correct);
        return coords;
    }

    /**
     * Запрет заполнения клеток вокруг данной.
     *
     * @param x вертикальная координата клетки
     * @param y горизонтальная координата клетки
     */
    private void forbidFillingAroundCell(int x, int y) {
        if (x > 0 && y > 0) {
            cells[x - 1][y - 1].forbidFilling();
        }

        if (x > 0) {
            cells[x - 1][y].forbidFilling();
        }

        if (x > 0 && y + 1 < m) {
            cells[x - 1][y + 1].forbidFilling();
        }

        if (y > 0) {
            cells[x][y - 1].forbidFilling();
        }

        if (y + 1 < m) {
            cells[x][y + 1].forbidFilling();
        }

        if (x + 1 < n && y > 0) {
            cells[x + 1][y - 1].forbidFilling();
        }

        if (x + 1 < n) {
            cells[x + 1][y].forbidFilling();
        }

        if (x + 1 < n && y + 1 < m) {
            cells[x + 1][y + 1].forbidFilling();
        }
    }

    /**
     * Передача размера поля
     *
     * @return массив с высотой и шириной
     */
    public int[] getSize() {
        return new int[]{n, m};
    }

    /**
     * Передача клеток поля.
     *
     * @return массив клеток
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Получение клетки по координатам.
     *
     * @param x координата по вертикали
     * @param y координата по горизонтали
     * @return клетка
     */
    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    /**
     * Выстрелы по смежным с кораблём клеткам.
     *
     * @param x вертикальная координата любой клетки корабля
     * @param y горизонтальная координата любой клетки корабля
     */
    public void shootAroundShip(int x, int y) {
        shootAroundCell(x, y);
        for (int up = 1; ; ++up) {
            if (x >= up) {
                if (!cells[x - up][y].isFilled()) {
                    break;
                }
                shootAroundCell(x - up, y);
            } else {
                break;
            }
        }

        for (int rgt = 1; ; ++rgt) {
            if (y + rgt < m) {
                if (!cells[x][y + rgt].isFilled()) {
                    break;
                }
                shootAroundCell(x, y + rgt);
            } else {
                break;
            }
        }

        for (int lft = 1; ; ++lft) {
            if (y >= lft) {
                if (!cells[x][y - lft].isFilled()) {
                    break;
                }
                shootAroundCell(x, y - lft);
            } else {
                break;
            }
        }

        for (int dwn = 1; ; ++dwn) {
            if (x + dwn < n) {
                if (!cells[x + dwn][y].isFilled()) {
                    break;
                }
                shootAroundCell(x + dwn, y);
            } else {
                break;
            }
        }
    }

    /**
     * Выстрелы по смежным с данной клеткам.
     *
     * @param x вертикальная координата клетки
     * @param y горизонтальная координата клетки
     */
    private void shootAroundCell(int x, int y) {
        if (x > 0 && y > 0) {
            cells[x - 1][y - 1].shoot();
        }

        if (x > 0) {
            cells[x - 1][y].shoot();
        }

        if (x > 0 && y + 1 < m) {
            cells[x - 1][y + 1].shoot();
        }

        if (y > 0) {
            cells[x][y - 1].shoot();
        }

        if (y + 1 < m) {
            cells[x][y + 1].shoot();
        }

        if (x + 1 < n && y > 0) {
            cells[x + 1][y - 1].shoot();
        }

        if (x + 1 < n) {
            cells[x + 1][y].shoot();
        }

        if (x + 1 < n && y + 1 < m) {
            cells[x + 1][y + 1].shoot();
        }
    }

    // Вывод сгенерированного поля (для тестирования случайной генерации).
    public void printAll() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                String pr;
                if (cells[i][j].isFilled())
                    pr = "X ";
                else if (!cells[i][j].canBeFilled())
                    pr = "· ";
                else pr = "  ";
                System.out.print(pr);
            }
            System.out.println();
        }
    }
}
