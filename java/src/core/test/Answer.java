package core.test;

import java.io.Serializable;

/**
 * Answer object contained by a Question object and containing the answer text and whether it is correct or not
 */
public class Answer implements Serializable{
    private String text;
    private boolean correct;
    private boolean userAnswer;
    
    /**
     * Correct answer constant
     */
    public static final int CORRECT = 1;
    
    /**
     * Incorrect answer constant
     */
    public static final int INCORRECT = -1;
    
    /**
     * Unanswered answer constant
     */
    public static final int NO_ANSWER = 0;

    /**
     * Empty answer constructor
     */
    public Answer(){}
    
    
    /**
     * Filled answer constructor
     * 
     * @param text the content of the question
     * @param correct whether the answer is correct or not
     */
    public Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }
    
    /**
     * 
     * @return CORRECT: selected by the user and correct, 
     * INCORRECT: selected by the user and incorrect, 
     * NO_ANSWER: not selected by the user
     */
    public int checkUserAnswer(){
        
        //El usuario no ha marcado la respuesta
        if(!userAnswer){
            return NO_ANSWER;
        }
        
        //La respuesta es correcta
        if(correct==userAnswer){
            return CORRECT;
        }
        
        //El usuario ha marcado la respuesta y esta es incorrecta
        return INCORRECT;
    }
    
    /**
     * toString() method override for testing purposes
     * @return the answer properties
     */
    @Override
    public String toString(){
        return "\t\tANSWER\n\t\t---------\n\t\tText: "+text+"\n\t\tCorrect: "+correct;
    }
    
    
    
    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the correct
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * @param correct the correct to set
     */
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    /**
     * @return the userAnswer
     */
    public boolean getUserAnswer() {
        return userAnswer;
    }

    /**
     * @param userAnswer the userAnswer to set
     */
    public void setUserAnswer(boolean userAnswer) {
        this.userAnswer = userAnswer;
    }
}
