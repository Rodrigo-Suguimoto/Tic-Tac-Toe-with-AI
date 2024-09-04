# Tic Tac Toe with AI

I've created this game with Java as part of the [Hyperskill's curriculum](https://hyperskill.org/projects/81).
You'll be able to play against a human or AI with three difficulty modes: easy, medium and hard.

1. The easy AI will only play random moves;
2. The medium AI is a little bit smarter. He can defend himself and make the movement to win when he has the chance;
3. The hard AI won't let you win. You'll only lose or draw. I've implemented the minimax algorithm which is a pretty cool algorithm used in Game Development. It recursively evaluates the moves and counter-moves and selects the best possible move.

![](https://github.com/Rodrigo-Suguimoto/Tic-Tac-Toe-with-AI/blob/main/tictactoe-demo.gif)

## Commands to start and stop the game
To start the game you need to type something like: `start user hard` which means that a human will play as 'X' and the hard AI will play as 'O'.
- The first player after "start" will always be 'X' and the second one will always be 'O'. So if you type `start hard user`, the hard AI will start the game;
- You can also play human vs human, you just need to type `start user user`;
- If you want to stop the program, type "exit".

## Commands to place movements
To place movements, you'll need to write the coordinates, such as `1 1` which will place your mark ('X' or 'O') in the first row of the first column. Note that the numbers are separated by a space.
