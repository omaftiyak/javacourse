package omaftiyak.javacourse.lab2.service;

import omaftiyak.javacourse.lab2.model.Abonent;
import omaftiyak.javacourse.lab2.model.Book;
import omaftiyak.javacourse.lab2.model.Library;
import omaftiyak.javacourse.lab2.model.Ticket;

import java.time.LocalDateTime;

/**
 * Service to manage tickets
 */
public class TicketService {

    private BookService bookService = new BookService();
    private AbonentService abonentService = new AbonentService();

    /**
     * Creates ticket for provided book and author id.
     *
     * @param bookId    id of book
     * @param abonentId id of abonent that takes book
     * @param pay       pay that abonent paid for book
     */
    public void takeBook(int bookId, int abonentId, int pay) {
        Book book = bookService.findBookById(bookId);
        Abonent abonent = abonentService.findAbonentById(abonentId);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        if (abonent == null) {
            throw new IllegalArgumentException("Abonent not found");
        }
        Ticket ticket = new Ticket(book.getId(), abonent.getId(), LocalDateTime.now(), null, pay);
        Library.getLibrary().getTickets().add(ticket);
    }

    /**
     * Returns book to library. Corresponding ticket will be assigned
     * with current date as return date and also will be moved to history
     *
     * @param bookId book id
     */
    public void returnBook(int bookId) {
        Ticket ticket = findTicketByBookId(bookId);
        if (ticket == null) {
            throw new IllegalArgumentException("For provided book id there is no any open tickets");
        }
        ticket.setDateReturning(LocalDateTime.now());
        Library.getLibrary().getTickets().remove(ticket);
        Library.getLibrary().getTicketHistory().add(ticket);
    }

    /**
     * Finds open ticket for provided book
     *
     * @param bookId book id
     * @return ticket or null if not found
     */
    public Ticket findTicketByBookId(int bookId) {
        for (Ticket ticket : Library.getLibrary().getTickets()) {
            if (ticket.getBookId() == bookId) {
                return ticket;
            }
        }
        return null;
    }


}
