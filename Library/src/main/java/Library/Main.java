package Library;

public class Main {
    public static void main(String[] args) {

        BooksDAO dao = new BooksDAO();

        // vytvoření nové knihy
        Book book = new Book(1,"Hobit", "J. R. R. Tolkien", 1937, "978-80-00-001",0,0);

        // vložení knihy do databáze
        dao.addBook(book);

        // zavření připojení
        Database.closeConnection();
    }
}