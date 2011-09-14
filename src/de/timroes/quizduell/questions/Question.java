package de.timroes.quizduell.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A question that can be asked. Each question need to have at least 2 answers.
 * 
 * @author Tim Roes
 */
public class Question {

	private final String question;
	private List<Answer> answers = new ArrayList<Answer>();
	private boolean done;

	/**
	 * Create a new question.
	 * 
	 * @param question Question text
	 */
	public Question(String question) {
		this.question = question;
	}

	/**
	 * Adds a new answer to the question.
	 *
	 * @param answer The answer to add
	 */
	public void addAnswer(Answer answer) {
		answers.add(answer);
		Collections.sort(answers);
	}

	/**
	 * Get the question's text.
	 * 
	 * @return Question's text
	 */
	public String getQuestionText() {
		return question;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void done() {
		done = true;
	}

	public boolean getDone() {
		return done;
	}

	@Override
	public String toString() {
		return (done ? "[X] " : "[ ] ") + question;
	}

}
