import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    static final String URL = "jdbc:sqlite:products.db"; // SQLite database file

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL);
             Scanner scanner = new Scanner(System.in)) {

            Class.forName("org.sqlite.JDBC");
            createTable(conn); // Ensure the table exists

            while (true) {
                System.out.println("\n--- Product CRUD Menu ---");
                System.out.println("1. Create Product");
                System.out.println("2. Read Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        createProduct(conn, scanner);
                        break;
                    case 2:
                        readProducts(conn);
                        break;
                    case 3:
                        updateProduct(conn, scanner);
                        break;
                    case 4:
                        deleteProduct(conn, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice, try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Product ("
                   + "ProductID INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "ProductName TEXT NOT NULL, "
                   + "Price REAL NOT NULL, "
                   + "Quantity INTEGER NOT NULL);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Product Name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        String sql = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
            System.out.println("Product added successfully!");
        }
    }

    private static void readProducts(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Product";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Product List ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ProductID") +
                                   ", Name: " + rs.getString("ProductName") +
                                   ", Price: " + rs.getDouble("Price") +
                                   ", Quantity: " + rs.getInt("Quantity"));
            }
        }
    }

    private static void updateProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Product ID to update: ");
        int id = scanner.nextInt();
        System.out.print("Enter new Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new Quantity: ");
        int quantity = scanner.nextInt();

        String sql = "UPDATE Product SET Price = ?, Quantity = ? WHERE ProductID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, price);
            pstmt.setInt(2, quantity);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Product updated successfully!" : "Product not found.");
        }
    }

    private static void deleteProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Product ID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM Product WHERE ProductID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Product deleted successfully!" : "Product not found.");
        }
    }
}
