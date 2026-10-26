package bootcamp.hibernate_practical.service;

import bootcamp.hibernate_practical.dto.BookResponse;
import bootcamp.hibernate_practical.dto.CreateBookRequest;
import bootcamp.hibernate_practical.dto.UpdateBookRequest;
import bootcamp.hibernate_practical.entity.Book;
import bootcamp.hibernate_practical.exception.BookNotFoundException;
import bootcamp.hibernate_practical.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public BookResponse createBook(CreateBookRequest request) {
        Book book = new Book(
                request.getTitle(),
                request.getAuthor(),
                request.getGenre(),
                request.getPublicationYear(),
                true
        );
        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }

    public List<BookResponse> getAllBooks() {
        return mapToResponses(bookRepository.findAll());
    }

    public BookResponse getBookById(Long id) {
        return mapToResponse(findBookOrThrow(id));
    }

    @Transactional
    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        Book book = findBookOrThrow(id);
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setPublicationYear(request.getPublicationYear());
        book.setAvailable(request.isAvailable());
        return mapToResponse(bookRepository.save(book));
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    public List<BookResponse> findByAuthor(String author) {
        return mapToResponses(bookRepository.findByAuthor(author));
    }

    public List<BookResponse> findAvailableBooks() {
        return mapToResponses(bookRepository.findByAvailableTrue());
    }

    private Book findBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    private List<BookResponse> mapToResponses(List<Book> books) {
        return books.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private BookResponse mapToResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPublicationYear(),
                book.isAvailable()
        );
    }
}