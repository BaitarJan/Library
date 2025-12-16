package Library;

import java.util.List;


public class DataManager {

    private final BookList bookList;
    private final BooksDAO booksDAO;

    public DataManager() {
        this.bookList = new BookList();
        this.booksDAO = new BooksDAO();
        loadBooksFromDatabase();
    }

    // načtení všech knih z DB do interní paměti
    private void loadBooksFromDatabase() {
        List<Book> booksFromDB = booksDAO.loadBooksFromDatabase();
        for (Book b : booksFromDB) {
            bookList.addBook(b);
        }
    }

    // přidání nové knihy
    public void addBook(Book book) {
        booksDAO.addBook(book);      // vloží do DB
        bookList.addBook(book);      // vloží do interní paměti
    }

    // odstranění knihy
    public void removeBook(int id) {
        booksDAO.deleteBook(id);     // smaže z DB
        bookList.removeBook(id);     // přesune do deletedBooks
    }

    // vyhledání knihy podle ID
    public Book findBook(int id) {
        return bookList.findBook(id);
    }

    // výpis knih
    public void listBooks() {
        bookList.listBooks();
    }

    // výpis smazaných knih
    public void listDeletedBooks() {
        bookList.listDeletedBooks();
    }
}

