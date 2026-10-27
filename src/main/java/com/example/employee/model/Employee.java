package com.example.employee.model;

public class Employee {
    private int employeeId;
    private String name;
    private String email;
    private double salary;
    private int departmentId;

    public Employee() {
    }

    public Employee(int employeeId, String name, String email, double salary, int departmentId) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public Employee(String name, String email, double salary, int departmentId) {
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", departmentId=" + departmentId +
                '}';
    }
}
