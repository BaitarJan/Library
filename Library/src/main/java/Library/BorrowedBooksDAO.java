package Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowedBooksDAO {

    // =================================================
    // 📕 ZÁPIS PŮJČENÍ
    // =================================================
    public void borrowBook(
            int bookId,
            int userId,
            LocalDate from,
            LocalDate to
    ) {
        String sql = """
            INSERT INTO borrowed_books
            (book_id, user_id, borrowed_from, borrowed_to)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            stmt.setInt(2, userId);
            stmt.setDate(3, java.sql.Date.valueOf(from));
            stmt.setDate(4, java.sql.Date.valueOf(to));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =================================================
    // 📗 VRÁCENÍ KNIHY (ODEBRÁNÍ Z AKTUÁLNÍCH)
    // =================================================
    public void returnBook(int bookId) {
        String sql = "DELETE FROM borrowed_books WHERE book_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =================================================
    // 📦 NAČTENÍ VŠECH AKTUÁLNĚ PŮJČENÝCH KNIH
    // =================================================
    public List<BorrowedBook> loadBorrowedBooks() {
        List<BorrowedBook> result = new ArrayList<>();

        String sql = """
            SELECT
                b.id,
                b.title,
                b.author,
                b.year_published,
                b.isbn,
                b.base_price,
                b.damage_percent,

                u.id AS user_id,
                u.name,
                u.email,
                u.blocked,
                u.debt,

                bb.borrowed_from,
                bb.borrowed_to
            FROM borrowed_books bb
            JOIN books b ON bb.book_id = b.id
            JOIN users u ON bb.user_id = u.id
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year_published"),
                        rs.getString("isbn"),
                        rs.getDouble("base_price"),
                        rs.getInt("damage_percent")
                );

                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getBoolean("blocked"),
                        rs.getDouble("debt")
                );

                BorrowedBook borrowedBook = new BorrowedBook(
                        book,
                        rs.getDate("borrowed_from").toLocalDate(),
                        rs.getDate("borrowed_to").toLocalDate(),
                        user
                );

                result.add(borrowedBook);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // =================================================
    // 📜 HISTORIE PŮJČENÍ (AUDIT LOG)
    // =================================================
    public void logBorrowHistory(
            int bookId,
            int userId,
            LocalDate from,
            LocalDate to,
            LocalDate returnedAt,
            int daysLate,
            double finalPrice,
            int damageAdded
    ) {
        String sql = """
            INSERT INTO borrow_history
            (book_id, user_id, borrowed_from, borrowed_to, returned_at,
             days_late, final_price, damage_added)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            stmt.setInt(2, userId);
            stmt.setDate(3, java.sql.Date.valueOf(from));
            stmt.setDate(4, java.sql.Date.valueOf(to));
            stmt.setDate(5, java.sql.Date.valueOf(returnedAt));
            stmt.setInt(6, daysLate);
            stmt.setDouble(7, finalPrice);
            stmt.setInt(8, damageAdded);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}