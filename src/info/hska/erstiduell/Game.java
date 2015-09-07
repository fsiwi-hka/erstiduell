package info.hska.erstiduell;

import info.hska.erstiduell.buzzer.BuzzerEventQueue;
import info.hska.erstiduell.buzzer.BuzzerHandler;
import info.hska.erstiduell.questions.Answer;
import info.hska.erstiduell.questions.Question;
import info.hska.erstiduell.questions.QuestionLibrary;
import info.hska.erstiduell.view.ConfigWindow;
import info.hska.erstiduell.view.ControllerWindow;
import info.hska.erstiduell.view.GameWindow;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Tim Roes
 */
public class Game extends Observable {

    private int[] points;
    private List<Team> teams;
    private ControllerWindow cw;
    private GameWindow gw;
    private QuestionLibrary questions;
    private Question currentQuestion;
    private boolean finished;
    private int currentTeam = -1;
    private BuzzerHandler bh;
    private boolean buzzersBlocked = true;

    public Game(Config config) {
        questions = QuestionLibrary.getInstance();

        teams = new ArrayList<Team>();
        for (int i = 0; i < config.players; i++) {
            teams.add(new Team("T" + (i + 1)));
        }
    }

    public int getNumberOfPlayers() {
        return teams.size();
    }

    public int getCurrentTeam() {
        return currentTeam;
    }

    public boolean areBuzzersBlocked() {
        return buzzersBlocked;
    }

    public void setBuzzersBlocked(boolean buzzersBlocked) {
        this.buzzersBlocked = buzzersBlocked;
    }

    public int getPoint(int player) {
        if (player > teams.size()) {
            return 0;
        }
        return teams.get(player - 1).getPoints();
    }

    public List<Team> getTeams() {
        return this.teams;
    }

    public QuestionLibrary getQuestions() {
        return questions;
    }

    public void setCurrentTeam(int team) {
        this.currentTeam = team;

        setChanged();
        notifyObservers(this);
        //   gw.showBuzzer(team);
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question question) {
        this.currentQuestion = question;
    }

    public void resetPenalties() {
        for (Team t : this.getTeams()) {
            t.resetPenalty();
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;

        setChanged();
        notifyObservers(this);
    }

    public List<Team> getWinner() {
        List<Team> winners = new ArrayList<Team>();
        int max = -1;

        for (Team t : teams) {
            if (t.getPoints() > max) {
                max = t.getPoints();
            }
        }

        for (Team t : teams) {
            if (t.getPoints() == max) {
                winners.add(t);
            }
        }

        return winners;
    }
}
