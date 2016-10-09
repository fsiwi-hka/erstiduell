package info.hska.erstiduell;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tim Roes
 */
public class Team extends Observable {

    private String name;
    private int points;
    private int penalty = 0;
    private boolean temporarilyBlocked;

    public Team(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPenalty() {
        this.penalty++;
    }

    public int getPenalty() {
        return this.penalty;
    }

    public void resetPenalty() {
        this.penalty = 0;
    }
    
    public void setTemporarilyBlocked(boolean temporarilyBlocked) {
        this.temporarilyBlocked = temporarilyBlocked;
        BlockedCountdown blockedCountdown = new BlockedCountdown();
        blockedCountdown.start();
        
        setChanged();
        notifyObservers(this);
    }
    
    public boolean getTemporarilyBlocked() {
        return temporarilyBlocked;
    }
    
    public void notifyGameWindow() {
        temporarilyBlocked = false;
        
        setChanged();
        notifyObservers(this);
    }
    
    private class BlockedCountdown extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                notifyGameWindow();
            } catch (InterruptedException ex) {
                Logger.getLogger(Team.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        }
    };

}
