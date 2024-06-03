package tictactoe;

import java.util.Scanner;

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

                    makeAMove(xPlayer, ticTacToe);
                    ticTacToe.printTicTacToe(); // Prints the TicTacToe after a successful move
                    ticTacToe.doesTheGameHaveAWinner(); // Verify whether there's a winner or it's a draw
                    isGameOver = ticTacToe.isGameOver(); // Verify if game is over

                    if (!isGameOver) {
                        makeAMove(oPlayer, ticTacToe);
                        ticTacToe.printTicTacToe(); // Prints the TicTacToe after a successful move
                        ticTacToe.doesTheGameHaveAWinner(); // Verify whether there's a winner or it's a draw
                        isGameOver = ticTacToe.isGameOver(); // Verify if game is over
                    }
                }
            }

        } while (!exitGame);

    }

    public static void makeAMove(String player, TicTacToe ticTacToe) {
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
                int[] randomCoordinate = AIPlayer.getRandomCoordinates(ticTacToe.getTicTacToe());
                String coordinates = String.format("%s %s", randomCoordinate[0], randomCoordinate[1]);
                ticTacToe.placeCell(coordinates);
                break;
        }
    }

}
