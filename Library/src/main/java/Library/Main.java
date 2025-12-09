package Library;

import Library.BorrowedBook;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        // Seznam dostupných knih
        BookList books = new BookList();

        books.addBook(new Book(1, "Duna", "Frank Herbert"));
        books.addBook(new Book(2, "Zaklínač", "Andrzej Sapkowski"));
        books.addBook(new Book(3, "Jádro Java", "Cay S. Horstmann"));

        System.out.println("=== Původní seznam knih ===");
        books.printBooks();

        // Seznam půjčených knih
        BorrowedBooksList borrowedList = new BorrowedBooksList();

        // Půjčíme knihu č. 2
        Book bookToBorrow = books.findBook(2);
        if (bookToBorrow != null) {
            BorrowedBook bb = new BorrowedBook(
                    bookToBorrow,
                    LocalDate.now(),
                    LocalDate.now().plusDays(14),
                    "Jan Novák"
            );

            borrowedList.addBorrowedBook(bb);
            books.removeBook(2); // odstraníme z dostupných
        }

        System.out.println("\n=== Půjčené knihy ===");
        borrowedList.printAll();

        System.out.println("\n=== Dostupné knihy pro půjčení ===");
        books.printBooks();

        // Vrácení knihy
        BorrowedBook returned = borrowedList.returnBook(2);
        if (returned != null) {
            books.addBook(returned); // vracíme zpátky mezi dostupné
        }

        System.out.println("\n=== Po vrácení knihy ===");
        books.printBooks();
    }
}