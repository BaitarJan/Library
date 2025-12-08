package Library;

import Library.Book;
import Library.BookList;

public class Main {
    public static void main(String[] args) {
        BookList library = new BookList();

        // Přidání knih
        library.addBook(new Book(1, "1984", "George Orwell"));
        library.addBook(new Book(2, "Malý princ", "Antoine de Saint-Exupéry"));
        library.addBook(new Book(3, "Harry Potter", "J.K. Rowling"));

        System.out.println("=== Seznam knih před smazáním ===");
        library.listBooks();

        // Smazání knihy s id = 2
        library.removeBook(2);

        System.out.println("\n=== Seznam knih po smazání ===");
        library.listBooks();

        System.out.println("\n=== Smazané knihy (archiv) ===");
        library.listDeletedBooks();
    }
}