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

    // Constructor that accepts an existing 3x3 matrix
    public Board(char[][] board) {
        if (board.length == MATRIX_SIZE && board[0].length == MATRIX_SIZE) {
            // Create a deep copy of the board
            this.board = new char[MATRIX_SIZE][MATRIX_SIZE];
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[i].length; j++) {
                    this.board[i][j] = board[i][j];
                }
            }
        } else {
            throw new IllegalArgumentException("The board must be a 3x3 matrix.");
        }
    }

    public char[][] getBoard() {
        return this.board;
    }

    public String placeMovement(int[] coordinates, char symbol) {
        if (!areCoordinatesInsideValidRange(coordinates)) return "UNSUCCESSFUL";
        if (!isCellEmpty(coordinates)) return "UNSUCCESSFUL";
        int coordinate1 = coordinates[0];
        int coordinate2 = coordinates[1];

        this.board[coordinate1][coordinate2] = symbol;
        return "SUCCESSFUL";
    }

    public void removeSymbol(int[] coordinates) {
        int coordinate1 = coordinates[0];
        int coordinate2 = coordinates[1];
        this.board[coordinate1][coordinate2] = ' ';
    }

    public boolean isGameOver() {
        return this.isGameOver;
    }

    public char getWinnerPlayer() {
        return this.winnerPlayer;
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

        if (coordinate1 >=0 && coordinate1 <= 2 && coordinate2 >= 0 && coordinate2 <= 2) {
            return true;
        } else {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }
    }

    private boolean hasWonInHorizontal() {
        for (int i = 0; i < this.MATRIX_SIZE; i++) {
            if (equals3(this.board[i][0], this.board[i][1], this.board[i][2])) {
                this.winnerPlayer = this.board[i][0];
                return true;
            }
        }
        return false;
    }

    private boolean hasWonInVertical() {
        for (int i = 0; i < this.MATRIX_SIZE; i++) {
            if (equals3(this.board[0][i], this.board[1][i], this.board[2][i])) {
                this.winnerPlayer = this.board[0][i];
                return true;
            }
        }
        return false;
    }

    private boolean hasWonInDiagonal() {
        if (equals3(this.board[0][0], this.board[1][1], this.board[2][2])) {
            this.winnerPlayer = this.board[0][0];
            return true;
        } else if (equals3(this.board[0][2], this.board[1][1], this.board[2][0])) {
            this.winnerPlayer = this.board[0][2];
            return true;
        }
        return false;
    }

    private boolean checkIfItsDraw() {
        int counterOfEmptyCells = 0;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (this.board[i][j] == ' ') {
                    counterOfEmptyCells++;
                }
            }
        }
        return counterOfEmptyCells == 0;
    }

    public void verifyIfGameIsOver() {
        if (hasWonInHorizontal() || hasWonInDiagonal() || hasWonInVertical() || checkIfItsDraw()) {
            this.isGameOver = true;
        }
    }

    private boolean equals3(char a, char b, char c) {
        return a == b && b == c && a != ' ';
    }
}
