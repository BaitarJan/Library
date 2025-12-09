package Library;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        LibraryManager manager = new LibraryManager();

        // Přidání knih
        manager.addBook(new Book(1, "Duna", "Frank Herbert"));
        manager.addBook(new Book(2, "Zaklínač", "Andrzej Sapkowski"));
        manager.addBook(new Book(3, "Jádro Java", "Cay S. Horstmann"));

        System.out.println("\n--- Dostupné knihy ---");
        manager.printAvailableBooks();

        // Půjčení knihy 1 (poškození < 60%)
        manager.borrowBook(
                1,
                "Jan Novák",
                List.of(DamageType.DIRTY, DamageType.TORN_PAGES), // 10+20 = 30%
                200, // cena nové knihy
                15   // půjčovací procento
        );

        // Půjčení knihy 2 (poškození > 60%)
        manager.borrowBook(
                2,
                "Petr Svoboda",
                List.of(DamageType.WET, DamageType.TORN_PAGES,DamageType.COVER_TORN), // 35+20 = 55% + další např. DIRTY 10% = 65%
                250,
                15
        );

        System.out.println("\n--- Dostupné knihy po půjčení ---");
        manager.printAvailableBooks();

        System.out.println("\n--- Vymazané knihy ---");
        manager.listDeletedBooks();

        // Vrácení knihy 1
        manager.returnBook(1);

        System.out.println("\n--- Dostupné knihy po vrácení ---");
        manager.printAvailableBooks();

        System.out.println("\n--- Půjčené knihy ---");
        manager.printBorrowedBooks();
    }
}