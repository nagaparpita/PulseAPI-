package com.pulseapi.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pulseapi.connection.DBConnection;
import com.pulseapi.model.Alert;

public class AlertRepo {

    // Insert Query
    public void addAlert(Alert alert) {

        // Get connection to communicate with database
        Connection con = DBConnection.getConnection();

        try {

            // Insert alert details into database
            String sql = """
                    INSERT INTO alerts
                    (api_id, alert_type, message, alert_status)
                    VALUES (?, ?, ?, ?)
                    """;

            // Prepare the query
            PreparedStatement ps = con.prepareStatement(sql);

            // Pass values to the ? placeholders
            ps.setInt(1, alert.getApiId());
            ps.setString(2, alert.getAlertType());
            ps.setString(3, alert.getMessage());
            ps.setString(4, alert.getAlertStatus());

            // INSERT changes data, so use executeUpdate()
            int rowAffected = ps.executeUpdate();

            if (rowAffected == 1) {
                System.out.println("Insertion Done.");
            }

            // Close statement and connection
            ps.close();
            con.close();

        } catch (SQLException e) {

            System.out.println("Insertion failed.");
            e.printStackTrace();
        }
    }

    // Select Query
    public void getAllAlerts() {

        // Get connection to communicate with database
        Connection con = DBConnection.getConnection();

        try {

            // Get all alerts from database
            String sql = """
                    SELECT alert_id,
                           api_id,
                           alert_type,
                           message,
                           alert_status
                    FROM alerts
                    """;

            // Create Statement to execute SELECT query
            Statement statement = con.createStatement();

            // SELECT returns data, so use executeQuery()
            ResultSet rs = statement.executeQuery(sql);

            // Read each row from ResultSet
            while (rs.next()) {

                // Convert database row into Alert object
                Alert alert = mapRow(rs);

                // Display alert details
                System.out.println(alert);
            }

            // Close ResultSet, statement and connection
            rs.close();
            statement.close();
            con.close();

        } catch (SQLException e) {

            System.out.println("Selection failed.");
            e.printStackTrace();
        }
    }

    // Convert database row into Alert object
    public Alert mapRow(ResultSet rs) throws SQLException {

        // Create an empty Alert object
        Alert alert = new Alert();

        // Get values from database and set them into object
        alert.setAlertId(rs.getInt("alert_id"));
        alert.setApiId(rs.getInt("api_id"));
        alert.setAlertType(rs.getString("alert_type"));
        alert.setMessage(rs.getString("message"));
        alert.setAlertStatus(rs.getString("alert_status"));

        // Return the Alert object
        return alert;
    }
}