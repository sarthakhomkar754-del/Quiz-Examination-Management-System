package main;

import exception.*;
import model.Admin;
import model.Question;
import model.Quiz;
import model.Result;
import model.Student;
import repository.DataStore;
import service.AdminService;
import service.QuizService;
import service.ResultService;
import service.StudentService;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Main application console driver for Quiz & Examination Management System.
 * 
 * Strict Academic Implementation:
 * - Pure Core Java: OOP, Collections Framework, Exception Handling.
 * - In-memory storage via Collections.
 * - No Database, No File I/O, No Multithreading, No External Libraries.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminService adminService = new AdminService();
    private static final StudentService studentService = new StudentService();
    private static final QuizService quizService = new QuizService();
    private static final ResultService resultService = new ResultService();

    public static void main(String[] args) {
        // Initialize sample data in memory
        DataStore.initSampleData();

        boolean running = true;
        while (running) {
            System.out.println("========================================");
            System.out.println("       QUIZ & EXAMINATION SYSTEM        ");
            System.out.println("========================================");
            System.out.println();
            System.out.println("1. Admin Login");
            System.out.println("2. Student Registration");
            System.out.println("3. Student Login");
            System.out.println("4. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleAdminLogin();
                    break;
                case "2":
                    handleStudentRegistration();
                    break;
                case "3":
                    handleStudentLogin();
                    break;
                case "4":
                    System.out.println();
                    System.out.println("Thank you for using the Quiz & Examination System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println();
                    System.out.println("ERROR: Invalid menu choice! Please enter 1, 2, 3, or 4.");
                    System.out.println();
            }
        }
        scanner.close();
    }

    // =========================================================================
    // ADMIN FLOWS
    // =========================================================================

    private static void handleAdminLogin() {
        System.out.println();
        System.out.println("--- ADMIN LOGIN ---");
        System.out.println("(Hint: Default login is username: admin | password: admin123)");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        try {
            Admin admin = adminService.login(username, password);
            System.out.println();
            System.out.println("Login Successful! Welcome, " + admin.getName() + ".");
            runAdminDashboard(admin);
        } catch (InvalidLoginException e) {
            System.out.println();
            System.out.println("ERROR: " + e.getMessage());
            System.out.println();
        }
    }

    private static void runAdminDashboard(Admin admin) {
        boolean inSession = true;
        while (inSession) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("             ADMIN DASHBOARD            ");
            System.out.println("========================================");
            System.out.println();
            System.out.println("1. Add Question");
            System.out.println("2. View Questions");
            System.out.println("3. Update Question");
            System.out.println("4. Delete Question");
            System.out.println("5. Create Quiz");
            System.out.println("6. View Quizzes");
            System.out.println("7. View Students");
            System.out.println("8. View All Results");
            System.out.println("9. Logout");
            System.out.println();
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    handleAddQuestion();
                    break;
                case "2":
                    adminService.viewQuestions();
                    break;
                case "3":
                    handleUpdateQuestion();
                    break;
                case "4":
                    handleDeleteQuestion();
                    break;
                case "5":
                    handleCreateQuiz();
                    break;
                case "6":
                    adminService.viewQuizzes();
                    break;
                case "7":
                    adminService.viewStudents();
                    break;
                case "8":
                    adminService.viewAllResults();
                    break;
                case "9":
                    System.out.println("Admin logged out successfully.");
                    inSession = false;
                    break;
                default:
                    System.out.println("ERROR: Invalid choice! Please select from 1 to 9.");
            }
        }
    }

    private static void handleAddQuestion() {
        try {
            System.out.print("Enter Question ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            if (DataStore.questionMap.containsKey(id)) {
                System.out.println("ERROR: A question with ID " + id + " already exists.");
                return;
            }

            System.out.print("Enter Question Text: ");
            String text = scanner.nextLine().trim();

            System.out.print("Enter Option A: ");
            String optA = scanner.nextLine().trim();

            System.out.print("Enter Option B: ");
            String optB = scanner.nextLine().trim();

            System.out.print("Enter Option C: ");
            String optC = scanner.nextLine().trim();

            System.out.print("Enter Option D: ");
            String optD = scanner.nextLine().trim();

            System.out.print("Enter Correct Answer (A/B/C/D): ");
            String ansStr = scanner.nextLine().trim().toUpperCase();

            if (ansStr.length() != 1 || "ABCD".indexOf(ansStr.charAt(0)) == -1) {
                throw new InvalidAnswerException("Correct answer must be one of: A, B, C, or D.");
            }

            adminService.addQuestion(id, text, optA, optB, optC, optD, ansStr.charAt(0));
            System.out.println("Question added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Question ID must be a valid integer.");
        } catch (InvalidAnswerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void handleUpdateQuestion() {
        try {
            System.out.print("Enter Question ID to update: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Question existing = DataStore.questionMap.get(id);
            if (existing == null) {
                throw new QuestionNotFoundException("Question with ID " + id + " was not found.");
            }

            System.out.println("Current Question: " + existing.getQuestionText());
            System.out.print("Enter New Question Text (Leave blank to keep current): ");
            String text = scanner.nextLine().trim();
            if (text.isEmpty()) text = existing.getQuestionText();

            System.out.print("Enter New Option A (Leave blank to keep current): ");
            String optA = scanner.nextLine().trim();
            if (optA.isEmpty()) optA = existing.getOptionA();

            System.out.print("Enter New Option B (Leave blank to keep current): ");
            String optB = scanner.nextLine().trim();
            if (optB.isEmpty()) optB = existing.getOptionB();

            System.out.print("Enter New Option C (Leave blank to keep current): ");
            String optC = scanner.nextLine().trim();
            if (optC.isEmpty()) optC = existing.getOptionC();

            System.out.print("Enter New Option D (Leave blank to keep current): ");
            String optD = scanner.nextLine().trim();
            if (optD.isEmpty()) optD = existing.getOptionD();

            System.out.print("Enter Correct Answer (A/B/C/D, or blank to keep " + existing.getCorrectAnswer() + "): ");
            String ansStr = scanner.nextLine().trim().toUpperCase();
            char correct = existing.getCorrectAnswer();
            if (!ansStr.isEmpty()) {
                if (ansStr.length() != 1 || "ABCD".indexOf(ansStr.charAt(0)) == -1) {
                    throw new InvalidAnswerException("Correct answer must be A, B, C, or D.");
                }
                correct = ansStr.charAt(0);
            }

            adminService.updateQuestion(id, text, optA, optB, optC, optD, correct);
            System.out.println("Question updated successfully!");

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Question ID must be a valid integer.");
        } catch (QuestionNotFoundException | InvalidAnswerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void handleDeleteQuestion() {
        try {
            System.out.print("Enter Question ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            adminService.deleteQuestion(id);
            System.out.println("Question deleted successfully!");
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Question ID must be an integer.");
        } catch (QuestionNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void handleCreateQuiz() {
        try {
            System.out.print("Enter Quiz ID: ");
            int quizId = Integer.parseInt(scanner.nextLine().trim());

            if (DataStore.quizMap.containsKey(quizId)) {
                System.out.println("ERROR: Quiz with ID " + quizId + " already exists.");
                return;
            }

            System.out.print("Enter Quiz Name: ");
            String quizName = scanner.nextLine().trim();

            adminService.createQuiz(quizId, quizName);
            System.out.println("Quiz created successfully!");

            System.out.print("Would you like to add questions now? (Y/N): ");
            String addMore = scanner.nextLine().trim();
            if (addMore.equalsIgnoreCase("y") || addMore.equalsIgnoreCase("yes")) {
                adminService.viewQuestions();
                System.out.print("Enter Question IDs to add (comma-separated, e.g. 101,102): ");
                String[] qIds = scanner.nextLine().split(",");
                for (String qIdStr : qIds) {
                    try {
                        int qId = Integer.parseInt(qIdStr.trim());
                        adminService.addQuestionToQuiz(quizId, qId);
                        System.out.println("Added Question ID " + qId + " to quiz.");
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid ID: " + qIdStr);
                    } catch (QuizNotFoundException | QuestionNotFoundException e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Quiz ID must be a valid integer.");
        }
    }

    // =========================================================================
    // STUDENT REGISTRATION & LOGIN FLOWS
    // =========================================================================

    private static void handleStudentRegistration() {
        System.out.println();
        System.out.println("--- STUDENT REGISTRATION ---");
        try {
            System.out.print("Enter Numeric Student ID (e.g. 102): ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Desired Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine().trim();

            System.out.print("Enter Course (e.g. MCA, B.Tech, BCA): ");
            String course = scanner.nextLine().trim();

            Student student = studentService.register(id, name, username, password, course);
            System.out.println();
            System.out.println("Registration Successful! Welcome, " + student.getName() + ".");
            System.out.println("You can now login from the main menu.");
            System.out.println();

        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("ERROR: Student ID must be an integer (e.g. 101, 102).");
            System.out.println();
        } catch (DuplicateUserException | IllegalArgumentException e) {
            System.out.println();
            System.out.println("ERROR: " + e.getMessage());
            System.out.println();
        }
    }

    private static void handleStudentLogin() {
        System.out.println();
        System.out.println("--- STUDENT LOGIN ---");
        System.out.println("(Hint: Default login is username: sarthak | password: pass123)");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        try {
            Student student = studentService.login(username, password);
            System.out.println();
            System.out.println("Login Successful! Welcome, " + student.getName() + ".");
            runStudentDashboard(student);
        } catch (InvalidLoginException e) {
            System.out.println();
            System.out.println("ERROR: " + e.getMessage());
            System.out.println();
        }
    }

    private static void runStudentDashboard(Student student) {
        boolean inSession = true;
        while (inSession) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("            STUDENT DASHBOARD           ");
            System.out.println("========================================");
            System.out.println();
            System.out.println("1. View Available Quizzes");
            System.out.println("2. Start Quiz (by Quiz ID)");
            System.out.println("3. Take Random Practice Quiz (Random Java Questions)");
            System.out.println("4. View My Results");
            System.out.println("5. View Profile");
            System.out.println("6. Logout");
            System.out.println();
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    studentService.viewAvailableQuizzes();
                    break;
                case "2":
                    handleStartQuiz(student);
                    break;
                case "3":
                    handleRandomQuiz(student);
                    break;
                case "4":
                    studentService.viewMyResults(student);
                    break;
                case "5":
                    // Polymorphic call
                    student.displayProfile();
                    break;
                case "6":
                    System.out.println("Student logged out successfully.");
                    inSession = false;
                    break;
                default:
                    System.out.println("ERROR: Invalid choice! Please select 1 to 6.");
            }
        }
    }

    // =========================================================================
    // QUIZ EXAMINATION FLOW
    // =========================================================================

    private static void handleStartQuiz(Student student) {
        studentService.viewAvailableQuizzes();
        if (DataStore.quizzes.isEmpty()) {
            return;
        }

        try {
            System.out.print("Enter Quiz ID: ");
            String idInput = scanner.nextLine().trim();
            if (idInput.isEmpty()) {
                return;
            }
            int quizId = Integer.parseInt(idInput);

            Quiz quiz = quizService.getQuizById(quizId);
            quizService.validateQuizHasQuestions(quiz);

            if (student.hasAttemptedQuiz(quizId)) {
                System.out.print("NOTICE: You have already attempted this quiz. Retake to improve score? (Y/N): ");
                String retake = scanner.nextLine().trim();
                if (!retake.equalsIgnoreCase("y") && !retake.equalsIgnoreCase("yes")) {
                    System.out.println("Returning to dashboard.");
                    return;
                }
            }

            // Ask if user wants questions shuffled/randomized
            System.out.print("Shuffle question order randomly for this attempt? (Y/N, default Y): ");
            String shuffleChoice = scanner.nextLine().trim();
            boolean shuffle = shuffleChoice.isEmpty() || shuffleChoice.equalsIgnoreCase("y") || shuffleChoice.equalsIgnoreCase("yes");

            Quiz examQuiz = quiz;
            if (shuffle) {
                examQuiz = new Quiz(quiz.getQuizId(), quiz.getQuizName());
                for (Question q : quiz.getRandomizedQuestions()) {
                    examQuiz.addQuestion(q);
                }
            }

            conductExam(student, examQuiz);

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Quiz ID must be a valid integer.");
        } catch (QuizNotFoundException | NoQuestionsException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void handleRandomQuiz(Student student) {
        try {
            int totalAvailable = DataStore.questions.size();
            if (totalAvailable == 0) {
                throw new NoQuestionsException("No questions available in the question bank.");
            }

            System.out.println("--- RANDOM JAVA PRACTICE TEST ---");
            System.out.println("Total questions available in bank: " + totalAvailable);
            System.out.print("How many random questions would you like to attempt? (1-" + totalAvailable + ", default 5): ");
            String countInput = scanner.nextLine().trim();
            int count = 5;
            if (!countInput.isEmpty()) {
                count = Integer.parseInt(countInput);
                if (count <= 0 || count > totalAvailable) {
                    System.out.println("NOTICE: Adjusting question count to " + Math.min(Math.max(1, count), totalAvailable));
                    count = Math.min(Math.max(1, count), totalAvailable);
                }
            }

            Quiz randomQuiz = quizService.createRandomizedQuiz("Java Random Practice Test (" + count + " Questions)", count);
            conductExam(student, randomQuiz);

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Please enter a valid number of questions.");
        } catch (NoQuestionsException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /**
     * Conducts the quiz examination process question-by-question with input validation.
     * Demonstrates:
     * - Collections: HashMap<Integer, Character> to store student answers.
     * - Exception: InvalidAnswerException caught gracefully on non-A/B/C/D input.
     */
    private static void conductExam(Student student, Quiz quiz) {
        System.out.println();
        System.out.println("========================================");
        System.out.println(" Starting Quiz: " + quiz.getQuizName());
        System.out.println(" Total Questions: " + quiz.getQuestionCount());
        System.out.println(" Please enter A, B, C, or D for each answer.");
        System.out.println("========================================");

        // HashMap stores Student Answers: Question ID -> Answer Character
        HashMap<Integer, Character> studentAnswers = new HashMap<>();

        for (int i = 0; i < quiz.getQuestionCount(); i++) {
            Question q = quiz.getQuestions().get(i);

            boolean validAnswer = false;
            while (!validAnswer) {
                System.out.println();
                System.out.println("================================");
                System.out.println("Question " + (i + 1) + " of " + quiz.getQuestionCount());
                System.out.println("================================");
                System.out.println();
                System.out.println(q.getQuestionText());
                System.out.println();
                System.out.println("A. " + q.getOptionA());
                System.out.println("B. " + q.getOptionB());
                System.out.println("C. " + q.getOptionC());
                System.out.println("D. " + q.getOptionD());
                System.out.println();
                System.out.print("Enter your answer (A/B/C/D): ");

                String input = scanner.nextLine().trim().toUpperCase();

                try {
                    if (input.length() != 1 || "ABCD".indexOf(input.charAt(0)) == -1) {
                        throw new InvalidAnswerException("Invalid answer '" + input + "'! You must enter only A, B, C, or D.");
                    }

                    studentAnswers.put(q.getQuestionId(), input.charAt(0));
                    validAnswer = true;

                } catch (InvalidAnswerException e) {
                    System.out.println();
                    System.out.println("ERROR: " + e.getMessage());
                }
            }
        }

        // Grade the quiz and generate result
        Result result = resultService.evaluateQuiz(student, quiz, studentAnswers);
        System.out.println();
        result.displayResult();

        // Detailed review of correct vs student answers
        System.out.println();
        System.out.println("========================================");
        System.out.println("       EXAMINATION ANSWER REVIEW        ");
        System.out.println("========================================");
        for (int i = 0; i < quiz.getQuestionCount(); i++) {
            Question q = quiz.getQuestions().get(i);
            char yourAns = studentAnswers.get(q.getQuestionId());
            boolean isCorrect = (yourAns == q.getCorrectAnswer());

            System.out.println("Q" + (i + 1) + ": " + q.getQuestionText());
            System.out.println("    Your Answer   : " + yourAns);
            System.out.println("    Correct Answer: " + q.getCorrectAnswer());
            System.out.println("    Status        : " + (isCorrect ? "CORRECT (+1)" : "WRONG (0)"));
            System.out.println("----------------------------------------");
        }
        System.out.println();
    }
}
