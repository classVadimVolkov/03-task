package task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TicTacToe {
    private static String[][] matrix = new String[3][3];

    public static void main(String[] args) throws IOException {
        showInitialView();
        System.out.println(getComputerSign());
    }

    private static void showInitialView() {
        System.out.println("It's Tic Tac Toe! You will enter 'o' or 'x'" + "\n" +
                "Then you should be enter ROW number(1, 2 or 3), where your sign must be." + "\n" +
                "Then you should be enter COLUMN number(1, 2 or 3), where your sign must be.");
        System.out.println(" " + " | " + " " + " | " + " " + "\n" +
                "---------" + "\n" +
                " " + " | " + " " + " | " + " " + "\n" +
                "---------" + "\n" +
                " " + " | " + " " + " | " + " ");
    }

    private static void showMatrix(String[][] matrix) {
        String[][] matrixForView = Arrays.stream(matrix).map(String[]::clone).toArray(String[][]::new);

        for (int i = 0; i < matrixForView.length; i++) {
            for (int j = 0; j < matrixForView[i].length; j++) {
                if (matrixForView[i][j] == null) {
                    matrixForView[i][j] = " ";
                }
            }
        }

        System.out.println(matrixForView[0][0] + " | " + matrixForView[0][1] + " | " + matrixForView[0][1] + "\n" +
                "---------" + "\n" +
                matrixForView[1][0] + " | " + matrixForView[1][1] + " | " + matrixForView[1][2] + "\n" +
                "---------" + "\n" +
                matrixForView[2][0] + " | " + matrixForView[2][1] + " | " + matrixForView[2][2]);
    }

    private static boolean hasMatrixMoreEmptyFields(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void printEnterSign() {
        System.out.println("Enter 'o' or 'x':");
    }

    private static String getUserSign() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String sign = reader.readLine();

            if(!sign.equalsIgnoreCase("o") && !sign.equalsIgnoreCase("x")) {
                System.out.println("Error sign! Enter 'o' or 'x'");
                sign = getUserSign();
            }

        return sign;
    }

    private static int getUserNumber() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int number = Integer.parseInt(reader.readLine());

        if(number != 1 && number != 2 && number != 3) {
            System.out.println("Error! Enter 1, 2 or 3");
            number = getUserNumber();
        }

        return number;
    }

    private static String getComputerSign() {
        int number = (int) (Math.random() * 2);
        return number == 0 ? "o" : "x";
    }
}
