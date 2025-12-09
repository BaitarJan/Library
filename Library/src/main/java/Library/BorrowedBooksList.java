package Library;


import java.util.LinkedList;
import java.util.List;

public class BorrowedBooksList {

    private List<BorrowedBook> borrowed = new LinkedList<>();

    // Přidání půjčené knihy
    public void addBorrowedBook(BorrowedBook b) {
        borrowed.add(b);
    }

    // Vrácení knihy podle ID
    public BorrowedBook returnBook(int id) {
        for (BorrowedBook b : borrowed) {
            if (b.getId() == id) {
                borrowed.remove(b);
                return b;  // vracíme objekt pro další použití
            }
        }
        return null; // nenašlo se nic
    }

    // Najít půjčenou knihu podle ID
    public BorrowedBook findBorrowed(int id) {
        for (BorrowedBook b : borrowed) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    // Výpis všech půjčených knih
    public void printAll() {
        for (BorrowedBook b : borrowed) {
            System.out.println(b);
        }
    }
}

