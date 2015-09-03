/*
 * ControllerWindow.java
 *
 * Created on 10.03.2011, 22:34:03
 */
package info.hska.erstiduell.view;

import info.hska.erstiduell.Game;
import info.hska.erstiduell.buzzer.BuzzerHandler;
import info.hska.erstiduell.Mouse;
import info.hska.erstiduell.questions.Answer;
import info.hska.erstiduell.questions.Question;
import info.hska.erstiduell.questions.QuestionLibrary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public final class ControllerWindow extends javax.swing.JFrame {

	private Game game;
	private JButton[] teamButtons;

	/** Creates new form ControllerWindow 
         * 
         * @param game the game object it gets its data from
         */
        
	public ControllerWindow(Game game) {
                this.game = game;
		initComponents();
		progressBar.setMaximum(QuestionLibrary.getInstance().getQuestionAmount());
              
		this.teamButtons = new JButton[] { teams[0], teams[1], teams[2], teams[3] };
                
                this.setLocationByPlatform(true);
                this.setVisible(true);
                refresh();
        }
        
	public void refresh() {

		points1.getModel().setValue(game.getPoint(1));
		points2.getModel().setValue(game.getPoint(2));
		points3.getModel().setValue(game.getPoint(3));
		points4.getModel().setValue(game.getPoint(4));

		if (BuzzerHandler.getInstance().getBuzzerPlayer() != 0) {
			buzzers.setText("Release Buzzers [" + BuzzerHandler.getInstance().getBuzzerPlayer() + "]");
		} else {
			buzzers.setText("Release Buzzers");
		}
		buzzers.setEnabled(BuzzerHandler.getInstance().isBlocked());

		for (int i = 0; i < this.game.getTeams().size(); i++) {
			teamButtons[i].setText("<html><b><font color='"
					+ (BuzzerHandler.getInstance().getBuzzerPlayer() - 1 == i ? "red" : "black")
					+ "'>"
					+ this.game.getTeams().get(i).getName()
					+ "</font></b></html>");
		}
                if (gameQuestions.getItemCount() > 0) {
                    gameQuestions.removeAll();
                    gameQuestions.removeAllItems();
                }
                answers.removeAll();
             
                //System.out.println(game.getQuestions().getQuestionAmount());
               
		if (game.getCurrentQuestion() != null) {

                    gameQuestions.addItem(new Question("[Choose Question]"));
                    for (Question q : QuestionLibrary.getInstance().getAllQuestions()) {
                        gameQuestions.addItem(q);
                    }
                    question.setText(game.getCurrentQuestion().getQuestionText());

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
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        chooseQuestionPanel = new javax.swing.JPanel();
        gameQuestions = new javax.swing.JComboBox<Question>();
        nextQuestion = new javax.swing.JButton();
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

        //setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        setTitle("Quizduell Controller");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        chooseQuestionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Questions"));
        chooseQuestionPanel.setLayout(new java.awt.GridBagLayout());

        gameQuestions.setFont(new java.awt.Font("Liberation Mono", 0, 15)); // NOI18N
        gameQuestions.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(gameQuestions.getSelectedIndex() > 0) {
                    game.nextQuestion((Question)gameQuestions.getSelectedItem());
                }
            }
        });
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 10);
        chooseQuestionPanel.add(gameQuestions, gridBagConstraints);

        nextQuestion.setText("Random Question");
        nextQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextQuestionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        chooseQuestionPanel.add(nextQuestion, gridBagConstraints);

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

        question.setFont(new java.awt.Font("Liberation Sans", 1, 15)); 
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
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        answerPanel.add(jScrollPane1, gridBagConstraints);

        buzzers.setText("Release Buzzers");
        buzzers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buzzersActionPerformed(evt);
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

        show.setText("Show");
        show.setEnabled(false);
        show.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(answers.getSelectedValue() != null)
                game.showAnswer((Answer)answers.getSelectedValue());
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

        for (int i = 0; i < 4; i++){
            teams[i].setText("T" + (i + 1));
            teams[i].setEnabled(false);
            if (game.getNumberOfPlayers() <= i){
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

        points1.setModel(new javax.swing.SpinnerNumberModel (0, 0, null, 1));
        points1.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                game.setPoints(1, (Integer)points1.getValue());
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
                game.setPoints(2, (Integer)points2.getValue());
            }

        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        pointPanel.add(points2, gridBagConstraints);

        if(game.getNumberOfPlayers() < 3){
            points3.setEnabled(false);
        }
        points3.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        points3.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                game.setPoints(3, (Integer)points3.getValue());
            }

        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        pointPanel.add(points3, gridBagConstraints);

        if(game.getNumberOfPlayers() < 4){
            points4.setEnabled(false);
        }
        points4.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        points4.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                game.setPoints(4, (Integer)points4.getValue());
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        pointPanel.add(points4, gridBagConstraints);

        teamNames[0].setText("T1");
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

        teamNames[1].setText("T2");
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

        teamNames[2].setText("T3");
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

        teamNames[3].setText("T4");
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
    }// </editor-fold>


    public void team1ActionPerformed(java.awt.event.ActionEvent evt) {
            answer(1);
    }

    public void team2ActionPerformed(java.awt.event.ActionEvent evt) {
            answer(2);
    }

    public void team3ActionPerformed(java.awt.event.ActionEvent evt) {
            answer(3);
    }

    public void team4ActionPerformed(java.awt.event.ActionEvent evt) {
            answer(4);
    }
    
    private void showWinnerActionPerformed(java.awt.event.ActionEvent evt) {
        if (JOptionPane.showConfirmDialog(this, "This will be irreversible. Still do it?",
                "End Game?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            game.endGame();
        }
    }
    
    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (game.isFinished() || JOptionPane.showConfirmDialog(this, "Do you want to exit the game?",
                        "Exiting?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    private void nextQuestionActionPerformed(java.awt.event.ActionEvent evt) {
        game.nextQuestion();
    }

    public void answersValueChanged(javax.swing.event.ListSelectionEvent evt) {
        boolean vis = answers.getSelectedValue() != null
                        && !((Answer) answers.getSelectedValue()).getDone();
        teams[0].setEnabled(vis);
        teams[1].setEnabled(vis);
        teams[2].setEnabled(vis);
        teams[3].setEnabled(vis);
        show.setEnabled(vis);
    }

    
    public JTextField[] getTeamNames() {
        return teamNames;
    }

    public void setTeams(String teamnames, int team) {
        this.teams[team].setText(teamnames);
    }

    public void buzzersActionPerformed(java.awt.event.ActionEvent evt) {
        buzzers.setEnabled(false);
        game.releaseBuzzer();
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
                    BuzzerHandler.getInstance().setEnabled(false);
           	}
            }
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
                    BuzzerHandler.getInstance().setEnabled(!getTeamNames()[0].isEnabled() && !getTeamNames()[1].isEnabled()
                                    && !getTeamNames()[2].isEnabled() && !getTeamNames()[3].isEnabled());
                    game.update();
            }
    }
        
    private void answer(int player) {
        if (answers.getSelectedValue() != null) {
                game.guessedAnswer((Answer) answers.getSelectedValue(), player);
        }
    }
    private javax.swing.JPanel answerPanel;
    private javax.swing.JComboBox<Question> gameQuestions;
    private javax.swing.JList answers;
    private javax.swing.JButton buzzers;
    private javax.swing.JPanel chooseQuestionPanel;
    private javax.swing.JButton exitButton;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton nextQuestion;
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

}

