package info.hska.erstiduell.questions;

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
    private boolean done = false;

    /**
     * Create a new question.
     *
     * @param question Question text
     */
    public Question(String question, ArrayList<Answer> answers, boolean done) {
        this.question = question;
        this.answers = answers;
        this.done = done;
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
    /**
     * Getter for answers to this question
     * @return List of answers to this question
     */
    public List<Answer> getAnswers() {
        return answers;
    }

    public void done() {
        done = true;
    }

    public boolean getDone() {
        return done;
    }
    /**
     * checks weather all answers to this question are done
     * @return true if all answers were done, false otherwise
     */
    public boolean wasAnswered() {

        boolean answeredCompletely = true;

        for (int i = 0; i < this.getAnswers().size(); i++) {
            answeredCompletely = this.getAnswers().get(i).getDone() && answeredCompletely;
        }
        return answeredCompletely;
    }

    @Override
    public String toString() {
        return (done ? "[X] " : "[ ] ") + question;
    }

}
