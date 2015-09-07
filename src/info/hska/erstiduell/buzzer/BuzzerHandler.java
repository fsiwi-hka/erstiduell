package info.hska.erstiduell.buzzer;

import info.hska.erstiduell.Game;
import java.util.Observable;

/**
 * Observable
 *
 * @author Moritz Grimm
 */
public final class BuzzerHandler extends Observable {

    private static BuzzerHandler instance;

    private Key[] hotkeys;
    private boolean enabled = true;
    private long[] lastBuzz = new long[]{0, 0, 0, 0};
    private int currentPlayer;
    private boolean blocked = true;
    private Game game;

    public BuzzerHandler(Key[] hotkeys, Game game) {
        this.hotkeys = hotkeys;
        this.game = game;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getCurrentTeam() {
        return currentPlayer;
    }

    public boolean getBlocked() {
        return blocked;
    }

    protected void setSuccessfulBuzz(int player) {

        if (enabled && game != null && this.game.getCurrentQuestion() != null && player <= game.getNumberOfPlayers()) {

            long now = System.currentTimeMillis();
            int numAnswers = this.game.getCurrentQuestion().getAnswers().size();

            if (this.game.getTeams().get(player - 1).getPenalty() < numAnswers) {
                if (game.areBuzzersBlocked()) {
                    lastBuzz[player - 1] = now;
                } else if (now - lastBuzz[player - 1] < 1000) {

                } else {
                    //game.setBuzzersBlocked(true);
                    blocked = true;
                    currentPlayer = player;

                    setChanged();
                    notifyObservers(this);
                }
            }
        }
    }

    public void release() {
        currentPlayer = 0;
        //game.setCurrentTeam(0);
        blocked = false;
            //game.setBuzzersBlocked(false);

        setChanged();
        notifyObservers(this);
    }

}
