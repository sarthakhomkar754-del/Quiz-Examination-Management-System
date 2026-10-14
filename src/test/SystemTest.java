package test;

import exception.*;
import model.*;
import repository.DataStore;
import service.AdminService;
import service.QuizService;
import service.ResultService;
import service.StudentService;

import java.util.HashMap;

/**
 * Verification test suite validating OOP, Collections, and Exception Handling.
 */
public class SystemTest {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  RUNNING QUIZ SYSTEM VERIFICATION TEST SUITE   ");
        System.out.println("=================================================");

        int passed = 0;
        int failed = 0;

        DataStore.initSampleData();
        AdminService adminService = new AdminService();
        StudentService studentService = new StudentService();
        QuizService quizService = new QuizService();
        ResultService resultService = new ResultService();

        // ----------------------------------------------------
        // TEST 1: OOP - Polymorphic Profile Display
        // ----------------------------------------------------
        try {
            System.out.print("[TEST 1] OOP - Inheritance & Polymorphism (displayProfile)... ");
            User admin = DataStore.admins.get(0);
            User student = DataStore.students.get(0);

            if (admin instanceof Admin && student instanceof Student) {
                // Verify polymorphic execution does not throw
                admin.displayProfile();
                student.displayProfile();
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }

        // ----------------------------------------------------
        // TEST 2: Exception - InvalidLoginException (Admin & Student)
        // ----------------------------------------------------
        try {
            System.out.print("[TEST 2] Exception - InvalidLoginException on Wrong Password... ");
            adminService.login("admin", "wrongpassword");
            System.out.println("FAILED (Expected exception not thrown)");
            failed++;
        } catch (InvalidLoginException e) {
            System.out.println("PASSED (Caught: " + e.getMessage() + ")");
            passed++;
        } catch (Exception e) {
            System.out.println("FAILED (Wrong exception type: " + e.getClass().getSimpleName() + ")");
            failed++;
        }

        // ----------------------------------------------------
        // TEST 3: Exception - DuplicateUserException (HashSet check)
        // ----------------------------------------------------
        try {
            System.out.print("[TEST 3] Exception - DuplicateUserException on Duplicate Registration... ");
            studentService.register(102, "Duplicate Sarthak", "sarthak", "pwd", "MCA");
            System.out.println("FAILED (Expected exception not thrown)");
            failed++;
        } catch (DuplicateUserException e) {
            System.out.println("PASSED (Caught: " + e.getMessage() + ")");
            passed++;
        } catch (Exception e) {
            System.out.println("FAILED (Wrong exception type: " + e.getClass().getSimpleName() + ")");
            failed++;
        }

        // ----------------------------------------------------
        // TEST 4: Collections - HashMap O(1) Question & Quiz Lookup
        // ----------------------------------------------------
        try {
            System.out.print("[TEST 4] Collections - HashMap O(1) Lookup... ");
            Question q = DataStore.questionMap.get(101);
            Quiz quiz = DataStore.quizMap.get(1);

            if (q != null && quiz != null && q.getCorrectAnswer() == 'B' && quiz.getQuestionCount() >= 5) {
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED (Question or Quiz not found in HashMap)");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }

        // ----------------------------------------------------
        // TEST 5: Exception - QuestionNotFoundException
        // ----------------------------------------------------
        try {
            System.out.print("[TEST 5] Exception - QuestionNotFoundException on Delete Non-Existent ID... ");
            adminService.deleteQuestion(9999);
            System.out.println("FAILED (Expected exception not thrown)");
            failed++;
        } catch (QuestionNotFoundException e) {
            System.out.println("PASSED (Caught: " + e.getMessage() + ")");
            passed++;
        } catch (Exception e) {
            System.out.println("FAILED (Wrong exception type)");
            failed++;
        }

        // ----------------------------------------------------
        // TEST 6: Exception - QuizNotFoundException & NoQuestionsException
        // ----------------------------------------------------
        try {
            System.out.print("[TEST 6] Exception - QuizNotFoundException & NoQuestionsException... ");
            try {
                quizService.getQuizById(999);
                System.out.println("FAILED (Expected QuizNotFoundException not thrown)");
                failed++;
            } catch (QuizNotFoundException qe) {
                // Now test NoQuestionsException
                Quiz emptyQuiz = new Quiz(99, "Empty Quiz");
                DataStore.quizzes.add(emptyQuiz);
                DataStore.quizMap.put(99, emptyQuiz);
                quizService.validateQuizHasQuestions(emptyQuiz);
                System.out.println("FAILED (Expected NoQuestionsException not thrown)");
                failed++;
            }
        } catch (NoQuestionsException e) {
            System.out.println("PASSED (Caught: " + e.getMessage() + ")");
            passed++;
        } catch (Exception e) {
            System.out.println("FAILED (Wrong exception type: " + e.getClass().getSimpleName() + ")");
            failed++;
        }

        // ----------------------------------------------------
        // TEST 7: Collections - Student Answer Storage in HashMap<Integer, Character> & Evaluation
        // ----------------------------------------------------
        try {
            System.out.print("[TEST 7] Collections - Student Exam Flow & Result Calculation... ");
            Student student = DataStore.students.get(0);
            Quiz quiz = DataStore.quizMap.get(1);

            HashMap<Integer, Character> studentAnswers = new HashMap<>();
            studentAnswers.put(101, 'B'); // Correct
            studentAnswers.put(102, 'C'); // Correct
            studentAnswers.put(103, 'B'); // Correct
            studentAnswers.put(104, 'C'); // Correct
            studentAnswers.put(105, 'C'); // Correct
            studentAnswers.put(106, 'C'); // Correct

            Result result = resultService.evaluateQuiz(student, quiz, studentAnswers);

            if (result.getScore() == 6 && result.getPercentage() == 100.0 && "PASS".equals(result.getStatus())) {
                System.out.println("PASSED (Score: " + result.getScore() + "/6, Percentage: " + result.getPercentage() + "%)");
                passed++;
            } else {
                System.out.println("FAILED (Result calculation incorrect: " + result.getScore() + ")");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }

        // ----------------------------------------------------
        // TEST 8: Collections - HashSet Attempt Tracking & ArrayList History
        // ----------------------------------------------------
        try {
            System.out.print("[TEST 8] Collections - HashSet Attempt Tracking & ArrayList History... ");
            Student student = DataStore.students.get(0);

            boolean hasAttempted = student.hasAttemptedQuiz(1);
            boolean historyRecorded = !student.getResults().isEmpty() && !DataStore.results.isEmpty();

            if (hasAttempted && historyRecorded) {
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED (Attempt not recorded in HashSet or ArrayList)");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }

        // ----------------------------------------------------
        // TEST 9: Collections - Random Java Quiz Generation & Shuffling
        // ----------------------------------------------------
        try {
            System.out.print("[TEST 9] Collections - Random Questions Quiz Generation & Shuffling... ");
            Quiz randomQuiz = quizService.createRandomizedQuiz("Random Test", 5);
            java.util.ArrayList<Question> shuffled = randomQuiz.getRandomizedQuestions();

            if (randomQuiz.getQuestionCount() == 5 && shuffled.size() == 5) {
                System.out.println("PASSED (Created 5 random questions successfully)");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }

        System.out.println("=================================================");
        System.out.println("  SUMMARY: " + passed + " PASSED, " + failed + " FAILED");
        System.out.println("=================================================");
        if (failed > 0) {
            System.exit(1);
        }
    }
}
