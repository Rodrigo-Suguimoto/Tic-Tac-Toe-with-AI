package tictactoe;

import java.util.Arrays;
import java.util.List;

public class GameMenu {
    protected boolean isValidCommand;
    protected boolean exitGame;
    protected String xPlayer;
    protected String oPlayer;

    public void processUserCommand(String userCommand) {
        String[] parameters = userCommand.split(" ");
        final List<String> VALID_PLAYERS = Arrays.asList("user", "easy", "medium", "hard");

        if (parameters[0].equals("start")) {
            if (parameters.length != 3) {
                invalidCommand();
                return;
            }

            String xPlayer = parameters[1];
            String oPlayer = parameters[2];
            if (
                    !VALID_PLAYERS.contains(xPlayer) || !VALID_PLAYERS.contains(oPlayer)
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
    public boolean isExitGame() {
        return this.exitGame;
    }

    public String getXPlayer() {
        return this.xPlayer;
    }

    public String getOPlayer() {
        return this.oPlayer;
    }
}
