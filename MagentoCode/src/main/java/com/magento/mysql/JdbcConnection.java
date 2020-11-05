package com.magento.mysql;

import com.magento.loggers.Loggers;
import com.magento.utilities.Property;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnection {
    private static String database_url;
    private static String database_port;
    private static String database_name;
    private static Connection connect;

    /**
     * Establish the Database Connection
     */
    public static void establishConnection() {

        /*Setting the Loggers*/
        Loggers.setLogger(JdbcConnection.class.getName());

        /*Defining the Username and Password for the mysql from Property file*/
        final String USERNAME = Property.getProperty("database_username");
        final String PASSWORD = Property.getProperty("database_password");

        /*Getting the url and the port from Property file*/
        database_url = Property.getProperty("database_url");
        database_port = Property.getProperty("database_port");
        database_name = Property.getProperty("database_name");

        /*Defining the Driver and DB URL*/
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DATABASE_URL = "jdbc:mysql://" + database_url + ":" + database_port + "/" + database_name;
        final String NO_DATABASE_URL = "jdbc:mysql://" + database_url + ":" + database_port;

        /*Registering the Driver
         * Connecting to Driver by passing the URL, Username and Password*/
        try {

            Class.forName(JDBC_DRIVER);
            connect = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            Loggers.getLogger().info("Database Connection Established at Port:" + database_port);

        } catch (Exception e) {

            /*If above database is not present then Creating Sample Data*/
            Loggers.getLogger().info("Database not found!\n Creating Sample Database...");
            CreateSampleDatabase.createDatabase(JDBC_DRIVER, NO_DATABASE_URL, USERNAME, PASSWORD);

        }
    }

    /**
     * @return Connection
     */
    public static Connection getConnection() {
        return connect;
    }
}
