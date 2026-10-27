package com.example.employee.dao;

import com.example.employee.config.DBConnection;
import com.example.employee.model.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    public List<Department> getAllDepartments()
            throws SQLException {

        String sql = """
                SELECT department_id, department_name
                FROM departments
                ORDER BY department_id
                """;

        List<Department> departments = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                departments.add(
                        new Department(
                                rs.getInt("department_id"),
                                rs.getString("department_name")
                        )
                );
            }
        }

        return departments;
    }

    public boolean departmentExists(int departmentId)
            throws SQLException {

        String sql = """
                SELECT 1
                FROM departments
                WHERE department_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, departmentId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
