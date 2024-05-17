package tictactoe;
import java.util.Arrays;
import java.util.Scanner;

class TicTacToe {

    final int MATRIX_SIZE = 3;
    protected int coordinate1;
    protected int coordinate2;
    protected char[][] ticTacToe = new char[MATRIX_SIZE][MATRIX_SIZE];

    public TicTacToe(String cells) {
        int charIndex = 0;

        for (int i = 0; i < ticTacToe.length; i++) {
            for (int j = 0; j < ticTacToe[i].length; j++) {
                ticTacToe[i][j] = cells.charAt(charIndex);
                charIndex++;
            }
        }
    }
    public void printTicTacToe() {
        System.out.println("---------");
        for (char[] cell : ticTacToe) {
            System.out.printf("| %s %s %s |\n", cell[0], cell[1], cell[2]);
        }
        System.out.println("---------");

        checkStateOfTheGame();
    }

    public String placeCell(String coordinates) {
        String[] coordinatesIntoArray = coordinates.split(" ");
        if (!areCoordinatesValidNumbers(coordinatesIntoArray)) return "UNSUCCESSFUL";
        if (!areCoordinatesInsideValidRange()) return "UNSUCCESSFUL";
        if (!isCellEmpty()) return "UNSUCCESSFUL";

        int fixedCoordinate1 = this.coordinate1 - 1;
        int fixedCoordinate2 = this.coordinate2 - 1;

        this.ticTacToe[fixedCoordinate1][fixedCoordinate2] = isXorO();

        return "SUCCESSFUL";
    }

    public boolean areCoordinatesInsideValidRange() {
        if (this.coordinate1 < 1 | this.coordinate1 > 3 | this.coordinate2 < 1 | this.coordinate2 > 3) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }

        return true;
    }

    private boolean isCellEmpty() {
        int fixedCoordinate1 = this.coordinate1 - 1;
        int fixedCoordinate2 = this.coordinate2 - 1;

        if (this.ticTacToe[fixedCoordinate1][fixedCoordinate2] == '_') {
            return true;
        }

        System.out.println("This cell is occupied! Choose another one!");
        return false;
    }

    private char isXorO() {
        int numberOfX = 0;
        int numberOfO = 0;

        for (int i = 0; i < this.ticTacToe.length; i++) {
            for (int j = 0; j < this.ticTacToe[i].length; j++) {
                if (this.ticTacToe[i][j] == 'X') {
                    numberOfX++;
                }

                if (this.ticTacToe[i][j] == 'O') {
                    numberOfO++;
                }
            }
        }

        if (numberOfX == numberOfO) {
            return 'X';
        }

        return 'O';
    }

    private boolean areCoordinatesValidNumbers(String[] coordinates) {
        try {
            this.coordinate1 = Integer.parseInt(coordinates[0]);
            this.coordinate2 = Integer.parseInt(coordinates[1]);
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
            return false;
        }
        return true;
    }

    private void checkStateOfTheGame() {
        if (hasWonAcross('X')) {
            System.out.println("X wins");
            return;
        }

        if (hasWonAcross('O')) {
            System.out.println("O wins");
            return;
        }

        if (hasWonUp('X')) {
            System.out.println("X wins");
            return;
        }

        if (hasWonUp('O')) {
            System.out.println("O wins");
            return;
        }
    }

    private boolean hasWonAcross(char player) {
        int repeatedMovements = 0;
        final int REPEATED_MOVEMENTS_TO_WIN = 3;
        for (int i = 0; i < this.ticTacToe.length; i++) {
            if (repeatedMovements == REPEATED_MOVEMENTS_TO_WIN) return true;
            repeatedMovements = 0; // Reset counter
            for (int j = 0; j < this.ticTacToe[i].length; j++) {
                if (this.ticTacToe[i][j] == player) repeatedMovements++;
            }
        }

        return false;
    }

    private boolean hasWonUp(char player) {
        final int REPEATED_MOVEMENTS_TO_WIN = 3;
        final int MATRIX_SIZE = 3;

        int repeat = 0;
        int j = 0;
        while (repeat < MATRIX_SIZE) {
            int repeatedMovements = 0;
            for (int i = 0; i < MATRIX_SIZE; i++) {
                if (this.ticTacToe[i][j] == player) repeatedMovements++;
            }
            if (repeatedMovements == REPEATED_MOVEMENTS_TO_WIN) return true;
            j++;
            repeat++;
        }

        return false;
    }

}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the cells:");
        String cells = scanner.nextLine();

        TicTacToe ticTacToe = new TicTacToe(cells);
        ticTacToe.printTicTacToe();

        String isSuccessfulMove;
        do {
            System.out.println("Enter the coordinates:");
            String coordinates = scanner.nextLine();
            isSuccessfulMove = ticTacToe.placeCell(coordinates);
        } while (isSuccessfulMove.equals("UNSUCCESSFUL"));

        ticTacToe.printTicTacToe();
    }
}
