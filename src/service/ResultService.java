package service;

import model.Question;
import model.Quiz;
import model.Result;
import model.Student;
import repository.DataStore;

import java.util.HashMap;

/**
 * Service managing quiz grading and result generation.
 * Demonstrates:
 * - Collections:
 *     - Reads student answers from HashMap<Integer, Character>.
 *     - Appends created Result to ArrayList<Result> in DataStore and Student.
 */
public class ResultService {

    public Result evaluateQuiz(Student student, Quiz quiz, HashMap<Integer, Character> studentAnswers) {
        int totalQuestions = quiz.getQuestionCount();
        int correctAnswers = 0;

        for (Question q : quiz.getQuestions()) {
            Character answer = studentAnswers.get(q.getQuestionId());
            if (answer != null && Character.toUpperCase(answer) == q.getCorrectAnswer()) {
                correctAnswers++;
            }
        }

        int wrongAnswers = totalQuestions - correctAnswers;
        int score = correctAnswers;
        double percentage = totalQuestions > 0 ? ((double) correctAnswers / totalQuestions) * 100.0 : 0.0;
        String status = percentage >= 50.0 ? "PASS" : "FAIL";

        Result result = new Result(
                student.getName(),
                quiz.getQuizName(),
                totalQuestions,
                correctAnswers,
                wrongAnswers,
                score,
                percentage,
                status
        );

        // Record in student profile and central storage
        student.addResult(result);
        student.addAttemptedQuiz(quiz.getQuizId());
        DataStore.results.add(result);

        return result;
    }
}
