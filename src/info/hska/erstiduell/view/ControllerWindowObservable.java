package info.hska.erstiduell.view;

import info.hska.erstiduell.questions.Answer;
import info.hska.erstiduell.questions.Question;
import java.util.Observable;

/**
 *
 * @author Moritz Grimm
 */
public class ControllerWindowObservable extends Observable {

    Question nextQuestion;
    Answer answer;

    public ControllerWindowObservable() {

    }

    public void setNextQuestion(Question question) {
        this.nextQuestion = question;

        setChanged();
        notifyObservers(question);
    }
    
    public Question getNextQuestion() {
        return nextQuestion;
    }
    
    public void showAnswer(Answer answer) {
        this.answer = answer;
        
        setChanged();
        notifyObservers(answer);
    }
    public Answer getAnswer() {
        return answer;
    }
    
    public void update() {
        setChanged();
        notifyObservers();
    }
    
    public void releaseBuzzers() {
        setChanged();
        notifyObservers(true);
    }

}
