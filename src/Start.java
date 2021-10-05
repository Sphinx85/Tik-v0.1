import java.util.Scanner;
import java.util.Random;

public class Start {

    private static char [] [] map;
    private static int size;
    private static boolean rival;
    private static boolean marker = true;
    private static int dif; //Уровень сложности
    //private static int wayScore[];

    private static final char DOT_EMPTY = '·';
    private static final char DOT_X = 'X';
    private static final char DOT_O = 'O';

    private static final Scanner userInput = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        gameParameter();
        System.out.println("Игра начинается!");
        initMap();
        printMap();

        while (!isEndGame()) {
            turn(rival, dif);
        }
    }   //Логика игры

    private static boolean isEndGame(){

        if (horizontalCheck()){return true;}
        if (verticalCheck()){return true;}
        if (diagonalCheck()){return true;}
        return checkForDraw();

    }   //Проверка окончания игры

    private static boolean checkForDraw() {
        int result = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (map[i][j]!=DOT_EMPTY){
                    result++;
                }
            }
        }
        if(result==size*size){
            System.out.println("Боевая ничья!");
            return true;
        }
        return false;
    }   //Проверка на ничью

    private static boolean diagonalCheck() {
            for (int i = 0, j = 0, winPointsX = 0, winPointsO = 0; j < size; j++, i++){
                if (map[i][j]==DOT_X){
                    winPointsX++;
                } else if (map[i][j]==DOT_O){
                    winPointsO++;
                }
                if(winPointsX == size){
                    System.out.println("Выиграли крестики");
                    return true;
                }
                if (winPointsO == size){
                    System.out.println("Выиграли нолики");
                    return true;
                }
            }

        for (int i = 0, j = size - 1, winPointsX = 0, winPointsO = 0; i < size; j--, i++){
            if (map[i][j]==DOT_X){
                winPointsX++;
            } else if (map[i][j]==DOT_O){
                winPointsO++;
            }
            if(winPointsX == size){
                System.out.println("Выиграли крестики");
                return true;
            }
            if (winPointsO == size){
                System.out.println("Выиграли нолики");
                return true;
            }
        }

        return false;
    }   //Диагональная проверка

    private static boolean verticalCheck() {
        for (int j = 0; j < size; j++){
            int winPointsX = 0, winPointsO = 0;
            for (int i = 0; i < size; i++){
                if (map[i][j]==DOT_X){
                    winPointsX++;
                } else if (map[i][j]==DOT_O){
                    winPointsO++;
                }
                if(winPointsX == size){
                    System.out.println("Выиграли крестики");
                    return true;
                }
                if (winPointsO == size){
                    System.out.println("Выиграли нолики");
                    return true;
                }
            }
        }
        return false;
    }   //Вертикальная проверка

    private static boolean horizontalCheck() {
        for (int i = 0; i < size; i++){
            int winPointsX = 0, winPointsO = 0;
            for (int j = 0; j < size; j++){
                if (map[i][j]==DOT_X){
                    winPointsX++;

                } else if (map[i][j]==DOT_O){
                    winPointsO++;

                }
                if(winPointsX == size){
                    System.out.println("Выиграли крестики");
                    return true;
                }
                if (winPointsO == size){
                    System.out.println("Выиграли нолики");
                    return true;
                }

            }
        }
        return false;
    }   //Горизонтальная проверка

    private static void turn(boolean rival, int dif) {
        if (rival){
            if (marker){
                playerOne();
            } else {
                computerTurn(dif);
            }
        } else {
            if (marker){
                playerOne();
            } else {
                playerTwo();
            }
        }
        marker = !marker;
    }   //Логика хода

    private static boolean isValid(int x, int y){
        if ((x >= size) || (y >= size) || (x < 0) || (y < 0)) {
            return false;
        }
        return map[y][x] != DOT_EMPTY;
    }   //Проверка правильности хода

    private static void playerOne() {
        int x, y;
        do {
            System.out.println("Введите координаты хода по оси Х и У.");
            x = userInput.nextByte();
            y = userInput.nextByte();
            if (isValid(x, y)){
                System.out.println("Вы ввели неправильное значение!");
            }
        } while (isValid(x, y));
        map[y - 1][x - 1] = Start.DOT_X;
        printMap();
    }   //Ход игрока 1

    private static void playerTwo() {
        int x, y;
        do {
            System.out.println("Введите координаты хода по оси Х и У.");
            x = userInput.nextByte();
            y = userInput.nextByte();
            if (isValid(x, y)){
                System.out.println("Вы ввели неправильное значение!");
            }
        } while (isValid(x, y));
        map[y - 1][x - 1] = Start.DOT_O;
        printMap();
    }   //Ход игрока 2

    private static void computerTurn(int diff){
        int x = -1, y = -1;
        switch (diff){
            case (1):
                do {
                    x = random.nextInt(size + 1);
                    y = random.nextInt(size + 1);
                } while (!isValid(x, y));
                break;
            case (2):
                break;
            case (3):
                int way = 0;
                int[] wayScore = new int[size+size+2];
                for(int i = 0; i < size; i++){
                    wayScore[i] = horizontalScore(i);
                    wayScore[i+size] = verticalScore(i);
                    if (i<2){
                        wayScore[i+size+size - 1] = diagonalScore(i);
                    }
                }

                way = switchWay(wayScore);
                if (way < size){
                    do {
                        x = random.nextInt(size + 1);
                        y = way + 1;
                    } while (!isValid(x, y));
                } else if (way < size*2){
                    do {
                        x = way - size + 1;
                        y = random.nextInt(size + 1);
                    } while (!isValid(x, y));
                } else {
                    do {
                        for (int i = 0; i < size; i++) {
                            if (map[i][i] == DOT_EMPTY) {
                                x = i;
                                y = i;

                            }
                        }
                        for (int i = 0; i < size; i++) {
                            if (map[i][size-i-1] == DOT_EMPTY) {
                                x = i;
                                y = i;
                                break;
                            }
                        }
                    } while (!isValid(x, y));

                }
                /*do {
                    x = random.nextInt(size + 1);
                    y = random.nextInt(size + 1);
                } while (isValid(x, y));*/
                break;
        }
        System.out.println("Компьютер сделал ход " + x + " " + y);
        map[y - 1][x - 1] = Start.DOT_O;
        printMap();
    }   //Ход компьютера


    private static int switchWay(int[] score) {
        int rate = 0;
        int way = 0;
        for (int i = 0; i < score.length; i++){
            if (score[i] > rate){
                rate = score[i];
                way = i;
            }
            if (score[i] == - size + 1){
                way = i;
                break;
            }
        }
        return way;
    }

    private static int diagonalScore(int mark) {
        int score = 0;
        if (mark==1){
            for (int i = 0; i < size; i++){
                if (map[i][i]==DOT_X){
                    score--;
                }
                if (map[i][i]==DOT_O){
                    score++;
                }
            }
        }

        if (mark==2){
            for (int i = 0; i < size; i++){
                if (map[i][size-i-1]==DOT_X){
                    score--;
                }
                if (map[i][size-i-1]==DOT_O){
                    score++;
                }
            }
        }

        return score;
    }

    private static int verticalScore(int position) {
        int score = 0;
        for (int i = 0; i < size; i++){
            if (map[i][position]==DOT_X){
                score--;
            }
            if (map[i][position]==DOT_O){
                score++;
            }
        }
        return score;
    }

    private static int horizontalScore(int position) {
        int score = 0;
        for (int i = 0; i < size; i++){
            if (map[position][i]==DOT_X){
                score--;
            }
            if (map[position][i]==DOT_O){
                score++;
            }
        }
        return score;
    }

    private static void gameParameter() {
        size = areaSize();
        rival = playerOrComputer();
        if (rival) {
            dif = difficulty();
        }
    }   //Параметры игры

    private static int areaSize() {
        int var;
        do {
            System.out.println("Введите размер игрового поля от 3 до 9");
            var = userInput.nextByte();
        } while (var > 9 || var < 3);
        return var;
    }   //Размер поля

    private static boolean playerOrComputer() {
        int var;
        do {
            System.out.println("Выберите соперника: 1 - Компьютер, 2 - Человек");
            var = userInput.nextByte();
        } while (var > 2 || var < 1);
        return var == 1;
    }   //Выбор игрока

    private static int difficulty() {
        int var;
        do {
            System.out.println("Выберите уровень сложности 1, 2 или 3");
            var = userInput.nextByte();
        } while (var > 3 || var < 1);
        return var;
    }   //Уровень сложности

    private static void initMap(){
        map = new char [size] [size];
        for (byte i = 0; i < size; i++) {
            for (byte j = 0; j < size; j++) {
                map [i] [j] = DOT_EMPTY;
            }
        }
    }   //Инициализация карты

    private static void printMap(){
        for (byte i = 0; i <= size; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (byte i = 0; i < size; i++) {
            System.out.print(i + 1 + " ");
            for (byte j = 0; j < size; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }   //Отрисовка карты
}