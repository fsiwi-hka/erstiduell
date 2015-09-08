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
import info.hska.erstiduell.view.ConfigWindow;
import info.hska.erstiduell.view.ControllerWindow;
import info.hska.erstiduell.view.GameWindow;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Moritz Grimm
 */
public class Controller {

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
        BuzzerObserver bo = new BuzzerObserver();
        bh.addObserver(bo);
        BuzzerEventQueue.setKeys(config.hotkeys, bh);

        Toolkit.getDefaultToolkit().getSystemEventQueue().push(BuzzerEventQueue.getInstance());

        createGameWindow(config, this);
        game.addObserver(cw);
        game.addObserver(gw);

        update();
    }

    private class BuzzerObserver implements Observer {

        public void update(Observable o, Object arg) {
            BuzzerHandler bh = (BuzzerHandler) arg;
            game.setCurrentTeam(bh.getCurrentTeam());
            game.setBuzzersBlocked(bh.getBlocked());

            Controller.this.update();
            gw.showBuzzer(bh.getCurrentTeam());
        }
    }

    private class ControllerWindowObserver implements Observer {

        public void update(Observable o, Object arg) {
            //ControllerWindowObservable cwo = (ControllerWindowObservable) arg;
            //Controller.this.nextQuestion(cwo.getNextQuestion());
            if (arg instanceof Answer) {
                Controller.this.showAnswer((Answer) arg);
            } else if (arg instanceof Question) {
                Controller.this.nextQuestion((Question) arg);
            } else if (arg instanceof Boolean) {
                Controller.this.releaseBuzzers();
            }

            Controller.this.update();
        }
        public void update(Observable o) {
            Controller.this.update();
        }
    }

    public void update() {
        gw.redraw();
        cw.refresh();
    }

    public void createGameWindow(Config config, Controller controller) {
        gw = new GameWindow(game);
        gw.setBGColor(config.background);
        gw.setFGColor(config.foreground);
        gw.setVisible(true);
        cw = new ControllerWindow(game);
        cw.setVisible(true);

        ControllerWindowObserver co = new ControllerWindowObserver();
        cw.getObservable().addObserver(co);
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
            gw.redraw();
        }
    };
    ReleaseTimer releaseTimer;

    public void releaseBuzzers() {
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
        game.setCurrentTeam(-1);
        resetPenalties();
        update();
        releaseBuzzers();
    }

    public void resetPenalties() {
        for (Team t : game.getTeams()) {
            t.resetPenalty();
        }
    }

    public void showAnswer(Answer a) {
        a.done();
        gw.redraw();
        //update();
    }

}
