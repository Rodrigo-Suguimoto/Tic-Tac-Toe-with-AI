package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static int[] getBestMove(Board board, char symbol) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = Integer.MIN_VALUE;

        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                int[] coordinates = new int[]{row, col};
                if (board.isCellEmpty(coordinates)) {
                    board.placeMovement(coordinates, symbol);
                    int moveScore = minimax(board, symbol, false);
                    board.removeSymbol(coordinates);
                    if (moveScore > bestScore) {
                        bestMove = new int[]{row, col};
                        bestScore = moveScore;
                    }
                }
            }
        }
        return bestMove;
    }

    private static int minimax(Board board, char symbol, boolean isMaximizer) {
        Optional<Integer> boardValue = evaluateBoard(board, symbol, isMaximizer);
        if (boardValue.isPresent()) {
            System.out.println(boardValue.get());
            return boardValue.get();
        }

        if (isMaximizer) {
            int highestVal = Integer.MIN_VALUE;
            for (int row = 0; row < board.getBoardSize(); row++) {
                for (int col = 0; col < board.getBoardSize(); col++) {
                    int[] coordinates = new int[]{row, col};
                    if (board.isCellEmpty(coordinates)) {
                        board.placeMovement(coordinates, symbol);
                        char opponentSymbol = symbol == 'X' ? 'O' : 'X';
                        highestVal = Math.max(highestVal, minimax(board, opponentSymbol, false));
                        board.removeSymbol(coordinates);
                    }
                }
            }
            return highestVal;
        } else {
            int lowestVal = Integer.MAX_VALUE;
            for (int row = 0; row < board.getBoardSize(); row++) {
                for (int col = 0; col < board.getBoardSize(); col++) {
                    int[] coordinates = new int[]{row, col};
                    if (board.isCellEmpty(coordinates)) {
                        board.placeMovement(coordinates, symbol);
                        char opponentSymbol = symbol == 'X' ? 'O' : 'X';
                        lowestVal = Math.min(lowestVal, minimax(board, opponentSymbol, true));
                        board.removeSymbol(coordinates);
                    }
                }
            }
            return lowestVal;
        }
    }

    private static Optional<Integer> evaluateBoard(Board board, char symbol, boolean isMaximizer) {
        char winnerPlayer;
        char[][] ticTacToe = board.getBoard();

        // Check for winner in Horizontal
        for (int i = 0; i < ticTacToe.length; i++) {
            if (board.equals3(ticTacToe[i][0], ticTacToe[i][1], ticTacToe[i][2])) {
                winnerPlayer = ticTacToe[i][0];
                if (winnerPlayer == symbol & isMaximizer) {
                    return Optional.of(10);
                } else if (winnerPlayer == symbol & !isMaximizer) {
                    return Optional.of(-10);
                }
            }
        }

        // Check for winner in Vertical
        for (int j = 0; j < ticTacToe.length; j++) {
            if (board.equals3(ticTacToe[0][j], ticTacToe[1][j], ticTacToe[2][j])) {
                winnerPlayer = ticTacToe[0][j];
                if (winnerPlayer == symbol & isMaximizer) {
                    return Optional.of(10);
                } else if (winnerPlayer == symbol & !isMaximizer) {
                    return Optional.of(-10);
                }
            }
        }

        // Check for winner in diagonal
        if (board.equals3(ticTacToe[0][0], ticTacToe[1][1], ticTacToe[2][2])) {
            winnerPlayer = ticTacToe[0][0];
            if (winnerPlayer == symbol & isMaximizer) {
                return Optional.of(10);
            } else if (winnerPlayer == symbol & !isMaximizer) {
                return Optional.of(-10);
            }
        }

        if (board.equals3(ticTacToe[0][2], ticTacToe[1][1], ticTacToe[2][0])) {
            winnerPlayer = ticTacToe[0][2];
            if (winnerPlayer == symbol & isMaximizer) {
                return Optional.of(10);
            } else if (winnerPlayer == symbol & !isMaximizer) {
                return Optional.of(-10);
            }
        }

        if (board.checkIfItsDraw()) {
            return Optional.of(0);
        }

        return Optional.empty();
    }

}
