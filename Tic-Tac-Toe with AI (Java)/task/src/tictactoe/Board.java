package tictactoe;

import java.util.Arrays;

public class Board {

    final int MATRIX_SIZE = 3;
    private char[][] board = new char[MATRIX_SIZE][MATRIX_SIZE];
    private boolean isGameOver;
    private char winnerPlayer = ' ';

    public Board() {
        for (int i = 0; i < this.MATRIX_SIZE; i++) {
            Arrays.fill(this.board[i], ' ');
        }
    }

    public String placeMovement(int[] coordinates, char symbol) {
        if (!areCoordinatesInsideValidRange(coordinates)) return "UNSUCCESSFUL";
        if (!isCellEmpty(coordinates)) return "UNSUCCESSFUL";
        int coordinate1 = coordinates[0];
        int coordinate2 = coordinates[1];

        this.board[coordinate1][coordinate2] = symbol;
        return "SUCCESSFUL";
    }

    // Verifies whether the cell the player is trying to fill is empty
    private boolean isCellEmpty(int[] coordinates) {
        int coordinate1 = coordinates[0];
        int coordinate2 = coordinates[1];

        if (this.board[coordinate1][coordinate2] == ' ') {
            return true;
        } else {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
    }

    private boolean areCoordinatesInsideValidRange(int[] coordinates) {
        int coordinate1 = coordinates[0];
        int coordinate2 = coordinates[1];

        if (coordinate1 >=0 & coordinate1 <= 2 & coordinate2 >= 0 & coordinate2 <= 2) {
            return true;
        } else {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }
    }
}
