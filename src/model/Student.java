package model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Student subclass inheriting from User.
 * Demonstrates:
 * - Inheritance: Extends base User class.
 * - Composition: Student HAS-A collection of Result objects and attempted Quiz IDs.
 * - Collections:
 *     - HashSet<Integer> guarantees unique tracking of attempted quiz IDs.
 *     - ArrayList<Result> stores sequential history of examination results.
 * - Polymorphism: Overrides displayProfile() for student academic details.
 */
public class Student extends User {
    private String course;

    // HashSet stores unique Quiz IDs that this student has already attempted.
    private HashSet<Integer> attemptedQuizIds;

    // ArrayList maintains the chronological list of all examination results for this student.
    private ArrayList<Result> results;

    public Student(int userId, String name, String username, String password, String course) {
        super(userId, name, username, password);
        this.course = course;
        this.attemptedQuizIds = new HashSet<>();
        this.results = new ArrayList<>();
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public HashSet<Integer> getAttemptedQuizIds() {
        return attemptedQuizIds;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void addAttemptedQuiz(int quizId) {
        attemptedQuizIds.add(quizId);
    }

    public boolean hasAttemptedQuiz(int quizId) {
        return attemptedQuizIds.contains(quizId);
    }

    public void addResult(Result result) {
        results.add(result);
    }

    @Override
    public void displayProfile() {
        System.out.println("========================================");
        System.out.println("           STUDENT PROFILE              ");
        System.out.println("========================================");
        System.out.println("Student ID : " + getUserId());
        System.out.println("Name       : " + getName());
        System.out.println("Username   : " + getUsername());
        System.out.println("Course     : " + course);
        System.out.println("Quizzes Taken: " + attemptedQuizIds.size());
        System.out.println("========================================");
    }
}
