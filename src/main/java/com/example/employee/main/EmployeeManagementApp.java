package com.example.employee.main;

import com.example.employee.config.DBConnection;
import com.example.employee.dao.DepartmentDAO;
import com.example.employee.dao.EmployeeDAO;
import com.example.employee.model.Department;
import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;
import com.example.employee.util.DatabaseInitializer;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagementApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final EmployeeService employeeService =
            new EmployeeService(new EmployeeDAO(), new DepartmentDAO());

    public static void main(String[] args) {
        try {
            DatabaseInitializer.initialize();
            System.out.println("Database initialized successfully.");

            boolean running = true;

            while (running) {
                showMenu();

                int choice = readInt("Enter your choice: ");

                try {
                    switch (choice) {

                        case 1 -> addEmployee();

                        case 2 -> viewAllEmployees();

                        case 3 -> searchEmployee();

                        case 4 -> updateEmployee();

                        case 5 -> deleteEmployee();

                        case 6 ->
                                employeeService
                                        .displayEmployeesDepartmentWise();

                        case 0 -> {
                            running = false;
                            System.out.println("Exiting application.");
                        }

                        default ->
                                System.out.println("Invalid menu choice.");
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println("Input error: " + e.getMessage());

                } catch (SQLException e) {
                    System.err.println(
                            "Database operation failed: "
                                    + e.getMessage()
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Could not initialize the application: "
                            + e.getMessage()
            );

        } finally {
            DBConnection.closePool();
            scanner.close();
        }
    }

    private static void showMenu() {
        System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Search Employee by ID");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Display Employees Department-wise");
        System.out.println("0. Exit");
    }

    private static void addEmployee() throws SQLException {

        String name = readText("Enter employee name: ");
        String email = readText("Enter employee email: ");

        double salary = readDouble("Enter employee salary: ");

        int departmentId = chooseDepartment();

        Employee employee = new Employee(
                name, email, salary, departmentId
        );

        employeeService.addEmployee(employee);
    }

    private static void viewAllEmployees() throws SQLException {

        List<Employee> employees =
                employeeService.getAllEmployees();

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    private static void searchEmployee() throws SQLException {

        int id = readInt("Enter employee ID: ");

        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            System.out.println("Employee not found.");
        } else {
            System.out.println(employee);
        }
    }

    private static void updateEmployee() throws SQLException {

        int id = readInt("Enter employee ID to update: ");

        Employee existing = employeeService.getEmployeeById(id);

        if (existing == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println("Current details: " + existing);
        System.out.println("Enter the new details:");

        String name = readText("Enter new name: ");
        String email = readText("Enter new email: ");
        double salary = readDouble("Enter new salary: ");
        int departmentId = chooseDepartment();

        Employee updated = new Employee(
                id, name, email, salary, departmentId
        );

        employeeService.updateEmployee(updated);
    }

    private static void deleteEmployee() throws SQLException {

        int id = readInt("Enter employee ID to delete: ");

        employeeService.deleteEmployee(id);
    }

    private static int chooseDepartment() throws SQLException {

        List<Department> departments =
                employeeService.getAllDepartments();

        System.out.println("\nAvailable departments:");

        for (Department department : departments) {
            System.out.println(department);
        }

        return readInt("Enter department ID: ");
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}

