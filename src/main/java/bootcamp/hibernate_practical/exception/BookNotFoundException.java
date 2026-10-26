package bootcamp.hibernate_practical.exception;

/**
 * Thrown when no book exists for the requested id.
 *
 * @author Karyna Litvinova
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Book not found with id " + id);
    }
}