package Library;

import java.util.ArrayList;
import java.util.LinkedList;

public class BookList {

// Seznam knih přez linked list

    final LinkedList<Book> books = new LinkedList<>();
    final ArrayList<Book> deletedBooks = new ArrayList<>();

    public void addBook(Book book){
        books.add(book);
    }

    public void removeBook(int id){
       //přesun vymazané knihy do vymazaných
        for (Book b : books) {
            if (b.getId() == id) {
                deletedBooks.add(new Book(b.getId(), b.getTitle(), b.getAuthor()));
                break;
            }
        }
        books.removeIf(b -> b.getId() == id);
    }
// Výpis knih
    public void listBooks() {
        for (Book b : books) {
            System.out.println(b);
        }
    }
// Výpis vymazaných knih
    public void listDeletedBooks() {
        for (Book b : deletedBooks) {
            System.out.println(b);
        }
    }
}





