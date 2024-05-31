package tictactoe;

public class GameMenu {

    boolean continueGame;
    String xPlayer;
    String oPlayer;

    public void processUserCommand(String userCommand) {
        String[] parameters = userCommand.split(" ");
        if (parameters[0].equals("start")) {
            if (parameters.length != 3) {
                invalidCommand();
                return;
            }

            String xPlayer = parameters[1];
            String oPlayer = parameters[2];

            if (
                    (!xPlayer.equals("easy") && !xPlayer.equals("user")) ||
                            (!oPlayer.equals("easy") && !oPlayer.equals("user"))
            ) {
                invalidCommand();
                return;
            }

            this.continueGame = true;
            this.xPlayer = xPlayer;
            this.oPlayer = oPlayer;
        }

        if (parameters[0].equals("exit")) {
            this.continueGame = false;
        }
    }

    private void invalidCommand() {
        System.out.println("Bad parameters!");
        this.continueGame = false;
    }

    public boolean getContinueGame() {
        return this.continueGame;
    }

}
