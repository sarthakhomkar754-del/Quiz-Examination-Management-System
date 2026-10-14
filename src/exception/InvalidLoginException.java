package exception;

/**
 * Thrown when credentials supplied during login are invalid.
 */
public class InvalidLoginException extends Exception {
    public InvalidLoginException(String message) {
        super(message);
    }
}
