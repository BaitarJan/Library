package Library;

import java.time.LocalDate;
import java.util.List;

public class LibraryManager {

    private final BookList availableBooks = new BookList();
    private final BorrowedBooksList borrowedBooks = new BorrowedBooksList();

    // Přidání nové knihy do knihovny
    public void addBook(Book book) {
        availableBooks.addBook(book);
    }

    // Půjčení knihy (kontrola poškození a přesun do vymazaných, pokud >60%)
    public boolean borrowBook(int id, String borrower, List<DamageType> damageTypes, double basePrice, double borrowPercent) {
        Book book = availableBooks.findBook(id);
        if (book == null) {
            System.out.println("Kniha s ID " + id + " nenalezena.");
            return false;
        }

        Damage damage = new Damage(damageTypes);

        if (damage.isDestroyed()) {
            System.out.println("Kniha je zničena a přesunuta do vymazaných knih.");
            availableBooks.removeBook(id); // přesune do deletedBooks
            return false;
        }

        BorrowedBook borrowedBook = new BorrowedBook(book, LocalDate.now(), LocalDate.now().plusDays(14), borrower);
        borrowedBooks.addBorrowedBook(borrowedBook);
        availableBooks.removeBook(id); // odstranění z dostupných

        Price price = new Price(book, basePrice, damage, borrowPercent);

        System.out.println("Kniha půjčena: " + borrowedBook);
        System.out.println("Půjčovací cena: " + String.format("%.2f", price.calculatePrice()) + " Kč");

        return true;
    }

    // Vrácení knihy
    public boolean returnBook(int id) {
        BorrowedBook book = borrowedBooks.returnBook(id);
        if (book == null) {
            System.out.println("Půjčená kniha s ID " + id + " nenalezena.");
            return false;
        }

        availableBooks.addBook(book);
        System.out.println("Kniha vrácena: " + book);
        return true;
    }

    // Výpis dostupných knih
    public void printAvailableBooks() {
        System.out.println("=== Dostupné knihy ===");
        availableBooks.listBooks();
    }

    // Výpis půjčených knih
    public void printBorrowedBooks() {
        System.out.println("=== Půjčené knihy ===");
        borrowedBooks.printAll();
    }

    // Výpis vymazaných knih
    public void listDeletedBooks() {
        System.out.println("=== Vymazané knihy ===");
        availableBooks.listDeletedBooks();
    }
}
