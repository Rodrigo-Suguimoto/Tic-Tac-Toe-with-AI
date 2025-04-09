# Tic Tac Toe with AI

Play Tic-Tac-Toe against a human opponent or challenge the AI in three difficulty modes: easy, medium, and hard.

1. The easy AI makes only random moves.
2. The medium AI is more sophisticated, capable of defending against threats and seizing opportunities to win.
3. The hard AI is unbeatable—you can only lose or draw. It uses the `minimax algorithm`, a powerful technique in game development that recursively evaluates all possible moves and counter-moves to select the optimal choice.

## Starting the game
To start the game, run `Main.java` located at `Tic-Tac-Toe-with-AI/Tic-Tac-Toe with AI (Java)/task/src/tictactoe/Main.java`. When prompted, enter a command like `start user hard`, which will let you play as X against a hard AI opponent as O.

- Player X always goes first after the "start" command, followed by player O. For example, typing `start hard user` means the hard AI plays first. You can choose `easy`, `medium`, or `hard` for the AI difficulty.
- For human vs. human gameplay, enter `start user user`. You can even watch AI opponents compete against each other—try `start medium hard`.
- To exit the program, type "exit".

## Playing the game
To make a move, enter coordinates like 1 1, which places your mark ('X' or 'O') in the first row and first column. The row and column numbers must be separated by a space.

![](https://github.com/Rodrigo-Suguimoto/Tic-Tac-Toe-with-AI/blob/main/tictactoe-demo.gif)
