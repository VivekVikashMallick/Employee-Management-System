package com.example.employee.service;

import com.example.employee.dao.DepartmentDAO;
import com.example.employee.dao.EmployeeDAO;
import com.example.employee.model.Department;
import com.example.employee.model.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {

    private final EmployeeDAO employeeDAO;
    private final DepartmentDAO departmentDAO;

    public EmployeeService(EmployeeDAO employeeDAO, DepartmentDAO departmentDAO) {
        this.employeeDAO = employeeDAO;
        this.departmentDAO = departmentDAO;
    }
    public void addEmployee(Employee employee) throws SQLException {

        validateEmployee(employee);

        if (!departmentDAO.departmentExists(
                employee.getDepartmentId())) {
            throw new IllegalArgumentException(
                    "Department ID does not exist."
            );
        }

        boolean added = employeeDAO.addEmployee(employee);

        if (added) {
            System.out.println("Employee added successfully.");
        }
    }

    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDAO.getAllEmployees();
    }

    public Employee getEmployeeById(int employeeId)
            throws SQLException {

        if (employeeId <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be positive."
            );
        }

        return employeeDAO.getEmployeeById(employeeId);
    }

    public void updateEmployee(Employee employee)
            throws SQLException {

        if (employee.getEmployeeId() <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be positive."
            );
        }

        validateEmployee(employee);

        if (!departmentDAO.departmentExists(
                employee.getDepartmentId())) {
            throw new IllegalArgumentException(
                    "Department ID does not exist."
            );
        }

        Employee existing = employeeDAO.getEmployeeById(
                employee.getEmployeeId()
        );

        if (existing == null) {
            System.out.println("Employee not found.");
            return;
        }

        boolean updated = employeeDAO.updateEmployee(employee);

        if (updated) {
            System.out.println("Employee updated successfully.");
        } else {
            System.out.println(
                    "No changes were made. The employee may already "
                            + "have these details."
            );
        }
    }

    public void deleteEmployee(int employeeId) throws SQLException {

        if (employeeId <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be positive."
            );
        }

        boolean deleted = employeeDAO.deleteEmployee(employeeId);

        if (deleted) {
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    public void displayEmployeesDepartmentWise()
            throws SQLException {
        employeeDAO.displayEmployeesDepartmentWise();
    }

    public List<Department> getAllDepartments()
            throws SQLException {
        return departmentDAO.getAllDepartments();
    }

    private void validateEmployee(Employee employee) {

        if (employee.getName() == null
                || employee.getName().isBlank()) {
            throw new IllegalArgumentException(
                    "Employee name cannot be empty."
            );
        }

        if (employee.getEmail() == null
                || !employee.getEmail().matches(
                "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException(
                    "Please enter a valid email address."
            );
        }

        if (!Double.isFinite(employee.getSalary())
                || employee.getSalary() <= 0) {
            throw new IllegalArgumentException(
                    "Salary must be a positive finite number."
            );
        }

        if (employee.getDepartmentId() <= 0) {
            throw new IllegalArgumentException(
                    "Department ID must be positive."
            );
        }
    }
}
