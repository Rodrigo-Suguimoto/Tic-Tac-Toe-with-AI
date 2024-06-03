package tictactoe;

public class GameMenu {

    protected boolean isValidCommand;
    protected boolean exitGame;
    protected String xPlayer;
    protected String oPlayer;

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

            this.isValidCommand = true;
            this.xPlayer = xPlayer;
            this.oPlayer = oPlayer;
            return;
        }

        if (parameters[0].equals("exit") && parameters.length == 1) {
            this.isValidCommand = true;
            this.exitGame = true;
        }
    }

    private void invalidCommand() {
        System.out.println("Bad parameters!");
        this.isValidCommand = false;
    }

    public boolean isValidCommand() {
        return this.isValidCommand;
    }

    public boolean isExitGame() { return this.exitGame; }

    public String getXPlayer() { return this.xPlayer; }

    public String getOPlayer() { return this.oPlayer; }

}
