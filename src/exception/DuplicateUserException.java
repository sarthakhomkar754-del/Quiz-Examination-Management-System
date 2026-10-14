package exception;

/**
 * Thrown when attempting to register with a username that is already taken.
 */
public class DuplicateUserException extends Exception {
    public DuplicateUserException(String message) {
        super(message);
    }
}
