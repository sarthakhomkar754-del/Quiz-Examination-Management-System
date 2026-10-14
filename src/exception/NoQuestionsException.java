package exception;

/**
 * Thrown when attempting to start an examination on a quiz that has no questions.
 */
public class NoQuestionsException extends Exception {
    public NoQuestionsException(String message) {
        super(message);
    }
}
