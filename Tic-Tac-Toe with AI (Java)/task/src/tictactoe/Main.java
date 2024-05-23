package tictactoe;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.printTicTacToe();

        boolean isGameOver = ticTacToe.isGameOver();
        while (!isGameOver) {
            String isSuccessfulMove;
            do {
                System.out.println("Enter the coordinates:");
                String coordinates = scanner.nextLine();
                isSuccessfulMove = ticTacToe.placeCell(coordinates);
            } while (isSuccessfulMove.equals("UNSUCCESSFUL"));

            ticTacToe.printTicTacToe(); // Prints the TicTacToe after a successful move
            ticTacToe.doesTheGameHaveAWinner(); // Verify whether there's a winner or it's a draw
            isGameOver = ticTacToe.isGameOver(); // Verify if game is over
        }
    }
}
