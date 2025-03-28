import java.util.Scanner;

public class StudentManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();
        StudentView view = new StudentView();

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Department: ");
                    String department = scanner.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = scanner.nextDouble();
                    dao.addStudent(new Student(0, name, department, marks));
                    break;

                case 2:
                    view.displayStudents(dao.getAllStudents());
                    break;

                case 3:
                    System.out.print("Enter Student ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter New Department: ");
                    String newDepartment = scanner.nextLine();
                    System.out.print("Enter New Marks: ");
                    double newMarks = scanner.nextDouble();
                    dao.updateStudent(id, newName, newDepartment, newMarks);
                    break;

                case 4:
                    System.out.print("Enter Student ID to delete: ");
                    int deleteId = scanner.nextInt();
                    dao.deleteStudent(deleteId);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
