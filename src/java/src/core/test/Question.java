package core.test;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Question object contained by a Test object and containing the question title and the Answer objects
 */
public class Question implements Serializable {

    private String title;

    private boolean multiAnswer;

    private ArrayList<Answer> answers;

    private int[] status;

    private boolean locked;

    /**
     * Correct question constant
     */
    public static final int CORRECT = 1;

    /**
     * Incorrect question constant
     */
    public static final int INCORRECT = -1;

    /**
     * Unanswered question constant
     */
    public static final int NO_ANSWER = 0;

    /**
     * Partially correct question constant
     */
    public static final int PARTIAL = 2;

    /**
     * Filled question constructor
     * @param title the title of the question 
     */
    public Question(String title) {
        this.title = title;
        answers = new ArrayList<>();
    }

    /**
     * Empty question constructor
     */
    public Question() {
        this("");
    }

    /**
     * @param correctScore Score for correct answers. Note that there are no
     * checks to restrict this value, so it can actually SUBSTRACT from the
     * global score
     * @param incorrectScore Score for correct answers. Note that there are no
     * checks to restrict this value, so it could actually ADD to the global
     * score
     * @param partialScore Maximum score for partially correct questions
     * (score==(correctAnswers/totalCorrectAnswers)*partialScore). Actual scores
     * will always be lower than this, as reaching it would mean that the
     * question is fully correct
     * @return the score change from this question
     */
    public double answerScore(double correctScore, double incorrectScore, double partialScore) {

        double total = getCountCorrect();

        int[] status = answerStatusCount();
        int correct = status[0];

        switch (questionStatus()) {
            case CORRECT:
                return correctScore;

            case INCORRECT:
                return incorrectScore;

            case PARTIAL:
                return (correct / total) * partialScore;

            default:
                return 0;
        }
    }

    /**
     *
     * @return {correct count, incorrect count, unanswered count}
     */
    private int[] answerStatusCount() {
        if (status == null) {
            int correct = 0;
            int incorrect = 0;
            int noAnswer = 0;

            //Itera sobre las preguntas
            for (Answer a : answers) {

                switch (a.checkUserAnswer()) {
                    case CORRECT:
                        correct++;
                        break;
                    case NO_ANSWER:
                        noAnswer++;
                        break;
                    default:
                        incorrect++;
                        break;
                }
            }

            status = new int[]{correct, incorrect, noAnswer};
        }
        return status;
    }

    /**
     *
     * @return INCORRECT: 1 or more incorrect answers, 
     * CORRECT: correct answer count equals the correct answer count, 
     * PARTIAL: no incorrect answers and more than 1 correct answer but less than the correct answer count,
     * NO_ANSWER: 0 correct and incorrect answers
     */
    public int questionStatus() {

        int[] s = answerStatusCount();
        int correct = s[0];
        int incorrect = s[1];

        if (incorrect > 0) {
            return INCORRECT;
        } else if (correct == getCountCorrect() || (correct > 0 && !multiAnswer)) {
            return CORRECT;
        } else if (correct > 0) {
            return PARTIAL;
        } else {
            return NO_ANSWER;
        }
    }

    /**
     * 
     * @return the amount of correct anwsers in a question
     */
    public int getCountCorrect() {
        int count = 0;
        for (Answer a : answers) {
            if (a.isCorrect()) {
                count++;
            }
        }
        return count;
    }

    /**
     * toString() method override for testing purposes
     * @return the question properties, including contained answers
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tQUESTION\n\t---------\n\tTitle: ").append(title);
        sb.append("\n\tmultiAnswer: ").append(multiAnswer);

        for (Answer a : answers) {
            sb.append("\n\n" + a.toString());
        }

        return sb.toString();

    }

    /**
     * @return the question title
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
     * @return the answers
     */
    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    /**
     * @param answer Add a new answer
     */
    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    /**
     * Resets the answer list
     */
    public void resetAnswers() {
        answers = new ArrayList<>();
    }

    /**
     * @return the multiAnswer
     */
    public boolean isMultAnswer() {
        return multiAnswer;
    }

    /**
     * @param multiAnswer the multiAnswer to set
     */
    public void setMultiAnswer(boolean multiAnswer) {
        this.multiAnswer = multiAnswer;
    }

    /**
     * @return checks whether the question is locked (already answered) or not
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * @param locked the locked to set
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

}
