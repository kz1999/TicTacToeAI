package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static String[][] field = new String[3][3];

    public static void intitialize() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = " ";
            }
        }
        printField();
    }


    private static void printField() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
    }

    public static boolean checkForWin(int x, int y) {
        boolean win = false;
        int empty = 0;
        int same = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j].equals(" "))
                    empty++;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (field[x - 1][j].equals(field[x - 1][y - 1])) {
                same++;
            }
        }
        if (same == 3) {
            win = true;
        }
        same = 0;
        if (!win) {
            for (int i = 0; i < 3; i++) {
                if (field[i][y - 1].equals(field[x - 1][y - 1])) {
                    same++;
                }
            }
        }
        if (same == 3) {
            win = true;
        }
        same = 0;

        if ((x == 1 && y == 1) || (x == 3 && y == 3) || (x == 2 && y == 2) && !win) {
            for (int dx = 0; dx < 3; dx++) {
                if (field[dx][dx].equals(field[x - 1][y - 1])) {
                    same++;
                }
            }
        }
        if (same == 3) {
            win = true;
        }
        same = 0;

        if ((x == 1 && y == 3) || (x == 3 && y == 1) || (x == 2 && y == 2) && !win) {
            for (int dx = 2; dx >= 0; dx--) {
                if (field[dx][2 - dx].equals(field[x - 1][y - 1])) {
                    same++;
                }
            }
        }

        if (same == 3) {
            win = true;
        }

        if (win) {
            System.out.println(field[x - 1][y - 1] + " wins");
            return win;
        } else if (empty != 0) {
            return false;
        } else {
            System.out.println("Draw");
            return true;
        }
    }

    public static boolean user(Scanner sc, String s) {
        System.out.println("Enter the coordinates:");
        boolean entered = false;
        while (!entered) {
            try {
                String in = sc.nextLine();

                int x = Integer.parseInt(in.split(" ")[0]);
                int y = Integer.parseInt(in.split(" ")[1]);

                if (x < 1 || x > 3 || y < 1 || y > 3) {
                    throw new Exception("Coordinates should be from 1 to 3!");
                } else if (field[x - 1][y - 1] != " ") {
                    throw new Exception("This cell is occupied! Choose another one!");
                }

                field[x - 1][y - 1] = s;
                entered = true;
                printField();

                return checkForWin(x, y);


            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);

            String command = new String();
            String playerOne = new String();
            String playerTwo = new String();

            while (true) {
                try {
                    String[] input = sc.nextLine().split(" ");
                    command = input[0];
                    if (command.equals("exit"))
                        break;
                    playerOne = input[1];
                    playerTwo = input[2];

                    if (playerOne.isEmpty() || playerTwo.isEmpty())
                        throw new Exception("Bad parameters!");
                    if (!(command.equals("start") || command.equals("exit")))
                        throw new Exception("Bad commands!");
                    if (!(playerOne.equals("user") || playerOne.equals("easy") || playerOne.equals("medium") || playerOne.equals("hard")) ||
                            !(playerTwo.equals("user") || playerTwo.equals("easy") || playerTwo.equals("medium") || playerTwo.equals("hard")))
                        throw new Exception("Bad players!");
                    break;
                } catch (Exception e) {
                    System.out.println("Bad parameters!");
                }

            }
            if (command.equals("start")) {
                intitialize();
                start(playerOne, playerTwo, sc);
            } else {
                break;
            }
        }
    }

    private static void start(String playerOne, String playerTwo, Scanner sc) {
        boolean finished = false;

        while (!finished) {
            if (playerOne.equals("user")) {
                finished = user(sc, "X");
                if (finished)
                    break;
            } else if (playerOne.equals("easy")) {
                finished = easy("X");
                if (finished)
                    break;
            } else if (playerOne.equals("medium")){
                finished = medium("X");
                if (finished)
                    break;
            } else {
                finished = hard("X");
                if (finished)
                    break;
            }
            if (playerTwo.equals("user")) {
                finished = user(sc, "O");
                if (finished)
                    break;
            } else if (playerTwo.equals("easy")) {
                finished = easy("O");
                if (finished)
                    break;
            } else if (playerTwo.equals("medium")){
                finished = medium("O");
                if (finished)
                    break;
            } else {
                finished = hard("O");
                if (finished)
                    break;;
            }
        }
    }

    private static boolean medium(String s) {
        System.out.println("Making move level \"medium\"");

        String opponent;

        if (s.equals("O"))
            opponent = "X";
        else
            opponent = "O";
        int count = 0;
        int x = -1;
        int y = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j].equals(s))
                    count++;
                else if (field[i][j].equals(" ")) {
                    x = i;
                    y = j;
                }
            }
            if (count == 2 && (x != -1 && y != -1)) {
                field[x][y] = s;
                count = 0;
                printField();
                return checkForWin(x + 1, y + 1);
            }
            count = 0;
            x = -1;
            y = -1;
        }


        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (field[i][j].equals(s))
                    count++;
                else if (field[i][j].equals(" ")) {
                    x = i;
                    y = j;
                }
            }
            if (count == 2 && (x != -1 && y != -1)) {
                field[x][y] = s;
                count = 0;
                printField();
                return checkForWin(x + 1, y + 1);
            }
            count = 0;
            x = -1;
            y = -1;
        }


        for (int i = 0; i < 3; i++) {
            if (field[i][i].equals(s))
                count++;
            else if (field[i][i].equals(" ")) {
                x = i;
                y = i;
            }

            if (count == 2 && (x != -1 && y != -1)) {
                field[x][y] = s;
                count = 0;
                printField();
                return checkForWin(x + 1, y + 1);
            }
            count = 0;
            x = -1;
            y = -1;
        }


        for (int i = 0; i < 3; i++) {
            if (field[i][i].equals(s))
                count++;
            else if (field[i][i].equals(" ")) {
                x = i;
                y = i;
            }

            if (count == 2 && (x != -1 && y != -1)) {
                field[x][y] = s;
                count = 0;
                printField();
                return checkForWin(x + 1, y + 1);
            }
        }
        count = 0;
        x = -1;
        y = -1;

        for (int i = 0; i < 3; i++) {
            if (field[i][2 - i].equals(s))
                count++;
            else if (field[i][2 - i].equals(" ")) {
                x = i;
                y = i;
            }

            if (count == 2 && (x != -1 && y != -1)) {
                field[x][y] = s;
                count = 0;
                printField();
                return checkForWin(x + 1, y + 1);
            }
        }
        count = 0;
        x = -1;
        y = -1;

        //blocking opponent
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j].equals(opponent))
                    count++;
                else if (field[i][j].equals(" ")) {
                    x = i;
                    y = j;
                }
            }
            if (count == 2 && (x != -1 && y != -1)) {
                field[x][y] = s;
                count = 0;
                printField();
                return checkForWin(x + 1, y + 1);
            }
            count = 0;
            x = -1;
            y = -1;
        }

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (field[i][j].equals(opponent))
                    count++;
                else if (field[i][j].equals(" ")) {
                    x = i;
                    y = j;
                }
            }
            if (count == 2 && (x != -1 && y != -1)) {
                field[x][y] = s;
                count = 0;
                printField();
                return checkForWin(x + 1, y + 1);
            }
            count = 0;
            x = -1;
            y = -1;
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][i].equals(opponent))
                count++;
            else if (field[i][i].equals(" ")) {
                x = i;
                y = i;
            }

            if (count == 2 && (x != -1 && y != -1)) {
                field[x][y] = s;
                count = 0;
                printField();
                return checkForWin(x + 1, y + 1);
            }

        }
        count = 0;
        x = -1;
        y = -1;

        for (int i = 0; i < 3; i++) {
            if (field[i][2 - i].equals(opponent))
                count++;
            else if (field[i][2 - i].equals(" ")) {
                x = i;
                y = 2 - i;
            }

            if (count == 2 && (x != -1 && y != -1)) {
                field[x][y] = s;
                count = 0;
                printField();
                return checkForWin(x + 1, y + 1);
            }
        }
        count = 0;
        x = -1;
        y = -1;

        Random rand = new Random();

        x = rand.nextInt(3);
        y = rand.nextInt(3);

        boolean taken;

        if (field[x][y].equals(" ")) {
            taken = false;
        } else {
            taken = true;
        }
        while (taken) {
            x = rand.nextInt(3);
            y = rand.nextInt(3);

            if (field[x][y].equals(" ")) {
                taken = false;
            } else {
                taken = true;
            }
        }
        field[x][y] = s;
        printField();
        return checkForWin(x + 1, y + 1);

    }

    private static boolean easy(String s) {
        System.out.println("Making move level \"easy\"");

        Random rand = new Random();

        int x = rand.nextInt(3);
        int y = rand.nextInt(3);

        boolean taken;

        if (field[x][y].equals(" ")) {
            taken = false;
        } else {
            taken = true;
        }
        while (taken) {
            x = rand.nextInt(3);
            y = rand.nextInt(3);

            if (field[x][y].equals(" ")) {
                taken = false;
            } else {
                taken = true;
            }
        }
        field[x][y] = s;
        printField();
        return checkForWin(x + 1, y + 1);
    }

    private static boolean hard(String s) {
        System.out.println("Making move level \"hard\"");

        String opponent;

        if (s.equals("X"))
            opponent = "O";
        else
            opponent = "X";

        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[2];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == " ") {
                    field[i][j] = s;
                    int score = minimax(field, 0, false, i, j, opponent, s);
                    field[i][j] = " ";
                    if (score > bestScore) {
                        bestScore = score;
                        move = new int[]{i, j};
                    }
                }
            }
        }
        field[move[0]][move[1]] = s;
        printField();
        return checkForWin(move[0] + 1, move[1] + 1);
    }
    public static int checkForWinMin(int x, int y) {
        boolean win = false;
        int empty = 0;
        int same = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j].equals(" "))
                    empty++;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (field[x - 1][j].equals(field[x - 1][y - 1])) {
                same++;
            }
        }
        if (same == 3) {
            win = true;
        }
        same = 0;
        if (!win) {
            for (int i = 0; i < 3; i++) {
                if (field[i][y - 1].equals(field[x - 1][y - 1])) {
                    same++;
                }
            }
        }
        if (same == 3) {
            win = true;
        }
        same = 0;

        if ((x == 1 && y == 1) || (x == 3 && y == 3) || (x == 2 && y == 2) && !win) {
            for (int dx = 0; dx < 3; dx++) {
                if (field[dx][dx].equals(field[x - 1][y - 1])) {
                    same++;
                }
            }
        }
        if (same == 3) {
            win = true;
        }
        same = 0;

        if ((x == 1 && y == 3) || (x == 3 && y == 1) || (x == 2 && y == 2) && !win) {
            for (int dx = 2; dx >= 0; dx--) {
                if (field[dx][2 - dx].equals(field[x - 1][y - 1])) {
                    same++;
                }
            }
        }

        if (same == 3) {
            win = true;
        }

        if (win) {
            //System.out.println(field[x - 1][y - 1] + " wins");
            return 1;
        } else if (empty != 0) {
            return 10;
        } else {
            //System.out.println("Draw");
            return 0;
        }
    }

    private static int minimax(String[][] board, int depth, boolean isMaximizing, int x, int y, String s, String opp) {
        int result = checkForWinMin(x + 1, y + 1);
        if (result != 10) {
            if (isMaximizing)
                return -result;
            else
                return result;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == " ") {
                        board[i][j] = s;
                        int score = minimax(board, depth + 1, false, i, j, opp, s);
                        board[i][j] = " ";
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == " ") {
                        board[i][j] = s;
                        int score = minimax(board, depth + 1, true, i, j, opp, s);
                        board[i][j] = " ";
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }
}
