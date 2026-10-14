package service;

import exception.InvalidLoginException;
import exception.QuestionNotFoundException;
import exception.QuizNotFoundException;
import model.Admin;
import model.Question;
import model.Quiz;
import model.Result;
import model.Student;
import repository.DataStore;

/**
 * Service managing administrative operations.
 * Demonstrates:
 * - Collections: Managing ArrayList and HashMap entries.
 * - Exception Handling: Throws domain exceptions when records are missing or credentials fail.
 */
public class AdminService {

    public Admin login(String username, String password) throws InvalidLoginException {
        if (username == null || password == null) {
            throw new InvalidLoginException("Username and password cannot be null.");
        }
        for (Admin admin : DataStore.admins) {
            if (admin.getUsername().equalsIgnoreCase(username.trim()) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        throw new InvalidLoginException("Invalid Administrator credentials.");
    }

    public void addQuestion(int id, String text, String optA, String optB, String optC, String optD, char correct) {
        Question q = new Question(id, text, optA, optB, optC, optD, correct);
        DataStore.questions.add(q);
        DataStore.questionMap.put(id, q);
    }

    public void viewQuestions() {
        if (DataStore.questions.isEmpty()) {
            System.out.println("No questions found in the system.");
            return;
        }
        System.out.println("====================================");
        System.out.println("           ALL QUESTIONS            ");
        System.out.println("====================================");
        for (Question q : DataStore.questions) {
            q.displayQuestion();
            System.out.println("Correct Answer: " + q.getCorrectAnswer());
            System.out.println("------------------------------------");
        }
    }

    public void updateQuestion(int id, String text, String optA, String optB, String optC, String optD, char correct)
            throws QuestionNotFoundException {
        Question q = DataStore.questionMap.get(id);
        if (q == null) {
            throw new QuestionNotFoundException("Question with ID " + id + " was not found.");
        }
        q.setQuestionText(text);
        q.setOptionA(optA);
        q.setOptionB(optB);
        q.setOptionC(optC);
        q.setOptionD(optD);
        q.setCorrectAnswer(correct);
    }

    public void deleteQuestion(int id) throws QuestionNotFoundException {
        Question q = DataStore.questionMap.remove(id);
        if (q == null) {
            throw new QuestionNotFoundException("Question with ID " + id + " was not found.");
        }
        DataStore.questions.remove(q);

        // Also remove this question from any quiz referencing it
        for (Quiz quiz : DataStore.quizzes) {
            quiz.removeQuestion(id);
        }
    }

    public void createQuiz(int quizId, String quizName) {
        Quiz quiz = new Quiz(quizId, quizName);
        DataStore.quizzes.add(quiz);
        DataStore.quizMap.put(quizId, quiz);
    }

    public void addQuestionToQuiz(int quizId, int questionId) throws QuizNotFoundException, QuestionNotFoundException {
        Quiz quiz = DataStore.quizMap.get(quizId);
        if (quiz == null) {
            throw new QuizNotFoundException("Quiz with ID " + quizId + " was not found.");
        }
        Question question = DataStore.questionMap.get(questionId);
        if (question == null) {
            throw new QuestionNotFoundException("Question with ID " + questionId + " was not found.");
        }
        quiz.addQuestion(question);
    }

    public void viewQuizzes() {
        if (DataStore.quizzes.isEmpty()) {
            System.out.println("No quizzes created yet.");
            return;
        }
        System.out.println("====================================");
        System.out.println("            ALL QUIZZES             ");
        System.out.println("====================================");
        for (Quiz quiz : DataStore.quizzes) {
            System.out.println("Quiz ID   : " + quiz.getQuizId());
            System.out.println("Quiz Name : " + quiz.getQuizName());
            System.out.println("Questions : " + quiz.getQuestionCount());
            System.out.println("------------------------------------");
        }
    }

    public void viewStudents() {
        if (DataStore.students.isEmpty()) {
            System.out.println("No registered students found.");
            return;
        }
        System.out.println("====================================");
        System.out.println("        REGISTERED STUDENTS         ");
        System.out.println("====================================");
        for (Student s : DataStore.students) {
            System.out.println("ID       : " + s.getUserId());
            System.out.println("Name     : " + s.getName());
            System.out.println("Username : " + s.getUsername());
            System.out.println("Course   : " + s.getCourse());
            System.out.println("Attempts : " + s.getAttemptedQuizIds().size());
            System.out.println("------------------------------------");
        }
    }

    public void viewAllResults() {
        if (DataStore.results.isEmpty()) {
            System.out.println("No examination results available yet.");
            return;
        }
        System.out.println("====================================");
        System.out.println("        ALL EXAMINATION RESULTS     ");
        System.out.println("====================================");
        for (Result r : DataStore.results) {
            r.displayResult();
            System.out.println();
        }
    }
}
