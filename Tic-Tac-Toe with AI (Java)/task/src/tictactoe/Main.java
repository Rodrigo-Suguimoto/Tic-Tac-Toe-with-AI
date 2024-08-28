package tictactoe;

import java.util.*;

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
            // If user inputs "exit", finish the program
            if (!exitGame) {
                TicTacToe ticTacToe = new TicTacToe();
                ticTacToe.printTicTacToe();

                boolean isGameOver = ticTacToe.isGameOver();
                while (!isGameOver) {
                    String xPlayer = gameMenu.getXPlayer(); // It may be human or AI
                    String oPlayer = gameMenu.getOPlayer(); // It may be human or AI

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
                    int[] coordinates = coordinatesInZeroIndex(scanner.nextLine());
                    isSuccessfulMove = ticTacToe.placeMovement(coordinates, xOrO);
                } while (isSuccessfulMove.equals("UNSUCCESSFUL"));
                break;
            case "easy":
                System.out.println("Making move level \"easy\"");
                char[][] board = ticTacToe.getBoard();
                ticTacToe.placeMovement(AIPlayer.getRandomCoordinates(board), xOrO);
                break;
            case "medium":
                System.out.println("Making move level \"medium\"");
                board = ticTacToe.getBoard();
                Optional<int[]> plannedMovement = AIPlayer.planMove(board, xOrO);
                if (plannedMovement.isPresent()) {
                    int[] coordinates = plannedMovement.get();
                    ticTacToe.placeMovement(coordinates, xOrO);
                } else {
                    ticTacToe.placeMovement(AIPlayer.getRandomCoordinates(board), xOrO);
                }
                break;
            case "hard":
                System.out.println("Making move level \"hard\"");
                char oppositePlayer = xOrO == 'X' ? 'O' : 'X';
                MiniMax minimax = new MiniMax(xOrO, oppositePlayer);
                board = ticTacToe.getBoard();

                int[] bestMove = minimax.findBestMove(board);
                ticTacToe.placeMovement(bestMove, xOrO);
                break;
        }
    }

    public static boolean checkIfGameIsOver(TicTacToe ticTacToe) {
        ticTacToe.printTicTacToe(); // Prints the TicTacToe after a successful move
        ticTacToe.verifyIfGameIsOver(); // Verify whether there's a winner or it's a draw
        return ticTacToe.isGameOver(); // Verify if game is over
    }

    public static int[] coordinatesInZeroIndex(String userCommand) {
        String[] parameters = userCommand.trim().split(" ");

        if (parameters.length != 2) {
            System.out.println("Error: please input exactly two numbers. Example: 1 2");
            return null;
        }

        int coordinate1 = 0;
        int coordinate2 = 0;
        try {
            coordinate1 = Integer.parseInt(parameters[0]) - 1;
            coordinate2 = Integer.parseInt(parameters[1]) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Error: please input numbers from 1 to 3 with a space between. Example: 1 2");
            return null;
        }

        return new int[]{coordinate1, coordinate2};
    }

}
