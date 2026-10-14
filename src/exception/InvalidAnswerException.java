package exception;

/**
 * Thrown when user enters an answer other than the accepted options ('A', 'B', 'C', 'D').
 */
public class InvalidAnswerException extends Exception {
    public InvalidAnswerException(String message) {
        super(message);
    }
}
