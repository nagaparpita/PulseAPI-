package com.pulseapi.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pulseapi.connection.DBConnection;
import com.pulseapi.model.MonitoringResult;

public class MonitoringResultRepo {

    // Insert Query
    public void addMonitoringResult(MonitoringResult result) {

        // Get connection to communicate with database
        Connection con = DBConnection.getConnection();

        try {

            // Insert monitoring result into database
            // ? is used because values are passed separately
            String sql = """
                    INSERT INTO monitoring_results
                    (api_id, status_code, response_time, status, error_message)
                    VALUES (?, ?, ?, ?, ?)
                    """;

            // Prepare the query
            PreparedStatement ps = con.prepareStatement(sql);

            // Pass values to the ? placeholders
            ps.setInt(1, result.getApiId());
            ps.setInt(2, result.getStatusCode());
            ps.setLong(3, result.getResponseTime());
            ps.setString(4, result.getStatus());
            ps.setString(5, result.getErrorMessage());

            // INSERT changes data, so use executeUpdate()
            int rowAffected = ps.executeUpdate();

            // Check whether the record was inserted
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
    public void getAllMonitoringResults() {

        // Get connection to communicate with database
        Connection con = DBConnection.getConnection();

        try {

            // Get all monitoring results from database
            String sql = """
                    SELECT result_id,
                           api_id,
                           status_code,
                           response_time,
                           status,
                           error_message
                    FROM monitoring_results
                    """;

            // Create Statement to execute SELECT query
            Statement statement = con.createStatement();

            // SELECT returns data, so use executeQuery()
            ResultSet rs = statement.executeQuery(sql);

            // Read each row from ResultSet
            while (rs.next()) {

                // Convert database row into MonitoringResult object
                MonitoringResult result = mapRow(rs);

                // Display monitoring result
                System.out.println(result);
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

    // Convert one database row into MonitoringResult object
    public MonitoringResult mapRow(ResultSet rs) throws SQLException {

        // Create an empty MonitoringResult object
        MonitoringResult result = new MonitoringResult();

        // Get values from database and set them into the object
        result.setResultId(rs.getInt("result_id"));
        result.setApiId(rs.getInt("api_id"));
        result.setStatusCode(rs.getInt("status_code"));
        result.setResponseTime(rs.getLong("response_time"));
        result.setStatus(rs.getString("status"));
        result.setErrorMessage(rs.getString("error_message"));

        // Return the object
        return result;
    }
}