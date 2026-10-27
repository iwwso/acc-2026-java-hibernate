package bootcamp.hibernate_practical.exception;

/**
 * Thrown when a book cannot be borrowed or returned in its current state.
 *
 * @author Karyna Litvinova
 */
public class BookStateException extends RuntimeException {

    public BookStateException(String message) {
        super(message);
    }
}