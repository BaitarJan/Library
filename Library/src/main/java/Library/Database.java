package Library;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    // metoda pro získání připojení
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Připojeno k databázi!");
            } catch (SQLException e) {
                System.out.println("❌ Chyba při připojení: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }

    // metoda pro zavření připojení
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Připojení k databázi zavřeno.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}