package tictactoe;

public class MiniMax {

    char maximizerPlayer, minimizerPlayer;

    public MiniMax(char maximizerPlayer, char minimizerPlayer) {
        this.maximizerPlayer = maximizerPlayer;
        this.minimizerPlayer = minimizerPlayer;
    }
    public int evaluateBoard(char[][] board) {
        // Checking for victory horizontally
        for (int row = 0; row < board.length; row++) {
            if (equals3(board[row][0], board[row][1], board[row][2])) {
                return getScore(board[row][0]);
            }
        }

        // Checking for victory vertically
        for (int col = 0; col < board.length; col++) {
            if (equals3(board[0][col], board[1][col], board[2][col])) {
                return getScore(board[0][col]);
            }
        }

        // Checking for victory on the diagonals
        if (equals3(board[0][0], board[1][1], board[2][2])) {
            return getScore(board[0][0]);
        }
        if (equals3(board[0][2], board[1][1], board[2][0])) {
            return getScore(board[0][2]);
        }

        // If none of them have won, then return 0 (Draw)
        return 0;
    }

    public int[] findBestMove(char[][] board) {
        int bestValue = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == ' ') {
                    board[row][col] = this.maximizerPlayer;
                    int moveValue = minimax(board, false);
                    board[row][col] = ' ';

                    if (moveValue > bestValue) {
                        bestMove = new int[]{row, col};
                        bestValue = moveValue;
                    }
                }
            }
        }

        return bestMove;
    }

    private int minimax(char[][] board, boolean isMax) {
        int score = evaluateBoard(board);

        if (score == 10 || score == -10 || !areThereAnyMovesLeft(board)) {
            return score;
        }

        if (isMax) {
            int bestValue = Integer.MIN_VALUE;
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    if (board[row][col] == ' ') {
                        board[row][col] = this.maximizerPlayer;
                        bestValue = Math.max(bestValue, minimax(board, false));
                        board[row][col] = ' ';
                    }
                }
            }
            return bestValue;
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    if (board[row][col] == ' ') {
                        board[row][col] = this.minimizerPlayer;
                        bestValue = Math.min(bestValue, minimax(board, true));
                        board[row][col] = ' ';
                    }
                }
            }
            return bestValue;
        }
    }

    private static boolean areThereAnyMovesLeft(char[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }

    private int getScore(char winnerPlayer) {
        if (winnerPlayer == this.maximizerPlayer) {
            return 10;
        } else {
            return -10;
        }
    }

    private boolean equals3(char a, char b, char c) {
        return a == b && b == c && a != ' ';
    }
}
