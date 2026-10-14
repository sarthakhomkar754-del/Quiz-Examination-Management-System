package service;

import exception.DuplicateUserException;
import exception.InvalidLoginException;
import model.Quiz;
import model.Result;
import model.Student;
import repository.DataStore;

/**
 * Service managing student registration, login, and profile operations.
 * Demonstrates:
 * - Collections: HashSet<String> to ensure unique usernames in O(1) time.
 * - Exception Handling: Throws DuplicateUserException and InvalidLoginException.
 */
public class StudentService {

    public Student register(int id, String name, String username, String password, String course)
            throws DuplicateUserException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        String cleanUsername = username.trim().toLowerCase();

        // HashSet ensures O(1) lookup to prevent duplicate usernames
        if (DataStore.usernames.contains(cleanUsername)) {
            throw new DuplicateUserException("Username '" + username + "' is already taken. Please choose another.");
        }

        Student student = new Student(id, name, cleanUsername, password, course);
        DataStore.students.add(student);
        DataStore.usernames.add(cleanUsername);
        return student;
    }

    public Student login(String username, String password) throws InvalidLoginException {
        if (username == null || password == null) {
            throw new InvalidLoginException("Username and password cannot be null.");
        }
        String cleanUsername = username.trim().toLowerCase();

        for (Student s : DataStore.students) {
            if (s.getUsername().equals(cleanUsername) && s.getPassword().equals(password)) {
                return s;
            }
        }
        throw new InvalidLoginException("Invalid Student credentials.");
    }

    public void viewAvailableQuizzes() {
        if (DataStore.quizzes.isEmpty()) {
            System.out.println("No quizzes are currently available.");
            return;
        }
        System.out.println("====================================");
        System.out.println("         AVAILABLE QUIZZES          ");
        System.out.println("====================================");
        for (Quiz q : DataStore.quizzes) {
            System.out.println("Quiz ID   : " + q.getQuizId());
            System.out.println("Quiz Name : " + q.getQuizName());
            System.out.println("Questions : " + q.getQuestionCount());
            System.out.println("------------------------------------");
        }
    }

    public void viewMyResults(Student student) {
        if (student.getResults().isEmpty()) {
            System.out.println("You have not taken any quizzes yet.");
            return;
        }
        System.out.println("====================================");
        System.out.println("      MY EXAMINATION RESULTS        ");
        System.out.println("====================================");
        for (Result r : student.getResults()) {
            r.displayResult();
            System.out.println();
        }
    }
}
