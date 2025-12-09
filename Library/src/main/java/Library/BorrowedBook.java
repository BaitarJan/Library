package Library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowedBook extends Book {
    private LocalDate from;      // datum půjčení
    private LocalDate to;        // datum vrácení
    private String borrower;     // kdo si půjčil knihu

    public BorrowedBook(Book book, LocalDate from, LocalDate to, String borrower) {
        super(book.getId(), book.getTitle(), book.getAutor());
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

    public String getBorrower() {
        return borrower;
    }

    //Vypočet zbývajících dnů minusové hodnoty znamena v prodlení
    public long daysRemaining(){
        return ChronoUnit.DAYS.between(LocalDate.now(),to);
}

@Override
   public String toString(){
    return super.toString() + " | Borrowed by: " + borrower
            + " from: " + from + " to: " + to
            + " | Days remaining: " + daysRemaining();
}





}
