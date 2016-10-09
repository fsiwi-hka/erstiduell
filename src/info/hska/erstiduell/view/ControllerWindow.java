/*
 * ControllerWindow.java
 *
 * Created on 10.03.2011, 22:34:03
 */
package info.hska.erstiduell.view;

import info.hska.erstiduell.Game;
import info.hska.erstiduell.Mouse;
import info.hska.erstiduell.buzzer.BuzzerEventQueue;
import info.hska.erstiduell.questions.Answer;
import info.hska.erstiduell.questions.Question;
import info.hska.erstiduell.questions.QuestionLibrary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author timroes
 */
public final class ControllerWindow extends javax.swing.JFrame implements Observer {

    private Game game;
    private final JButton[] teamButtons;
    private final ControllerWindowObservable cwo;
    private final List<Question> questions;

    /**
     * Creates new form ControllerWindow
     *
     * @param game the game object it gets its data from
     */
    public ControllerWindow(Game game) {
        this.game = game;
        initComponents();
        this.setSize(800, 700);
        progressBar.setMaximum(QuestionLibrary.getInstance().getQuestionAmount());

        this.teamButtons = new JButton[]{teams[0], teams[1], teams[2], teams[3]};

        this.setLocationByPlatform(true);
        this.setVisible(true);

        this.cwo = new ControllerWindowObservable();
        questions = QuestionLibrary.getInstance().getAllQuestions();

        initNames();
    }

    public void update(Observable game, Object o) {
        this.game = (Game) o;
        refresh();
    }

    public void refresh() {

        points1.getModel().setValue(game.getPoint(0));
        points2.getModel().setValue(game.getPoint(1));
        points3.getModel().setValue(game.getPoint(2));
        points4.getModel().setValue(game.getPoint(3));

        if (game.getCurrentTeam() > -1) {
            next.setText("<html><b><font color='red'>Give points to "
                    + game.getTeams().get(game.getCurrentTeam()).getName()
                    + " and release</font></b></html>");
            buzzers.setText("Release buzzers and penalize "
                    + game.getTeams().get(game.getCurrentTeam()).getName());
        } else if (game.getCurrentTeam() == -2) {
            buzzers.setText("Premature buzzering");
            next.setText(" ");
            next.setEnabled(false);

        } else {
            buzzers.setText("Release buzzers");
            next.setText(" ");
            next.setEnabled(false);
        }

        buzzers.setEnabled(game.areBuzzersBlocked());

        for (int i = 0; i < this.game.getTeams().size(); i++) {
            teamButtons[i].setText("<html><b><font color='"
                    + (game.getCurrentTeam() == i ? "red" : "black")
                    + "'>"
                    + this.game.getTeams().get(i).getName()
                    + "</font></b></html>");
        }

        answers.removeAll();

        if (game.getNextQuestion() < questions.size()) {

            gameQuestions.setText("Next question: " + questions.get(game.getNextQuestion()).getQuestionText());
        }

        if (game.getCurrentQuestion() != null) {
            question.setText("Question:  " + game.getCurrentQuestion().getQuestionText());

            answers.setModel(new AbstractListModel() {

                public int getSize() {
                    return game.getCurrentQuestion().getAnswers().size();
                }

                public Object getElementAt(int index) {
                    return game.getCurrentQuestion().getAnswers().get(index);
                }
            });
        }
        int answered = QuestionLibrary.getInstance().getDoneQuestions();

        progressBar.setString(answered
                + "/" + QuestionLibrary.getInstance().getQuestionAmount());
        progressBar.setValue(answered);
    }

    // <editor-fold defaultstate="collapsed">
    private javax.swing.JPanel answerPanel;
    private javax.swing.JButton gameQuestions;
    private javax.swing.JList answers;
    private javax.swing.JButton buzzers;
    private javax.swing.JPanel chooseQuestionPanel;
    private javax.swing.JButton exitButton;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton nextQuestionButton;
    private javax.swing.JPanel pointPanel;
    private javax.swing.JSpinner points1;
    private javax.swing.JSpinner points2;
    private javax.swing.JSpinner points3;
    private javax.swing.JSpinner points4;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel question;
    private javax.swing.JButton show;
    private javax.swing.JButton showWinner;
    private javax.swing.JButton[] teams;
    private javax.swing.JTextField[] teamNames;
    private javax.swing.JButton next;

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        chooseQuestionPanel = new javax.swing.JPanel();
        gameQuestions = new javax.swing.JButton();
        nextQuestionButton = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        question = new javax.swing.JLabel();
        answerPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        answers = new javax.swing.JList();
        buzzers = new javax.swing.JButton();
        show = new javax.swing.JButton();
        pointPanel = new javax.swing.JPanel();
        points1 = new javax.swing.JSpinner();
        points2 = new javax.swing.JSpinner();
        points3 = new javax.swing.JSpinner();
        points4 = new javax.swing.JSpinner();
        teamNames = new javax.swing.JTextField[4];
        teams = new javax.swing.JButton[4];
        for (int i = 0; i < teams.length; i++) {
            teamNames[i] = new javax.swing.JTextField();
            teams[i] = new javax.swing.JButton();
        }
        gamePanel = new javax.swing.JPanel();
        showWinner = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        next = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        setTitle("Quizduell Controller");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        chooseQuestionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Questions"));
        chooseQuestionPanel.setLayout(new java.awt.GridBagLayout());

