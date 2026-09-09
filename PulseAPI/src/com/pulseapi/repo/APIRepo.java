package com.pulseapi.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pulseapi.connection.DBConnection;
import com.pulseapi.model.API;

public class APIRepo {

    // Insert Query
    public void addAPI(API api) {

        // Get database connection
        Connection con = DBConnection.getConnection();

        try {

            // Query to insert new API details into the database
            // ? is used because values will be passed separately
            String sql = """
                    INSERT INTO apis
                    (user_id, api_name, api_url, http_method, monitoring_interval, status)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;

            // Prepare the query before executing it
            PreparedStatement ps = con.prepareStatement(sql);

            // Pass API values to the ? placeholders
            ps.setInt(1, api.getUserId());
            ps.setString(2, api.getApiName());
            ps.setString(3, api.getApiUrl());
            ps.setString(4, api.getHttpMethod());
            ps.setInt(5, api.getMonitoringInterval());
            ps.setBoolean(6, api.isStatus());

            // INSERT changes data in database, so use executeUpdate()
            int rowAffected = ps.executeUpdate();

            // Check if one row was inserted
            if (rowAffected == 1) {
                System.out.println("Insertion Done.");
            }

            // Close statement and connection
            ps.close();
            con.close();

        } catch (SQLException e) {

            // Handle database error
            System.out.println("Insertion failed.");
            e.printStackTrace();
        }
    }


    // Update Query
    public void updateAPI(API api) {

        // Get database connection
        Connection con = DBConnection.getConnection();

        try {

            // Update API details using api_id
            // WHERE is used so that only the required API is updated
            String sql = """
                    UPDATE apis
                    SET user_id = ?,
                        api_name = ?,
                        api_url = ?,
                        http_method = ?,
                        monitoring_interval = ?,
                        status = ?
                    WHERE api_id = ?
                    """;

            // Prepare the update query
            PreparedStatement ps = con.prepareStatement(sql);

            // Pass new API values to the query
            ps.setInt(1, api.getUserId());
            ps.setString(2, api.getApiName());
            ps.setString(3, api.getApiUrl());
            ps.setString(4, api.getHttpMethod());
            ps.setInt(5, api.getMonitoringInterval());
            ps.setBoolean(6, api.isStatus());

            // Pass api_id for WHERE condition
            ps.setInt(7, api.getApiId());

            // UPDATE changes data, so use executeUpdate()
            int result = ps.executeUpdate();

            // Check if API was updated
            if (result == 1) {
                System.out.println("Updation Done.");
            } else {
                System.out.println("API not found.");
            }

            // Close statement and connection
            ps.close();
            con.close();

        } catch (SQLException e) {

            System.out.println("Updation failed.");
            e.printStackTrace();
        }
    }


    // Delete Query
    public void deleteAPI(int apiId) {

        // Get database connection
        Connection con = DBConnection.getConnection();

        try {

            // Delete API using api_id
            String sql = """
                    DELETE FROM apis
                    WHERE api_id = ?
                    """;

            // Prepare the delete query
            PreparedStatement ps = con.prepareStatement(sql);

            // Pass api_id to the ? placeholder
            ps.setInt(1, apiId);

            // DELETE changes data, so use executeUpdate()
            int result = ps.executeUpdate();

            // Check if API was deleted
            if (result == 1) {
                System.out.println("Deletion Done.");
            } else {
                System.out.println("API not found.");
            }

            // Close statement and connection
            ps.close();
            con.close();

        } catch (SQLException e) {

            System.out.println("Deletion failed.");
            e.printStackTrace();
        }
    }


    // Select Query
    public void getAllAPIs() {

        // Get database connection
        Connection con = DBConnection.getConnection();

        try {

            // Query to get all API details
            String sql = """
                    SELECT api_id,
                           user_id,
                           api_name,
                           api_url,
                           http_method,
                           monitoring_interval,
                           status
                    FROM apis
                    """;

            // Create Statement to execute the SELECT query
            Statement statement = con.createStatement();

            // SELECT returns data, so use executeQuery()
            ResultSet rs = statement.executeQuery(sql);

            // Read each row from the ResultSet
            while (rs.next()) {

                // Convert database row into API object
                API api = mapRow(rs);

                // Display API details
                System.out.println(api);
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


    // Mapping Row
    public API mapRow(ResultSet rs) throws SQLException {

        // Create an empty API object
        API api = new API();

        // Get values from database and set them into API object
        api.setApiId(rs.getInt("api_id"));
        api.setUserId(rs.getInt("user_id"));
        api.setApiName(rs.getString("api_name"));
        api.setApiUrl(rs.getString("api_url"));
        api.setHttpMethod(rs.getString("http_method"));
        api.setMonitoringInterval(
                rs.getInt("monitoring_interval")
        );
        api.setStatus(
                rs.getBoolean("status")
        );

        // Return the API object
        return api;
    }
}