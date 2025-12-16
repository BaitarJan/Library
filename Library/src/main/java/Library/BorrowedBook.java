package Library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowedBook extends Book {

    private LocalDate from;      // datum půjčení
    private LocalDate to;        // datum vrácení
    private User borrower;       // 👈 OPRAVA: User, ne String

    public BorrowedBook(Book book, LocalDate from, LocalDate to, User borrower) {
        super(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getYearPublished(),
                book.getIsbn(),
                book.getBasePrice(),
                book.getDamagePercent()
        );
        this.from = from;
        this.to = to;
        this.borrower = borrower;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public User getBorrower() {
        return borrower;
    }

    // Výpočet zbývajících dnů (mínus = zpoždění)
    public long daysRemaining() {
        return ChronoUnit.DAYS.between(LocalDate.now(), to);
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Borrowed by: " + borrower.getName()
                + " from: " + from
                + " to: " + to
                + " | Days remaining: " + daysRemaining();
    }
}