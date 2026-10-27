package com.example.employee.util;

import com.example.employee.config.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseInitializer {
      private DatabaseInitializer(){

      }

    public static void initialize() throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS employees (
                    employee_id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(100) NOT NULL,
                    email VARCHAR(150) NOT NULL UNIQUE,
                    salary DECIMAL(12, 2) NOT NULL,
                    department_id INT NOT NULL,
                    CONSTRAINT fk_employee_department
                        FOREIGN KEY (department_id)
                        REFERENCES departments(department_id)
                        ON UPDATE CASCADE
                        ON DELETE RESTRICT
                )
                """;

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(sql);
        }
    }
}
