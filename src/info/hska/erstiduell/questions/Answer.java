package info.hska.erstiduell.questions;

/**
 * The answer to a question. It holds the answer and the amount of people
 * answered this question.
 *
 * @author Tim Roes
 */
public class Answer implements Comparable<Answer> {

    private final String name;
    private final int amount;
    private boolean done;

    /**
     * Create a new Answer.
     *
     * @param name Actual answer
     * @param amount Amount of people answered that
     */
    public Answer(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    /**
     * Get the Answer text
     *
     * @return Answer text
     */
    public String getAnswer() {
        return name;
    }

    /**
     * Get the answer amount
     *
     * @return Amount answer
     */
    public int getAmount() {
        return amount;
    }

    public void done() {
        done = true;
    }

    public boolean getDone() {
        return done;
    }

    public int compareTo(Answer o) {
        return o.amount - amount;
    }

    @Override
    public String toString() {
        return (done ? "[X] " : "[ ] ") + name + " (" + String.valueOf(amount) + ")";
    }

}
