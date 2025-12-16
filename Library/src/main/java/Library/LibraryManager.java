package Library;

import java.time.LocalDate;
import java.util.List;

public class LibraryManager {

    private final DataManager dataManager;
    private final BorrowedBooksList borrowedBooks;

    private final BorrowedBooksDAO borrowedBooksDAO;
    private final BooksDAO booksDAO;

    public LibraryManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.borrowedBooks = new BorrowedBooksList();

        this.borrowedBooksDAO = new BorrowedBooksDAO();
        this.booksDAO = new BooksDAO();

        loadBorrowedBooksFromDatabase();
    }

    // =================================================
    // 📕 PŮJČENÍ KNIHY
    // =================================================
    public boolean borrowBook(
            int bookId,
            User user,
            List<DamageType> damageTypes,
            double borrowPercent
    ) {
        if (user.isBlocked()) {
            System.out.println("❌ Uživatel je blokován.");
            return false;
        }

        Book book = dataManager.findBook(bookId);
        if (book == null) {
            System.out.println("❌ Kniha s ID " + bookId + " nenalezena.");
            return false;
        }

        Damage damage = new Damage(damageTypes);

        // kniha zničena už při půjčení
        if (damage.isDestroyed()) {
            System.out.println("❌ Kniha je zničena a nebude půjčena.");
            dataManager.removeBook(bookId);
            booksDAO.updateDamage(bookId, damage.getTotalDamage());
            return false;
        }

        BorrowedBook borrowedBook = new BorrowedBook(
                book,
                LocalDate.now(),
                LocalDate.now().plusDays(14),
                user
        );

        // ===== DB =====
        borrowedBooksDAO.borrowBook(
                bookId,
                user.getId(),
                borrowedBook.getFrom(),
                borrowedBook.getTo()
        );

        // ===== PAMĚŤ =====
        borrowedBooks.addBorrowedBook(borrowedBook);
        dataManager.removeBook(bookId);

        Price price = new Price(book, borrowPercent);

        System.out.println("✅ Kniha půjčena:");
        System.out.println(borrowedBook);
        System.out.println(price);

        return true;
    }

    // =================================================
    // 📗 VRÁCENÍ KNIHY + POŠKOZENÍ
    // =================================================
    public boolean returnBook(int bookId, List<DamageType> newDamage) {
        BorrowedBook borrowedBook = borrowedBooks.returnBook(bookId);

        if (borrowedBook == null) {
            System.out.println("❌ Kniha nebyla půjčena.");
            return false;
        }

        // ⏰ zpoždění
        long daysLate = borrowedBook.daysRemaining();

        Price price = new Price(borrowedBook, 10); // 10 % půjčovné
        double finalPrice = price.calculatePrice(daysLate);

        if (daysLate < 0) {
            System.out.println("⏰ Kniha vrácena se zpožděním: " + (-daysLate) + " dní");
        }

        System.out.println("💰 Cena k úhradě: " +
                String.format("%.2f", finalPrice) + " Kč");

        // 🛠️ poškození
        Damage damage = new Damage(newDamage);
        int damageAdded = damage.getTotalDamage();

        int totalDamage = borrowedBook.getDamagePercent() + damageAdded;
        if (totalDamage > 100) totalDamage = 100;

        // 📜 HISTORIE
        borrowedBooksDAO.logBorrowHistory(
                bookId,
                borrowedBook.getBorrower().getId(),
                borrowedBook.getFrom(),
                borrowedBook.getTo(),
                LocalDate.now(),
                (int) Math.max(0, -daysLate),
                finalPrice,
                damageAdded
        );
        // ===== DB =====
        borrowedBooksDAO.returnBook(bookId);
        booksDAO.updateDamage(bookId, totalDamage);

        // ===== PAMĚŤ =====
        borrowedBook.setDamagePercent(totalDamage);

        if (totalDamage > 60) {
            System.out.println("❌ Kniha je zničena při vrácení.");
            dataManager.removeBook(bookId);
            return false;
        }

        dataManager.addBook(borrowedBook);

        System.out.println("✅ Kniha vrácena:");
        System.out.println(borrowedBook);

        return true;
    }
    // =================================================
    // 📚 VÝPISY
    // =================================================
    public void printAvailableBooks() {
        System.out.println("=== Dostupné knihy ===");
        dataManager.listBooks();
    }

    public void printBorrowedBooks() {
        System.out.println("=== Půjčené knihy ===");
        borrowedBooks.printAll();
    }

    public void printDeletedBooks() {
        System.out.println("=== Vymazané knihy ===");
        dataManager.listDeletedBooks();
    }
    private void loadBorrowedBooksFromDatabase() {
        List<BorrowedBook> borrowedFromDB = borrowedBooksDAO.loadBorrowedBooks();

        for (BorrowedBook b : borrowedFromDB) {
            borrowedBooks.addBorrowedBook(b);
            dataManager.removeBook(b.getId()); // odebereme z dostupných
        }

        System.out.println("📦 Načteno půjčených knih: " + borrowedFromDB.size());
    }



}