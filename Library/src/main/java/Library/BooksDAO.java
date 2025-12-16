package Library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BooksDAO {

    // Vložení nové knihy do DB
    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, year_published, isbn, base_price, damage_percent) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getYearPublished());
            stmt.setString(4, book.getIsbn());
            stmt.setDouble(5, book.getBasePrice());
            stmt.setInt(6, book.getDamagePercent());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        book.setId(id); // uložíme ID do objektu
                        System.out.println("✅ Kniha byla úspěšně vložena s ID " + id);
                        logChange(id, "INSERT");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Načtení všech knih z DB
    public List<Book> loadBooksFromDatabase() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, title, author, year_published, isbn, base_price, damage_percent FROM books";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year_published");
                String isbn = rs.getString("isbn");
                double basePrice = rs.getDouble("base_price");
                int damagePercent = rs.getInt("damage_percent");

                Book book = new Book(id, title, author, year, isbn, basePrice, damagePercent);
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // Smazání knihy z DB
    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Kniha s ID " + id + " byla smazána z databáze.");
                logChange(id, "DELETE");
            } else {
                System.out.println("⚠️ Kniha s ID " + id + " nebyla nalezena.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Zapíše změnu do tabulky 'changes'
    private void logChange(int bookId, String action) {
        String sql = "INSERT INTO changes (book_id, action, change_time) VALUES (?, ?, NOW())";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            stmt.setString(2, action);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateDamage(int bookId, int newDamagePercent) {
        String sql = "UPDATE books SET damage_percent = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newDamagePercent);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();

            System.out.println("🛠️ Poškození knihy aktualizováno v DB");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}