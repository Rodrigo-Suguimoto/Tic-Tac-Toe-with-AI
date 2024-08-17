package tictactoe;

public class TicTacToe {

    private Board board = new Board();

    public void printTicTacToe() {
        System.out.println("---------");
        for (char[] cell : this.board.getBoard()) {
            System.out.printf("| %s %s %s |\n", cell[0], cell[1], cell[2]);
        }
        System.out.println("---------");
    }

    public char[][] getBoard() {
        return this.board.getBoard();
    }

    public boolean isGameOver() {
        return this.board.isGameOver();
    }

    public String placeMovement(int[] coordinates, char symbol) {
        return this.board.placeMovement(coordinates, symbol);
    }

    public void verifyIfGameIsOver() {
        this.board.verifyIfGameIsOver();
        if (this.board.isGameOver()) {
            char winnerPlayer = this.board.getWinnerPlayer(); // Only gets winner player when the game is over
            if (winnerPlayer == ' ') { // If the game is over and winnerPlayer is still an empty char, it's a draw
                System.out.println("Draw");
            } else {
                System.out.printf("%s wins\n", winnerPlayer);
            }
        }
    }

}
