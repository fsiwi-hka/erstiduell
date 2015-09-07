/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.hska.erstiduell;

import info.hska.erstiduell.buzzer.BuzzerEventQueue;
import info.hska.erstiduell.buzzer.BuzzerHandler;
import info.hska.erstiduell.questions.Answer;
import info.hska.erstiduell.questions.Question;
import info.hska.erstiduell.questions.QuestionLibrary;
import info.hska.erstiduell.view.ConfigWindow;
import info.hska.erstiduell.view.ControllerWindow;
import info.hska.erstiduell.view.GameWindow;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

/**
 *
 * @author Moritz Grimm
 */
public class Controller implements Observer {

    private Game game;
    private GameWindow gw;
    private BuzzerHandler bh;
    private ControllerWindow cw;

    public Controller() {

    }

    public void start() {
        ConfigWindow cfgw = new ConfigWindow(this);
        cfgw.setMonitors(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices());
        cfgw.setVisible(true);
    }

    public void configured(Config config) {
        game = new Game(config);

        bh = new BuzzerHandler(config.hotkeys, game);
        bh.addObserver(this);
        BuzzerEventQueue.setKeys(config.hotkeys, bh);

        Toolkit.getDefaultToolkit().getSystemEventQueue().push(BuzzerEventQueue.getInstance());

        createGameWindow(config, this);
        game.addObserver(cw);
        game.addObserver(gw);

        update();
    }

    public void update(Observable BuzzerHandler, Object o) {
        this.bh = (BuzzerHandler) o;
        game.setCurrentTeam(bh.getCurrentTeam());
        game.setBuzzersBlocked(bh.getBlocked());

        update();
        gw.showBuzzer(bh.getCurrentTeam());
    }

    private void update() {
        cw.refresh();
        gw.redraw();
    }

    public void createGameWindow(Config config, Controller controller) {
        gw = new GameWindow(game);
        gw.setBGColor(config.background);
        gw.setFGColor(config.foreground);
        gw.setVisible(true);
        cw = new ControllerWindow(game, controller);
        cw.setVisible(true);
    }

    public void nextQuesActionPerformed(java.awt.event.ActionEvent evt) {
        nextQuestion();
    }

    public void setPoints(int player, int points) {
        game.getTeams().get(player - 1).setPoints(points);

        update();
    }

    public void guessedAnswer(Answer a, int player) {
        if (!a.getDone()) {
            game.getTeams().get(player - 1).addPoints(a.getAmount());
            a.done();
        }
        game.setCurrentTeam(-1);
    }

    public void setBuzzersActive(boolean active) {
        bh.setEnabled(active);
    }

    private class ReleaseTimer extends Thread {

        @Override
        public void run() {
            try {
                gw.showTimer("Get ready!<br>3");
                Thread.sleep(1000);
                gw.showTimer("Get ready!<br>2");
                Thread.sleep(1000);
                gw.showTimer("Get ready!<br>1");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }

            releaseTimer = null;
            game.setBuzzersBlocked(false);
            bh.release();
            //Game.this.refresh = true;
            update();
        }
    };
    ReleaseTimer releaseTimer;

    public void releaseBuzzer() {
        if (game.getCurrentTeam() >= 0) {
            game.getTeams().get(game.getCurrentTeam()).addPenalty();
            game.setCurrentTeam(-1);
            gw.redraw();
            boolean endQuestion = true;
            for (Team t : game.getTeams()) {
                if (t.getPenalty() < game.getCurrentQuestion().getAnswers().size()) {
                    endQuestion = false;
                    break;
                }
            }
            if (endQuestion) {
                return;
            }
        }
        if (releaseTimer == null) {
            releaseTimer = new ReleaseTimer();
            releaseTimer.start();
        }
    }

    public void nextQuestion(Question q) {
        game.setCurrentQuestion(q);
        game.getCurrentQuestion().done();
        resetPenalties();
        game.setCurrentTeam(-1);
        update();
        releaseBuzzer();
    }

    public Question nextQuestion() {
        List<Question> qs = game.getQuestions().getAllQuestions();
                //List<Question> qs = QuestionLibrary.getInstance().getAllQuestions();

        // Get random question
        int i = (int) (Math.random() * qs.size());
        int count = 0;

        while (count++ < qs.size() && qs.get(i).getDone()) {
            i = (i + 1) % qs.size();
        }

        if (count >= qs.size()) {
            return null;
        }

        game.setCurrentQuestion(qs.get(i));
        game.getCurrentQuestion().done();
        resetPenalties();
        game.setCurrentTeam(-1);
        update();
        releaseBuzzer();

        return game.getCurrentQuestion();
    }

    public void resetPenalties() {
        for (Team t : game.getTeams()) {
            t.resetPenalty();
        }
    }

    public void endGame() {
        game.setFinished(true);
        update();
    }

    public void showAnswer(Answer a) {
        a.done();
        update();
    }

}
