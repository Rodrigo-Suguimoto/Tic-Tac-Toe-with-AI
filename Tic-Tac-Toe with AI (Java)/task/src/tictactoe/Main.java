package tictactoe;
import java.util.Arrays;
import java.util.Scanner;

class TicTacToe {

    final int MATRIX_SIZE = 3;
    protected int coordinate1;
    protected int coordinate2;
    protected String[][] ticTacToe = new String[MATRIX_SIZE][MATRIX_SIZE];

    public TicTacToe(String cells) {
        int charIndex = 0;

        for (int i = 0; i < ticTacToe.length; i++) {
            for (int j = 0; j < ticTacToe[i].length; j++) {
                ticTacToe[i][j] = String.valueOf(cells.charAt(charIndex));
                charIndex++;
            }
        }
    }

    public String[][] getTicTacToe() {
        return this.ticTacToe;
    }

    public void printTicTacToe() {
        System.out.println("---------");
        for (String[] cell : ticTacToe) {
            System.out.printf("| %s %s %s |\n", cell[0], cell[1], cell[2]);
        }
        System.out.println("---------");
    }

    public void placeCell(String coordinates) {
        String[] coordinatesIntoArray = coordinates.split(" ");
        validateCoordinates(coordinatesIntoArray);
    }

    private void validateCoordinates(String[] coordinates) {
        try {
            this.coordinate1 = Integer.parseInt(coordinates[0]);
            this.coordinate2 = Integer.parseInt(coordinates[1]);
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
        }
    }

}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the cells:");
        String cells = scanner.nextLine();

        TicTacToe ticTacToe = new TicTacToe(cells);
        ticTacToe.printTicTacToe();

        System.out.println("Enter the coordinates:");
        String coordinates = scanner.nextLine();
        ticTacToe.placeCell(coordinates);
    }
}