        gameQuestions.setFont(new java.awt.Font("Liberation Mono", 1, 20));
        gameQuestions.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameQuestionsActionPerformed();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 10);
        chooseQuestionPanel.add(gameQuestions, gridBagConstraints);

        progressBar.setString("X/X");
        progressBar.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        chooseQuestionPanel.add(progressBar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        getContentPane().add(chooseQuestionPanel, gridBagConstraints);

        question.setFont(new java.awt.Font("Liberation Sans", 1, 25));
        question.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        getContentPane().add(question, gridBagConstraints);

        answerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Answers"));
        answerPanel.setLayout(new java.awt.GridBagLayout());

        answers.setFont(new java.awt.Font("Liberation Mono", 0, 15));
        answers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        answers.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                answersValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(answers);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        answerPanel.add(jScrollPane1, gridBagConstraints);

        next.setText("Give Points to Buzzerer and Release");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autopointsActionPerformed();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        answerPanel.add(next, gridBagConstraints);

        buzzers.setText("Release Buzzers");
        buzzers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buzzersActionPerformed();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        answerPanel.add(buzzers, gridBagConstraints);

        show.setText("Show without giving Points");
        show.setEnabled(false);
        show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (answers.getSelectedValue() != null) {
                    cwo.showAnswer((Answer) answers.getSelectedValue());
                }
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        answerPanel.add(show, gridBagConstraints);

        for (int i = 0; i < 4; i++) {
            teams[i].setText("T" + (i + 1));
            teams[i].setEnabled(false);
            if (game.getNumberOfPlayers() <= i) {
                teams[i].setVisible(false);
            }
        }
        teams[0].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                team1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        answerPanel.add(teams[0], gridBagConstraints);

        teams[1].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                team2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        answerPanel.add(teams[1], gridBagConstraints);

        teams[2].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                team3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        answerPanel.add(teams[2], gridBagConstraints);

        teams[3].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                team4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        answerPanel.add(teams[3], gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        getContentPane().add(answerPanel, gridBagConstraints);

        pointPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Points"));
        pointPanel.setLayout(new java.awt.GridBagLayout());

        points1.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        points1.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                game.getTeams().get(0).setPoints((Integer) points1.getValue());
                cwo.update();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        pointPanel.add(points1, gridBagConstraints);

        points2.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        points2.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                game.getTeams().get(1).setPoints((Integer) points2.getValue());
                cwo.update();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        pointPanel.add(points2, gridBagConstraints);

        if (game.getNumberOfPlayers() < 3) {
            points3.setEnabled(false);
        }
        points3.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        points3.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                game.getTeams().get(2).setPoints((Integer) points3.getValue());
                cwo.update();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        pointPanel.add(points3, gridBagConstraints);

        if (game.getNumberOfPlayers() < 4) {
            points4.setEnabled(false);
        }
        points4.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        points4.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                game.getTeams().get(3).setPoints((Integer) points4.getValue());
                cwo.update();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        pointPanel.add(points4, gridBagConstraints);

        teamNames[0].setText("Team Awesome");
        teamNames[0].setEnabled(false);
        teamNames[0].addMouseListener(new Mouse(this));

        teamNames[0].addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                changedName(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        pointPanel.add(teamNames[0], gridBagConstraints);

        teamNames[1].setText("Team Better");
        teamNames[1].setEnabled(false);
        teamNames[1].addMouseListener(new Mouse(this));
        teamNames[1].addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                changedName(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        pointPanel.add(teamNames[1], gridBagConstraints);

        teamNames[2].setText("Team Cecilia");
        teamNames[2].setEnabled(false);
        teamNames[2].addMouseListener(new Mouse(this));
        teamNames[2].addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                changedName(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        pointPanel.add(teamNames[2], gridBagConstraints);

        teamNames[3].setText("The Dominators");
        teamNames[3].setEnabled(false);
        teamNames[3].addMouseListener(new Mouse(this));
        teamNames[3].addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                changedName(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        pointPanel.add(teamNames[3], gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        getContentPane().add(pointPanel, gridBagConstraints);

        gamePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Game"));

        showWinner.setText("Show Winner");
        showWinner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showWinnerActionPerformed(evt);
            }
        });
        gamePanel.add(showWinner);

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        gamePanel.add(exitButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 10);
        getContentPane().add(gamePanel, gridBagConstraints);

        pack();
    }

    public void team1ActionPerformed(java.awt.event.ActionEvent evt) {
        answer(0);
    }

    public void team2ActionPerformed(java.awt.event.ActionEvent evt) {
        answer(1);
    }

    public void team3ActionPerformed(java.awt.event.ActionEvent evt) {
        answer(2);
    }

    public void team4ActionPerformed(java.awt.event.ActionEvent evt) {
        answer(3);
    }
// </editor-fold>

    /**
     * Calls setNextQuestion if all answers were shown, else asks for approval.
     */
    private void gameQuestionsActionPerformed() {
        boolean answeredCompletely;
        if (game.getCurrentQuestion() != null) {
            answeredCompletely = game.getCurrentQuestion().wasAnswered();
            if (!answeredCompletely
                    && (JOptionPane.showConfirmDialog(this, "Question was not answered completely. Continue?",
                            "Next Question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)) {
                setNextQuestion();
            } else if (answeredCompletely) {
                setNextQuestion();
            }
        } else {
            setNextQuestion();
        }
    }

    private void setNextQuestion() {
        cwo.setNextQuestion(questions.get(game.getNextQuestion()));

        if (game.getNextQuestion() < questions.size() - 1) {
            game.setNextQuestion(game.getNextQuestion() + 1);
            gameQuestions.setText("Next question: " + questions.get(game.getNextQuestion()).getQuestionText());
        } else {
            game.setNextQuestion(0);
            gameQuestions.setText("Start from beginning: " + questions.get(0).getQuestionText());
        }
    }

    private void showWinnerActionPerformed(java.awt.event.ActionEvent evt) {
        if (!game.isFinished()) {
            game.setFinished(true);
            cwo.update();
            showWinner.setText("Resume Game");
        } else {
            game.setFinished(false);
            cwo.update();
            showWinner.setText("Show Winner");
        }
    }

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (game.isFinished() || JOptionPane.showConfirmDialog(this, "Do you want to exit the game?",
                "Exiting?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void initNames() {
        for (int team = 0; team < game.getTeams().size(); team++) {
            setTeams(teamNames[team].getText(), team);
            game.getTeams().get(team).setName(teamNames[team].getText());
        }
        BuzzerEventQueue.getInstance().setEnabled(!getTeamNames()[0].isEnabled() && !getTeamNames()[1].isEnabled()
                && !getTeamNames()[2].isEnabled() && !getTeamNames()[3].isEnabled());
        cwo.update();
    }

    public void answersValueChanged(javax.swing.event.ListSelectionEvent evt) {
        boolean vis = answers.getSelectedValue() != null
                && !((Answer) answers.getSelectedValue()).getDone();
        teams[0].setEnabled(vis);
        teams[1].setEnabled(vis);
        teams[2].setEnabled(vis);
        teams[3].setEnabled(vis);
        show.setEnabled(vis);
        next.setEnabled(vis);
    }

    public JTextField[] getTeamNames() {
        return teamNames;
    }

    public void setTeams(String teamnames, int team) {
        this.teams[team].setText(teamnames);
    }

    public ControllerWindowObservable getObservable() {
        return cwo;
    }

    public void doChangeName(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            if (evt.getSource().equals(this.getTeamNames()[2]) && game.getNumberOfPlayers() < 3
                    || evt.getSource().equals(getTeamNames()[3]) && game.getNumberOfPlayers() < 4) {
                return;
            }
            JTextField src = (JTextField) evt.getSource();

            if (!src.isEnabled()) {
                src.setEnabled(true);
                src.requestFocus();
                BuzzerEventQueue.getInstance().setEnabled(false);
            }
        }
    }

    public void buzzersActionPerformed() {
        cwo.releaseBuzzers();
        buzzers.setEnabled(false);
        buzzers.setText("---released---");
    }

    public void changedName(java.awt.event.FocusEvent evt) {
        JTextField src = (JTextField) evt.getSource();
        if (src.isEnabled()) {
            src.setEnabled(false);
            int team = -1;

            if (src.equals(getTeamNames()[0])) {
                team = 0;
            } else if (src.equals(getTeamNames()[1])) {
                team = 1;
            } else if (src.equals(getTeamNames()[2])) {
                team = 2;
            } else if (src.equals(getTeamNames()[3])) {
                team = 3;
            }
            setTeams(src.getText(), team);
            game.getTeams().get(team).setName(src.getText());
            BuzzerEventQueue.getInstance().setEnabled(!getTeamNames()[0].isEnabled() && !getTeamNames()[1].isEnabled()
                    && !getTeamNames()[2].isEnabled() && !getTeamNames()[3].isEnabled());
            cwo.update();
        }
    }

    private void answer(int player) {
        if (answers.getSelectedValue() != null) {
            Answer a = (Answer) answers.getSelectedValue();
            if (!a.getDone()) {
                game.getTeams().get(player).addPoints(a.getAmount());
                a.done();
            }
            game.setCurrentTeam(-1);
        }
    }

    private void autopointsActionPerformed() {
        if (answers.getSelectedValue() != null) {
            Answer a = (Answer) answers.getSelectedValue();
            if (!a.getDone()) {
                game.getTeams().get(game.getCurrentTeam()).addPoints(a.getAmount());
                a.done();
            }
            game.setCurrentTeam(-1);
            buzzersActionPerformed();
        }
    }
}
