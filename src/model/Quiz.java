package model;

import java.util.ArrayList;

/**
 * Quiz class grouping multiple questions together.
 * Demonstrates:
 * - Composition: Quiz HAS-A ArrayList of Question objects.
 * - Collections: ArrayList<Question> maintains the sequence of questions for the quiz.
 */
public class Quiz {
    private int quizId;
    private String quizName;

    // ArrayList stores the ordered list of Question objects within this Quiz.
    private ArrayList<Question> questions;

    public Quiz(int quizId, String quizName) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.questions = new ArrayList<>();
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        if (question != null) {
            questions.add(question);
        }
    }

    public boolean removeQuestion(int questionId) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getQuestionId() == questionId) {
                questions.remove(i);
                return true;
            }
        }
        return false;
    }

    public int getQuestionCount() {
        return questions.size();
    }

    /**
     * Demonstrates Collections.shuffle() to provide questions in randomized order.
     */
    public ArrayList<Question> getRandomizedQuestions() {
        ArrayList<Question> shuffled = new ArrayList<>(questions);
        java.util.Collections.shuffle(shuffled);
        return shuffled;
    }
}

