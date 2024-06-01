package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exitGame = false;
        do {
            System.out.println("Input command:");
            String userCommand = scanner.nextLine();
            GameMenu gameMenu = new GameMenu();
            gameMenu.processUserCommand(userCommand);
            exitGame = gameMenu.isExitGame();

            if (!exitGame) {
                TicTacToe ticTacToe = new TicTacToe();
                ticTacToe.printTicTacToe();

                boolean isGameOver = ticTacToe.isGameOver();
                while (!isGameOver) {
                    if (ticTacToe.isXorO() == 'X') {
                        String isSuccessfulMove;
                        do {
                            System.out.println("Enter the coordinates:");
                            String coordinates = scanner.nextLine();
                            isSuccessfulMove = ticTacToe.placeCell(coordinates);
                        } while (isSuccessfulMove.equals("UNSUCCESSFUL"));
                    }
                    ticTacToe.printTicTacToe(); // Prints the TicTacToe after a successful move
                    ticTacToe.doesTheGameHaveAWinner(); // Verify whether there's a winner or it's a draw
                    isGameOver = ticTacToe.isGameOver(); // Verify if game is over

                    if (ticTacToe.isXorO() == 'O' && !isGameOver) {
                        System.out.println("Making move level \"easy\"");
                        int[] randomCoordinate = AIPlayer.getRandomCoordinates(ticTacToe.getTicTacToe());
                        String coordinates = String.format("%s %s", randomCoordinate[0], randomCoordinate[1]);
                        ticTacToe.placeCell(coordinates);

                        ticTacToe.printTicTacToe(); // Prints the TicTacToe after a successful move
                        ticTacToe.doesTheGameHaveAWinner(); // Verify whether there's a winner or it's a draw
                        isGameOver = ticTacToe.isGameOver(); // Verify if game is over
                    }
                }
            }

        } while (!exitGame);

    }

}
