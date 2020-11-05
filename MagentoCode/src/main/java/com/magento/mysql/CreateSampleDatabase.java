package com.magento.mysql;

import com.magento.loggers.Loggers;
import com.magento.utilities.Property;

import java.sql.*;
import java.util.ArrayList;

public class CreateSampleDatabase {
    private static Connection no_connect;
    private static String database_name;
    protected static Statement statement;
    protected static DatabaseMetaData databaseMetaData;
    protected static ResultSet resultSet;
    protected static ArrayList<String> headers;
    protected static Connection table_connect;

    /**
     * Creating Sample Database
     */
    public static void createDatabase(String JDBC_DRIVER, String NO_DATABASE_URL, String USERNAME, String PASSWORD) {

        // Setting the Loggers
        Loggers.setLogger(CreateSampleDatabase.class.getName());

        // Getting the database name
        database_name = Property.getProperty("database_name");

        // Creating Sample database
        try {

            // Setting new connection without Database name
            Class.forName(JDBC_DRIVER);
            no_connect = DriverManager.getConnection(NO_DATABASE_URL, USERNAME, PASSWORD);

            // Creating Database
            statement = no_connect.createStatement();
            String database_name_query = "CREATE DATABASE " + database_name + ";";
            statement.executeUpdate(database_name_query);
            Loggers.getLogger().info("Sample Database Created");

            // Re-Establishing connection after creating new database
            JdbcConnection.establishConnection();
        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }

        // Calling the Create Table method to add Sample Table and data
        CustomerData.createCustomerData();
        WebsiteData.createWebsiteData();
    }

}