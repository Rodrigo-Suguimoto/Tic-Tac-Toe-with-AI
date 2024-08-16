package tictactoe;

public class TicTacToe {

    private Board board = new Board();

    public void printTicTacToe() {
        System.out.println("---------");
        for (char[] cell : this.board) {
            System.out.printf("| %s %s %s |\n", cell[0], cell[1], cell[2]);
        }
        System.out.println("---------");
    }

    public boolean isGameOver() {
        return this.board.isGameOver();
    }

    public String placeMovement(int[] coordinates, char symbol) {
        return this.board.placeMovement(coordinates, symbol);
    }

}
