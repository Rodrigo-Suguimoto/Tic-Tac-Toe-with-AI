package tictactoe;
import java.util.Arrays;
import java.util.Scanner;

class TicTacToe {

    final int MATRIX_SIZE = 3;
    protected int coordinate1;
    protected int coordinate2;
    private boolean isGameOver;
    protected char[][] ticTacToe = new char[MATRIX_SIZE][MATRIX_SIZE];

    public TicTacToe(String cells) {
        int charIndex = 0;

        for (int i = 0; i < this.ticTacToe.length; i++) {
            for (int j = 0; j < this.ticTacToe[i].length; j++) {
                this.ticTacToe[i][j] = cells.charAt(charIndex);
                charIndex++;
            }
        }
    }

    public TicTacToe() {
        for (int i = 0; i < this.ticTacToe.length; i++) {
            Arrays.fill(this.ticTacToe[i], ' ');
        }
    }

    public boolean isGameOver() {
        return this.isGameOver;
    }

    public void printTicTacToe() {
        System.out.println("---------");
        for (char[] cell : ticTacToe) {
            System.out.printf("| %s %s %s |\n", cell[0], cell[1], cell[2]);
        }
        System.out.println("---------");

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

    protected boolean doesTheGameHaveAWinner() {
        if (hasWonAcross('X')) {
            finishGame('X');
            return true;
        }

        if (hasWonAcross('O')) {
            finishGame('O');
            return true;
        }

        if (hasWonUp('X')) {
            finishGame('X');
            return true;
        }

        if (hasWonUp('O')) {
            finishGame('O');
            return true;
        }

        if (hasWonDiagonally('X')) {
            finishGame('X');
            return true;
        }

        if (hasWonDiagonally('O')) {
            finishGame('O');
            return true;
        }

        return false;
    }

    protected void isTicTacToeComplete() {
        int MAXIMUM_NUMBER_OF_CELLS = 9;
        int counter = 0;

        for (int i = 0; i < this.ticTacToe.length; i++) {
            for (int j = 0; j < this.ticTacToe[i].length; j++) {
                if (this.ticTacToe[i][j] != '_') {
                    counter++;
                }
            }
        }

        if (counter == MAXIMUM_NUMBER_OF_CELLS) {
            System.out.println("Draw");
        } else {
            System.out.println("Game not finished");
        }
    }

    private void finishGame(char player) {
        System.out.printf("%s wins", player);
        this.isGameOver = true;
    }

    private boolean hasWonAcross(char player) {
        int repeatedMovements = 0;
        final int REPEATED_MOVEMENTS_TO_WIN = 3;

        for (int i = 0; i < this.ticTacToe.length; i++) {
            repeatedMovements = 0;
            for (int j = 0; j < this.ticTacToe[i].length; j++) {
                if (this.ticTacToe[i][j] == player) repeatedMovements++;
                if (repeatedMovements == REPEATED_MOVEMENTS_TO_WIN) return true;
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

    private boolean hasWonDiagonally(char player) {
        return (this.ticTacToe[0][0] == player & this.ticTacToe[1][1] == player & this.ticTacToe[2][2] == player) |
                (this.ticTacToe[0][2] == player & this.ticTacToe[1][1] == player & this.ticTacToe[2][0] == player);
    }

}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.printTicTacToe();
        if (ticTacToe.doesTheGameHaveAWinner()) return;
        if (ticTacToe.isGameOver()) return;

        String isSuccessfulMove;
        do {
            System.out.println("Enter the coordinates:");
            String coordinates = scanner.nextLine();
            isSuccessfulMove = ticTacToe.placeCell(coordinates);
        } while (isSuccessfulMove.equals("UNSUCCESSFUL"));

        ticTacToe.printTicTacToe();
        if (ticTacToe.doesTheGameHaveAWinner()) return;
        ticTacToe.isTicTacToeComplete();
    }
}
