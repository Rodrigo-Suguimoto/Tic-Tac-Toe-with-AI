package tictactoe;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class AIPlayer {
    final static int MATRIX_SIZE = 3;
    final static int NUMBER_OF_CELLS_ALMOST_TO_WIN_OR_LOSE = 2;

    private static ArrayList<int[]> findEmptyCells(char[][] ticTacToe) {
        ArrayList<int[]> emptyCells = new ArrayList<>();

        for (int i = 0; i < ticTacToe.length; i++) {
            for (int j = 0; j < ticTacToe[i].length; j++) {
                if (ticTacToe[i][j] == ' ') {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        return emptyCells;
    }

    public static int[] getRandomCoordinates(char[][] ticTacToe) {
        ArrayList<int[]> emptyCells = findEmptyCells(ticTacToe);
        Random rand = new Random();
        int randomIndex = rand.nextInt(emptyCells.size());
        int[] randomCoordinates = emptyCells.get(randomIndex);

        return new int[]{randomCoordinates[0], randomCoordinates[1]};
    }

    public static Optional<int[]> planMove(char[][] ticTacToe, char player) {
        char oppositePlayer = player == 'X' ? 'O' : 'X';

        // For all the functions that scan the board, we first prioritize winning, then defending.
        Optional<int[]> coordinates = scanTicTacToeHorizontally(ticTacToe, player).isPresent()
                ? scanTicTacToeHorizontally(ticTacToe, player)
                : scanTicTacToeHorizontally(ticTacToe, oppositePlayer);
        if (coordinates.isPresent()) {
            return coordinates;
        }

        coordinates = scanTicTacToeVertically(ticTacToe, player).isPresent()
                ? scanTicTacToeVertically(ticTacToe, player)
                : scanTicTacToeVertically(ticTacToe, oppositePlayer);
        if (coordinates.isPresent()) {
            return coordinates;
        }

        coordinates = scanTicTacToeDiagonally(ticTacToe, player).isPresent()
                ? scanTicTacToeDiagonally(ticTacToe, player)
                : scanTicTacToeDiagonally(ticTacToe, oppositePlayer);
        if (coordinates.isPresent()) {
            return coordinates;
        }

        return Optional.empty();
    }

    private static Optional<int[]> scanTicTacToeVertically(char[][] ticTacToe, char player) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            int counter = 0;
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (ticTacToe[j][i] == player) counter++;
                if (counter == NUMBER_OF_CELLS_ALMOST_TO_WIN_OR_LOSE) {
                    // Find the index of the empty cell in the vertical line that has already two 'X' or 'O'
                    for (int z = 0; z < MATRIX_SIZE; z++) {
                        if (ticTacToe[z][i] == ' ') {
                            return Optional.of(new int[]{z, i});
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    private static Optional<int[]> scanTicTacToeHorizontally(char[][] ticTacToe, char player) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            int counter = 0;
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (ticTacToe[i][j] == player) counter++;
                if (counter == NUMBER_OF_CELLS_ALMOST_TO_WIN_OR_LOSE) {
                    // Find the index of the empty cell in the horizontal line that has already two 'X' or 'O'
                    for (int z = 0; z < MATRIX_SIZE; z++) {
                        if (ticTacToe[i][z] == ' ') {
                            return Optional.of(new int[]{i, z});
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    private static Optional<int[]> scanTicTacToeDiagonally(char[][] ticTacToe, char player) {
        int[][] firstDiagonal = {
                {0, 0},
                {1, 1},
                {2, 2}
        };

        int[][] secondDiagonal = {
                {0, 2},
                {1, 1},
                {2, 0}
        };

        Optional<int[]> firstDiagonalCoordinates = diagonalCheck(firstDiagonal, ticTacToe, player);
        if (firstDiagonalCoordinates.isPresent()) {
            return firstDiagonalCoordinates;
        }

        Optional<int[]> secondDiagonalCoordinates = diagonalCheck(secondDiagonal, ticTacToe, player);
        if (secondDiagonalCoordinates.isPresent()) {
            return secondDiagonalCoordinates;
        }

        return Optional.empty();
    }

    private static Optional<int[]> diagonalCheck(int[][] diagonalCoordinates, char[][] ticTacToe, char player) {
        int counter = 0;
        int[] emptyCellCoordinates = new int[0];
        boolean diagonalHasEmptyCell = false;

        for (int[] coordinate : diagonalCoordinates) {
            if (ticTacToe[coordinate[0]][coordinate[1]] == player) counter++;
            if (ticTacToe[coordinate[0]][coordinate[1]] == ' ') {
                emptyCellCoordinates = new int[]{coordinate[0], coordinate[1]};
                diagonalHasEmptyCell = true;
            }
        }

        if (counter == NUMBER_OF_CELLS_ALMOST_TO_WIN_OR_LOSE && diagonalHasEmptyCell) {
            return Optional.of(new int[]{emptyCellCoordinates[0], emptyCellCoordinates[1]});
        }
        return Optional.empty();
    }
}
