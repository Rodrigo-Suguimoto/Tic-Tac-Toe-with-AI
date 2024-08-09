package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class AIPlayer {
    final static int MATRIX_SIZE = 3;
    final static int NUMBER_OF_CELLS_ALMOST_TO_WIN_OR_LOSE = 2;

    private static ArrayList<int[]> findEmptyCells(char[][] ticTacToe) {
        ArrayList<int[]> emptyCells = new ArrayList<>();

        for (int i = 0; i < ticTacToe.length; i++) {
            for (int j = 0; j < ticTacToe[i].length; j++) {
                if (ticTacToe[i][j] == ' ') {
                    emptyCells.add(new int[]{i + 1, j + 1}); // Adding 1 to adapt coordinates to the TicTacToe
                }
            }
        }

        return emptyCells;
    }

    public static String getRandomCoordinates(char[][] ticTacToe) {
        ArrayList<int[]> emptyCells = findEmptyCells(ticTacToe);
        Random rand = new Random();
        int randomIndex = rand.nextInt(emptyCells.size());
        int[] randomCoordinates = emptyCells.get(randomIndex);

        return String.format("%s %s", randomCoordinates[0], randomCoordinates[1]);
    }

    public static String planMove(char[][] ticTacToe, char player) {
        char oppositePlayer = player == 'X' ? 'O' : 'X';

        String horizontalCoordinates = scanTicTacToeHorizontally(ticTacToe, player).isEmpty()
                ? scanTicTacToeHorizontally(ticTacToe, oppositePlayer)
                : scanTicTacToeHorizontally(ticTacToe, player);
        if (!horizontalCoordinates.isEmpty()) {
            return horizontalCoordinates;
        }

        String verticalCoordinates = scanTicTacToeVertically(ticTacToe, player).isEmpty()
                ? scanTicTacToeVertically(ticTacToe, oppositePlayer)
                : scanTicTacToeVertically(ticTacToe, player);
        if (!verticalCoordinates.isEmpty()) {
            return verticalCoordinates;
        }

        String diagonalCoordinates = scanTicTacToeDiagonally(ticTacToe, player).isEmpty()
                ? scanTicTacToeDiagonally(ticTacToe, oppositePlayer)
                : scanTicTacToeDiagonally(ticTacToe, player);
        if (!diagonalCoordinates.isEmpty()) {
            return diagonalCoordinates;
        }

        return "";
    }

    private static String scanTicTacToeVertically(char[][] ticTacToe, char player) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            int counter = 0;
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (ticTacToe[j][i] == player) counter++;
                if (counter == NUMBER_OF_CELLS_ALMOST_TO_WIN_OR_LOSE) {
                    // Find the index of the empty cell in the vertical line that has already two 'X' or 'O'
                    for (int z = 0; z < MATRIX_SIZE; z++) {
                        if (ticTacToe[z][i] == ' ') {
                            return String.format("%s %s", z + 1, i + 1);
                        }
                    }
                }
            }
        }
        return "";
    }

    private static String scanTicTacToeHorizontally(char[][] ticTacToe, char player) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            int counter = 0;
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (ticTacToe[i][j] == player) counter++;
                if (counter == NUMBER_OF_CELLS_ALMOST_TO_WIN_OR_LOSE) {
                    // Find the index of the empty cell in the horizontal line that has already two 'X' or 'O'
                    for (int z = 0; z < MATRIX_SIZE; z++) {
                        if (ticTacToe[i][z] == ' ') {
                            return String.format("%s %s", i + 1, z + 1);
                        }
                    }
                }
            }
        }
        return "";
    }

    private static String scanTicTacToeDiagonally(char[][] ticTacToe, char player) {
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

        String firstDiagonalCoordinates = diagonalCheck(firstDiagonal, ticTacToe, player);
        if (!firstDiagonalCoordinates.isEmpty()) {
            return firstDiagonalCoordinates;
        }

        String secondDiagonalCoordinates = diagonalCheck(secondDiagonal, ticTacToe, player);
        if (!secondDiagonalCoordinates.isEmpty()) {
            return secondDiagonalCoordinates;
        }

        return "";
    }

    private static String diagonalCheck(int[][] diagonalCoordinates, char[][] ticTacToe, char player) {
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
            return String.format("%s %s", emptyCellCoordinates[0] + 1, emptyCellCoordinates[1] + 1);
        }
        return "";
    }

    public static String bestMove(TicTacToe ticTacToe, char xOrO) {
        double bestScore = Double.NEGATIVE_INFINITY;
        char[][] board = ticTacToe.getTicTacToe();
        String movement = "";

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = xOrO;
                    double score = minimax(board, xOrO, true);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        movement = String.format("%s %s", i + 1, j + 1);
                    }
                }
            }
        }

        return movement;
    }

    // Minimax algorithm that uses recursion to get the best movement
    public static double minimax(char[][] board, char xOrO, boolean isMaximizing) {
        boolean isGameOver = ticTacToe.isGameOver();
        double score = 0;
        if (isGameOver) {
            char winnerPlayer = ticTacToe.getWinnerPlayer();
            if (winnerPlayer == xOrO) {
                score = 10; // Win
            } else if (winnerPlayer == ' ') { // Tie
                score = 0;
            } else {
                score = -10; // Loss
            }
            return score;
        }

        if (isMaximizing) {
            double bestScore = Double.NEGATIVE_INFINITY;
            char[][] board = ticTacToe.getTicTacToe();
            char oppositePlayer = xOrO == 'X' ? 'O' : 'X';

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = xOrO;
                        score = minimax(ticTacToe, oppositePlayer, false);
                        board[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            double bestScore = Double.POSITIVE_INFINITY;
            char[][] board = ticTacToe.getTicTacToe();
            char oppositePlayer = xOrO == 'X' ? 'O' : 'X';

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = xOrO;
                        score = minimax(ticTacToe, oppositePlayer, true);
                        board[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

}

class TicTacToe {

    final int MATRIX_SIZE = 3;
    protected int coordinate1;
    protected int coordinate2;
    private boolean isGameOver;
    private char[][] ticTacToe = new char[MATRIX_SIZE][MATRIX_SIZE];
    private char winnerPlayer = ' ';

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

    public char[][] getTicTacToe() {
        return this.ticTacToe;
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

        if (this.ticTacToe[fixedCoordinate1][fixedCoordinate2] == ' ') {
            return true;
        }

        System.out.println("This cell is occupied! Choose another one!");
        return false;
    }

    protected char isXorO() {
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

        if (isDraw()) {
            finishGame();
            return true;
        }

        return false;
    }

    protected boolean isDraw() {
        int MAXIMUM_NUMBER_OF_CELLS = 9;
        int counter = 0;

        for (int i = 0; i < this.ticTacToe.length; i++) {
            for (int j = 0; j < this.ticTacToe[i].length; j++) {
                if (this.ticTacToe[i][j] != ' ') {
                    counter++;
                }
            }
        }

        return counter == MAXIMUM_NUMBER_OF_CELLS;
    }

    private void finishGame(char player) {
        System.out.printf("%s wins\n", player);
        this.winnerPlayer = player;
        this.isGameOver = true;
    }

    private void finishGame() {
        System.out.println("Draw");
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

    public char getWinnerPlayer() {
        return this.winnerPlayer;
    }

}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exitGame = false;
        do {
            GameMenu gameMenu = new GameMenu();

            // Loop until get a valid user input
            boolean isValidCommand = false;
            do {
                System.out.println("Input command:");
                String userCommand = scanner.nextLine();
                gameMenu.processUserCommand(userCommand);
                isValidCommand = gameMenu.isValidCommand;
            } while (!isValidCommand);

            exitGame = gameMenu.isExitGame();
            // If user inputs "exit", do nothing
            if (!exitGame) {
                TicTacToe ticTacToe = new TicTacToe();
                ticTacToe.printTicTacToe();

                boolean isGameOver = ticTacToe.isGameOver();
                while (!isGameOver) {
                    String xPlayer = gameMenu.getXPlayer();
                    String oPlayer = gameMenu.getOPlayer();

                    makeAMove(xPlayer, ticTacToe, 'X');
                    isGameOver = checkIfGameIsOver(ticTacToe); // Verify if game is over

                    if (!isGameOver) {
                        makeAMove(oPlayer, ticTacToe, 'O');
                        isGameOver = checkIfGameIsOver(ticTacToe); // Verify if game is over
                    }
                }
            }

        } while (!exitGame);

    }

    public static void makeAMove(String player, TicTacToe ticTacToe, char xOrO) {
        Scanner scanner = new Scanner(System.in);

        switch (player) {
            case "user":
                String isSuccessfulMove;
                do {
                    System.out.println("Enter the coordinates:");
                    String coordinates = scanner.nextLine();
                    isSuccessfulMove = ticTacToe.placeCell(coordinates);
                } while (isSuccessfulMove.equals("UNSUCCESSFUL"));
                break;
            case "easy":
                System.out.println("Making move level \"easy\"");
                ticTacToe.placeCell(AIPlayer.getRandomCoordinates(ticTacToe.getTicTacToe()));
                break;
            case "medium":
                System.out.println("Making move level \"medium\"");
                String plannedMovement = AIPlayer.planMove(ticTacToe.getTicTacToe(), xOrO);
                if (plannedMovement.isEmpty()) {
                    ticTacToe.placeCell(AIPlayer.getRandomCoordinates(ticTacToe.getTicTacToe()));
                } else {
                    ticTacToe.placeCell(plannedMovement);
                }
            case "hard":
                System.out.println("Making move level \"hard\"");
                String bestMove = AIPlayer.bestMove(ticTacToe, xOrO);
                ticTacToe.placeCell(bestMove);
        }
    }

    public static boolean checkIfGameIsOver(TicTacToe ticTacToe) {
        ticTacToe.printTicTacToe(); // Prints the TicTacToe after a successful move
        ticTacToe.doesTheGameHaveAWinner(); // Verify whether there's a winner or it's a draw
        return ticTacToe.isGameOver(); // Verify if game is over
    }

}
