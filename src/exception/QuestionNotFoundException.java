package exception;

/**
 * Thrown when an expected Question ID does not exist in the collection.
 */
public class QuestionNotFoundException extends Exception {
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
