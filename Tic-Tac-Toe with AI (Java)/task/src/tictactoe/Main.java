package tictactoe;
import java.util.Scanner;

class TicTacToe {

    String[][] ticTacToe;

    public TicTacToe(String cells) {
        for (int i = 0; i < cells.length(); i = i + 3) {
            System.out.println(cells.substring(i, i + 3));
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the cells:");
        String cells = scanner.nextLine();

        TicTacToe ticTacToe = new TicTacToe(cells);
    }
}
