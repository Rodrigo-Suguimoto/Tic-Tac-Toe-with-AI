package tictactoe;
import java.util.ArrayList;
import java.util.Random;

public class AIPlayer {

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

    public static int[] getRandomCoordinates(char[][] ticTacToe) {
        ArrayList<int[]> emptyCells = findEmptyCells(ticTacToe);
        Random rand = new Random();
        int randomIndex = rand.nextInt(emptyCells.size());

        return emptyCells.get(randomIndex);
    }

}
