package task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;

public class TicTacToe {
    private static final String[][] matrix = new String[3][3];

    public static void main(String[] args) throws IOException {
        showInitialView();
        messageChooseYourSign();
        String userSign = chooseYourSign();
        String computerSign = getComputerSign(userSign);

        while (hasMatrixMoreEmptyFields()) {
            if (hasWinner()) {
                System.out.println("Computer wins!");
                break;
            }

            showMatrix();

            boolean isUserWrong = true;
            while (isUserWrong) {
                messagePutRowNumber();
                int row = getUserNumber() - 1;
                messagePutColumnNumber();
                int column = getUserNumber() - 1;
                if (hasMatrixFreeCell(row, column)) {
                    putSignToFreeCell(userSign, row, column);
                    isUserWrong = false;
                } else {
                    messageWarningPutInFreeCell();
                }
            }

            showMatrix();

            if (hasWinner()) {
                System.out.println("You win!");
                break;
            }

            boolean isComputerWrong = true;
            while (isComputerWrong) {
                int[][] numbers = getComputerNumbers();
                int computerRow = numbers[0][0];
                int computerColumn = numbers[0][1];
                if (hasMatrixFreeCell(computerRow, computerColumn)) {
                    putSignToFreeCell(computerSign, computerRow, computerColumn);
                    isComputerWrong = false;
                }
            }
        }

        if (!hasWinner()) {
            System.out.println("Draw!");
        }
    }

    private static void showInitialView() {
        System.out.println("""
                It's Tic Tac Toe! You will enter 'o' or 'x'
                Then you should be enter ROW number(1, 2 or 3), where your sign must be.
                Then you should be enter COLUMN number(1, 2 or 3), where your sign must be.""");
        System.out.println("Good luck!");
    }

    private static void showMatrix() {
        String[][] matrixForView = Arrays.stream(matrix).map(String[]::clone).toArray(String[][]::new);

        for (int i = 0; i < matrixForView.length; i++) {
            for (int j = 0; j < matrixForView[i].length; j++) {
                if (matrixForView[i][j] == null) {
                    matrixForView[i][j] = " ";
                }
            }
        }

        System.out.println("***************");

        System.out.println(matrixForView[0][0] + " | " + matrixForView[0][1] + " | " + matrixForView[0][2] + "\n" +
                "---------" + "\n" +
                matrixForView[1][0] + " | " + matrixForView[1][1] + " | " + matrixForView[1][2] + "\n" +
                "---------" + "\n" +
                matrixForView[2][0] + " | " + matrixForView[2][1] + " | " + matrixForView[2][2]);
    }

    private static boolean hasMatrixMoreEmptyFields() {
        for (String[] strings : matrix) {
            for (String string : strings) {
                if (string == null) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void messageChooseYourSign() {
        System.out.println("Please, choose 'o' or 'x':");
    }

    private static void messagePutRowNumber() {
        System.out.println("Put row number 1, 2 or 3");
    }

    private static void messagePutColumnNumber() {
        System.out.println("Put column number 1, 2 or 3");
    }

    private static void messageWarningPutInFreeCell() {
        System.out.println("This cell was fill! Choose empty cell!");
    }

    private static String chooseYourSign() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine().toLowerCase();
    }

    private static int getUserNumber() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int number = Integer.parseInt(reader.readLine());

        if (number != 1 && number != 2 && number != 3) {
            System.out.println("Error! Enter 1, 2 or 3");
            number = getUserNumber();
        }

        return number;
    }

    private static boolean hasMatrixFreeCell(int row, int column) {
        return matrix[row][column] == null;
    }

    private static void putSignToFreeCell(String sign, int row, int column) {
        matrix[row][column] = sign;
    }

    private static boolean hasWinner() throws NullPointerException{

        if (matrix[0][0] == null && matrix[1][1] == null && matrix[2][2] == null) {
            return false;
        }
        if (matrix[0][2] == null && matrix[1][1] == null && matrix[2][0] == null) {
            return false;
        }
        if (matrix[0][0] == null && matrix[0][1] == null && matrix[0][2] == null) {
            return false;
        }
        if (matrix[1][0] == null && matrix[1][1] == null && matrix[1][2] == null) {
            return false;
        }
        if (matrix[2][0] == null && matrix[2][1] == null && matrix[2][2] == null) {
            return false;
        }
        if (matrix[0][0] == null && matrix[1][0] == null && matrix[2][0] == null) {
            return false;
        }
        if (matrix[0][1] == null && matrix[1][1] == null && matrix[2][1] == null) {
            return false;
        }
        if (matrix[0][2] == null && matrix[1][2] == null && matrix[2][2] == null) {
            return false;
        }

        if (matrix[0][0] != null && matrix[0][0].equals(matrix[1][1]) && matrix[0][0].equals(matrix[2][2])) {
            return true;
        }
        if (matrix[0][2] != null && matrix[0][2].equals(matrix[1][1]) && matrix[0][2].equals(matrix[2][0])) {
            return true;
        }
        if (matrix[0][0] != null && matrix[0][0].equals(matrix[0][1]) && matrix[0][1].equals(matrix[0][2])) {
            return true;
        }
        if (Objects.equals(matrix[1][0], matrix[1][1]) && matrix[1][0].equals(matrix[1][2])) {
            return true;
        }
        if (Objects.equals(matrix[2][0], matrix[2][1]) && matrix[2][0].equals(matrix[2][2])) {
            return true;
        }
        if (Objects.equals(matrix[0][0], matrix[1][0]) && matrix[0][0].equals(matrix[2][0])) {
            return true;
        }
        if (Objects.equals(matrix[0][1], matrix[1][1]) && matrix[0][1].equals(matrix[2][1])) {
            return true;
        }

        return Objects.equals(matrix[0][2], matrix[1][2]) && matrix[0][2].equals(matrix[2][2]);
    }

    private static String getComputerSign(String userSign) {
        return userSign.equals("o") ? "x" : "o";
    }

    private static int[][] getComputerNumbers() {
        Integer[][] emptyCells = new Integer[8][2];
        int row = 0;
        int column = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == null) {
                    emptyCells[row][column] = i;
                    emptyCells[row][column + 1] = j;
                    row++;
                }
            }
        }

        int limit = 0;
        for (Integer[] emptyCell : emptyCells) {
            if (emptyCell[0] != null) {
                limit++;
            }
        }

        row = (int) (Math.random() * limit);
        int[][] rowAndNumber = new int[1][2];
        rowAndNumber[0][0] = emptyCells[row][0];
        rowAndNumber[0][1] = emptyCells[row][1];
        return rowAndNumber;
    }
}
