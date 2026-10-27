package com.example.employee.dao;

import com.example.employee.config.DBConnection;
import com.example.employee.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public boolean addEmployee(Employee employee) throws SQLException {

        String sql = """
                INSERT INTO employees
                    (name, email, salary, department_id)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setDouble(3, employee.getSalary());
            ps.setInt(4, employee.getDepartmentId());

            return ps.executeUpdate() == 1;
        }
    }

    // 2. SELECT ALL
    public List<Employee> getAllEmployees() throws SQLException {

        String sql = """
                SELECT employee_id, name, email, salary, department_id
                FROM employees
                ORDER BY employee_id
                """;

        List<Employee> employees = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Employee employee = new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDouble("salary"),
                        rs.getInt("department_id")
                );

                employees.add(employee);
            }
        }

        return employees;
    }

    // 3. SELECT BY ID
    public Employee getEmployeeById(int employeeId)
            throws SQLException {

        String sql = """
                SELECT employee_id, name, email, salary, department_id
                FROM employees
                WHERE employee_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Employee(
                            rs.getInt("employee_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getDouble("salary"),
                            rs.getInt("department_id")
                    );
                }
            }
        }

        return null;
    }

    // 4. UPDATE
    public boolean updateEmployee(Employee employee)
            throws SQLException {

        String sql = """
                UPDATE employees
                SET name = ?, email = ?, salary = ?, department_id = ?
                WHERE employee_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setDouble(3, employee.getSalary());
            ps.setInt(4, employee.getDepartmentId());
            ps.setInt(5, employee.getEmployeeId());

            return ps.executeUpdate() == 1;
        }
    }

    // 5. DELETE
    public boolean deleteEmployee(int employeeId)
            throws SQLException {

        String sql = """
                DELETE FROM employees
                WHERE employee_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            return ps.executeUpdate() == 1;
        }
    }

    // 6. JOIN REPORT
    public void displayEmployeesDepartmentWise()
            throws SQLException {

        String sql = """
                SELECT e.employee_id, e.name, e.email, e.salary,
                       d.department_name
                FROM employees e
                INNER JOIN departments d
                    ON e.department_id = d.department_id
                ORDER BY d.department_name, e.name
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println(
                    "\nID | Name | Email | Salary | Department"
            );

            while (rs.next()) {
                System.out.printf(
                        "%d | %s | %s | %.2f | %s%n",
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDouble("salary"),
                        rs.getString("department_name")
                );
            }
        }
    }
}
