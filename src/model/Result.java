package model;

/**
 * Result model representing the outcome of a student's quiz attempt.
 * Demonstrates Encapsulation with private fields and formatted output.
 */
public class Result {
    private String studentName;
    private String quizName;
    private int totalQuestions;
    private int correctAnswers;
    private int wrongAnswers;
    private int score;
    private double percentage;
    private String status; // "PASS" or "FAIL"

    public Result(String studentName, String quizName, int totalQuestions, int correctAnswers, int wrongAnswers, int score, double percentage, String status) {
        this.studentName = studentName;
        this.quizName = quizName;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.score = score;
        this.percentage = percentage;
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getQuizName() {
        return quizName;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public int getScore() {
        return score;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getStatus() {
        return status;
    }

    public void displayResult() {
        System.out.println("================================");
        System.out.println("             RESULT             ");
        System.out.println("================================");
        System.out.println("Student: " + studentName);
        System.out.println("Quiz: " + quizName);
        System.out.println();
        System.out.println("Total Questions : " + totalQuestions);
        System.out.println("Correct Answers : " + correctAnswers);
        System.out.println("Wrong Answers   : " + wrongAnswers);
        System.out.println("Score           : " + score + "/" + totalQuestions);
        System.out.printf("Percentage      : %.1f%%%n", percentage);
        System.out.println();
        System.out.println("Status          : " + status);
        System.out.println("================================");
    }
}
