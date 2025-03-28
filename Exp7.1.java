// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConnection {
   public SQLiteConnection() {
   }

   public static void main(String[] var0) {
      String var1 = "jdbc:sqlite:employees.db";

      try {
         Class.forName("org.sqlite.JDBC");
         Connection var2 = DriverManager.getConnection(var1);
         System.out.println("Connected to SQLite database!");
         String var3 = "CREATE TABLE IF NOT EXISTS Employee (EmpID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT NOT NULL, Salary REAL NOT NULL);";
         Statement var4 = var2.createStatement();
         var4.execute(var3);
         System.out.println("Employee table is ready.");
         String var5 = "INSERT INTO Employee (Name, Salary) SELECT 'Alice', 50000.00 WHERE NOT EXISTS (SELECT 1 FROM Employee);";
         var4.execute(var5);
         ResultSet var6 = var4.executeQuery("SELECT * FROM Employee");
         System.out.println("Employee Details:");

         while(var6.next()) {
            PrintStream var10000 = System.out;
            int var10001 = var6.getInt("EmpID");
            var10000.println("EmpID: " + var10001 + ", Name: " + var6.getString("Name") + ", Salary: " + var6.getDouble("Salary"));
         }

         var6.close();
         var4.close();
         var2.close();
         System.out.println("Database operation completed.");
      } catch (ClassNotFoundException var7) {
         System.out.println("SQLite JDBC Driver not found. Ensure sqlite-jdbc-3.49.1.0.jar is in classpath.");
         var7.printStackTrace();
      } catch (SQLException var8) {
         var8.printStackTrace();
      }

   }
}
