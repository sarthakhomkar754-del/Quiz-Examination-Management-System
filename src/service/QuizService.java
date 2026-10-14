package service;

import exception.NoQuestionsException;
import exception.QuizNotFoundException;
import model.Quiz;
import repository.DataStore;

/**
 * Service providing validation and retrieval of Quizzes.
 * Demonstrates:
 * - Collections: Fast HashMap<Integer, Quiz> lookup.
 * - Exception Handling: Throws QuizNotFoundException and NoQuestionsException.
 */
public class QuizService {

    public Quiz getQuizById(int quizId) throws QuizNotFoundException {
        // Fast O(1) lookup using HashMap
        Quiz quiz = DataStore.quizMap.get(quizId);
        if (quiz == null) {
            throw new QuizNotFoundException("Quiz with ID " + quizId + " does not exist.");
        }
        return quiz;
    }

    public void validateQuizHasQuestions(Quiz quiz) throws NoQuestionsException {
        if (quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
            throw new NoQuestionsException("Quiz '" + quiz.getQuizName() + "' has no questions available to attempt.");
        }
    }

    /**
     * Creates a custom dynamic Quiz composed of randomly selected questions from the global question bank.
     * Demonstrates:
     * - Collections: ArrayList and Collections.shuffle() to choose random questions.
     * - Exception: Throws NoQuestionsException if the question pool is empty.
     */
    public Quiz createRandomizedQuiz(String quizName, int count) throws NoQuestionsException {
        if (DataStore.questions.isEmpty()) {
            throw new NoQuestionsException("No questions are currently available in the question bank.");
        }

        java.util.ArrayList<model.Question> pool = new java.util.ArrayList<>(DataStore.questions);
        java.util.Collections.shuffle(pool);

        int selectedCount = Math.min(count, pool.size());
        Quiz randomQuiz = new Quiz(999, quizName);
        for (int i = 0; i < selectedCount; i++) {
            randomQuiz.addQuestion(pool.get(i));
        }

        return randomQuiz;
    }
}
