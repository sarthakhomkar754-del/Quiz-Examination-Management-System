package exception;

/**
 * Thrown when an expected Quiz ID is not found in the system.
 */
public class QuizNotFoundException extends Exception {
    public QuizNotFoundException(String message) {
        super(message);
    }
}
