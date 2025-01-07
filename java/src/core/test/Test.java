package core.test;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The main Test object containing the Question objects and the test properties
 */
public class Test implements Serializable {
    
    private String title;
    
    private boolean hasTimeLimit;
    private int hours=0;
    private int mins=0;
    
    private boolean hasMinScore;
    private double minScore = 0;
    private double correctAnsScore=0;
    private double incorrectAnsScore=0;
    private double partialAnsScore=0;
    
    private boolean canCheckAnswer;

    private ArrayList<Question> questions;

     
    /**
     * @return the number of questions
     */
    public int countQuestions(){
        return questions.size();
    }
    
    /**
     * 
     * @return the max score for the test
     */
    public double getMaxScore(){
        return (questions.size()*correctAnsScore);
    }
    
    /**
     * 
     * @return the current user score
     */
    public double getScore(){
        double score = 0;
        
        for(Question q : questions){
            score+=q.answerScore(correctAnsScore, incorrectAnsScore, partialAnsScore);
        }
        return score;
    }
    
    /**
     * 
     * @return whether the pass score has been achieved by the user or not
     */
    public boolean hasPassed(){
        double score = 0;
        
        for(Question q:questions){
            score+= q.answerScore(correctAnsScore, incorrectAnsScore, partialAnsScore);
        }
        
        if(score>=minScore){
            return true;
        }
        
        return false;
    }

    /**
     * Empty test constructor
     */
    public Test(){
        this.questions = new ArrayList<>();
    }
    
    /**
     * Filled test constructor
     * @param title the name of the test
     * @param hasTimeLimit whether the test has a time limit or not
     * @param hours the hour limit for the test
     * @param mins the minute limit for the test
     * @param hasMinScore whether the test has a minimum score or not
     * @param minScore the minimum score to pass the test
     * @param correctAnsScore the score change for every correct question (can be negative)
     * @param incorrectAnsScore the score change for every incorrect question (can be positive)
     * @param partialAnsScore the maximum score change for a partially correct question ((correctAnswers/totalCorrectAnswers)*partialAnsScore)
     * @param canCheckAnswer whether the user can check if a question is correct before finishing the test
     */
    public Test(String title, boolean hasTimeLimit, int hours, int mins, boolean hasMinScore, double minScore, double correctAnsScore, double incorrectAnsScore, double partialAnsScore, boolean canCheckAnswer) {
        this.title = title;
        this.hasTimeLimit = hasTimeLimit;
        this.hours = hours;
        this.mins = mins;
        this.hasMinScore = hasMinScore;
        this.minScore = minScore;
        this.correctAnsScore = correctAnsScore;
        this.incorrectAnsScore = incorrectAnsScore;
        this.partialAnsScore = partialAnsScore;
        this.canCheckAnswer = canCheckAnswer;
        this.questions = new ArrayList<>();
    }
       
    
    /**
     * toString() method override for testing purposes
     * @return the test properties, including contained questions
     */
    @Override
    public String toString(){
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("TEST\n--------------------\nTitle: ").append(title);
        sb.append("\nquestionsSize: ").append(questions.size());
        sb.append("\nhasTimeLimit: ").append(hasTimeLimit);
        sb.append("\nhours: ").append(hours);
        sb.append("\nmins: ").append(mins);
        sb.append("\nhasMinScore: ").append(hasMinScore);
        sb.append("\nminScore: ").append(minScore);
        sb.append("\ncorrectAnsScore: ").append(correctAnsScore);
        sb.append("\nincorrectAnsScore: ").append(incorrectAnsScore);
        sb.append("\npartialAnsScore: ").append(partialAnsScore);
        sb.append("\ncanCheckAnswer: ").append(canCheckAnswer);
        
        for(Question q:questions){
            sb.append("\n\n"+q.toString());
        }
        
        return sb.toString();
        
    }
    
    
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the hasTimeLimit
     */
    public boolean hasTimeLimit() {
        return hasTimeLimit;
    }

    /**
     * @param hasTimeLimit the hasTimeLimit to set
     */
    public void setHasTimeLimit(boolean hasTimeLimit) {
        this.hasTimeLimit = hasTimeLimit;
    }

    /**
     * @return the hours
     */
    public int getHours() {
        return hours;
    }

    /**
     * @param hours the hours to set
     */
    public void setHours(int hours) {
        this.hours = hours;
    }

    /**
     * @return the mins
     */
    public int getMins() {
        return mins;
    }

    /**
     * @param mins the mins to set
     */
    public void setMins(int mins) {
        this.mins = mins;
    }

    /**
     * @return the hasMinScore
     */
    public boolean hasMinScore() {
        return hasMinScore;
    }

    /**
     * @param hasMinScore the hasMinScore to set
     */
    public void setHasMinScore(boolean hasMinScore) {
        this.hasMinScore = hasMinScore;
    }

    /**
     * @return the passScore
     */
    public double getPassScore() {
        return minScore;
    }

    /**
     * @param passScore the passScore to set
     */
    public void setMinScore(double passScore) {
        this.minScore = passScore;
    }

    /**
     * @return the correctAnsScore
     */
    public double getCorrectAnsScore() {
        return correctAnsScore;
    }

    /**
     * @param correctAnsScore the correctAnsScore to set
     */
    public void setCorrectAnsScore(double correctAnsScore) {
        this.correctAnsScore = correctAnsScore;
    }

    /**
     * @return the incorrectAnsScore
     */
    public double getIncorrectAnsScore() {
        return incorrectAnsScore;
    }

    /**
     * @param incorrectAnsScore the incorrectAnsScore to set
     */
    public void setIncorrectAnsScore(double incorrectAnsScore) {
        this.incorrectAnsScore = incorrectAnsScore;
    }

    /**
     * @return the partialAnsScore
     */
    public double getPartialAnsScore() {
        return partialAnsScore;
    }

    /**
     * @param partialAnsScore the partialAnsScore to set
     */
    public void setPartialAnsScore(double partialAnsScore) {
        this.partialAnsScore = partialAnsScore;
    }

    /**
     * @return the questions
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }
    

    /**
     * @return the canCheckAnswer
     */
    public boolean canCheckAnswer() {
        return canCheckAnswer;
    }

    /**
     * @param canCheckAnswer the canCheckAnswer to set
     */
    public void setCanCheckAnswer(boolean canCheckAnswer) {
        this.canCheckAnswer = canCheckAnswer;
    }
    
    /**
     * @param question Adds a new question
     */
    public void addQuestion(Question question){
        questions.add(question);
    }
    
}
