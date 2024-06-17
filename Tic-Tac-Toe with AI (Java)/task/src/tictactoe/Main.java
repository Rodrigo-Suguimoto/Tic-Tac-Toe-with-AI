package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.List;

class GameMenu {

    protected boolean isValidCommand;
    protected boolean exitGame;
    protected String xPlayer;
    protected String oPlayer;

    public void processUserCommand(String userCommand) {
        String[] parameters = userCommand.split(" ");
        final List<String> VALID_PLAYERS = Arrays.asList("user", "easy", "medium");

        if (parameters[0].equals("start")) {
            if (parameters.length != 3) {
                invalidCommand();
                return;
            }

            String xPlayer = parameters[1];
            String oPlayer = parameters[2];
            if (
                    !VALID_PLAYERS.contains(xPlayer) || !VALID_PLAYERS.contains(oPlayer)
            ) {
                invalidCommand();
                return;
            }

            this.isValidCommand = true;
            this.xPlayer = xPlayer;
            this.oPlayer = oPlayer;
            return;
        }

        if (parameters[0].equals("exit") && parameters.length == 1) {
            this.isValidCommand = true;
            this.exitGame = true;
        }
    }

    private void invalidCommand() {
        System.out.println("Bad parameters!");
        this.isValidCommand = false;
    }

    public boolean isValidCommand() {
        return this.isValidCommand;
    }

    public boolean isExitGame() { return this.exitGame; }

    public String getXPlayer() { return this.xPlayer; }

    public String getOPlayer() { return this.oPlayer; }

}

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


    public static String planMove(char[][] ticTacToe) {
        String horizontalCoordinates = scanTicTacToeHorizontally(ticTacToe);
        if (!horizontalCoordinates.isEmpty()) {
            return horizontalCoordinates;
        }

        String verticalCoordinates = scanTicTacToeVertically(ticTacToe);
        if (!verticalCoordinates.isEmpty()) {
            return verticalCoordinates;
        }

        return "";
    }

    private static String scanTicTacToeVertically(char[][] ticTacToe) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            int counterOfX = 0;
            int counterOfO = 0;
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (ticTacToe[j][i] == 'X') counterOfX++;
                if (ticTacToe[j][i] == 'O') counterOfO++;

                if (counterOfX == NUMBER_OF_CELLS_ALMOST_TO_WIN_OR_LOSE || counterOfO == NUMBER_OF_CELLS_ALMOST_TO_WIN_OR_LOSE) {
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

    private static String scanTicTacToeHorizontally(char[][] ticTacToe) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            int counterOfX = 0;
            int counterOfO = 0;
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (ticTacToe[i][j] == 'X') counterOfX++;
                if (ticTacToe[i][j] == 'O') counterOfO++;

                if (counterOfX == NUMBER_OF_CELLS_ALMOST_TO_WIN_OR_LOSE || counterOfO == NUMBER_OF_CELLS_ALMOST_TO_WIN_OR_LOSE) {
                    // Find the index of the empty cell in the horizontal line that has already two 'X'
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

}

class TicTacToe {

    final int MATRIX_SIZE = 3;
    protected int coordinate1;
    protected int coordinate2;
    private boolean isGameOver;
    private char[][] ticTacToe = new char[MATRIX_SIZE][MATRIX_SIZE];

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

    public char[][] getTicTacToe() { return this.ticTacToe; }

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
                String plannedMovement = AIPlayer.planMove(ticTacToe.getTicTacToe());
                if (plannedMovement.isEmpty()) {
                    ticTacToe.placeCell(AIPlayer.getRandomCoordinates(ticTacToe.getTicTacToe()));
                } else {
                    ticTacToe.placeCell(plannedMovement);
                }
        }
    }

    public static boolean checkIfGameIsOver(TicTacToe ticTacToe) {
        ticTacToe.printTicTacToe(); // Prints the TicTacToe after a successful move
        ticTacToe.doesTheGameHaveAWinner(); // Verify whether there's a winner or it's a draw
        return ticTacToe.isGameOver(); // Verify if game is over
    }

}
