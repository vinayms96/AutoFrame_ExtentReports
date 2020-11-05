package com.magento.mysql;

import com.magento.interfaces.Constants;
import com.magento.interfaces.UpdateTestData;
import com.magento.loggers.Loggers;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import static com.magento.mysql.CreateSampleDatabase.*;

public class WebsiteData implements Constants, UpdateTestData {
    private static PreparedStatement preparedStatement;
    private static String website_table;

    /**
     * Fetching the Column Headers
     */
    public static void getColumnHeaders() {
        int cell_count = ExcelUtils.getLastCellNumber();

        headers = new ArrayList<>(cell_count);

        // Setting the table headers in array
        for (int cell = 0; cell < cell_count; cell++) {
            headers.add(ExcelUtils.getCellHeaders().get(cell));
        }
    }

    /**
     * Execute the Insert Query
     */
    public static void executeCreate() {

        try {
            // Creating the Table values
            preparedStatement = table_connect.prepareStatement("INSERT INTO " + website_table + "(" +
                    headers.get(1) + "," + headers.get(2) + "," + headers.get(3) + "," +
                    headers.get(4) + "," + headers.get(5) + "," + headers.get(6) + ")" +
                    " VALUES " + "(?,?,?,?,?,?);");

            // Setting the Column values by fetching it from Excel
            preparedStatement.setString(1, ExcelUtils.getDataMap().get(headers.get(1)));
            preparedStatement.setString(2, ExcelUtils.getDataMap().get(headers.get(2)));
            preparedStatement.setString(3, ExcelUtils.getDataMap().get(headers.get(3)));
            preparedStatement.setString(4, ExcelUtils.getDataMap().get(headers.get(4)));
            preparedStatement.setString(5, ExcelUtils.getDataMap().get(headers.get(5)));
            preparedStatement.setInt(6, Integer.parseInt(ExcelUtils.getDataMap().get(headers.get(6))));

            // Executing the PreparedStatement to enter the table values
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }

    }

    /**
     * Create the Sample Table Structure
     */
    public static void createWebsiteData() {

        // Setting the Loggers
        Loggers.setLogger(CreateSampleDatabase.class.getName());

        // Establish connection with database
        table_connect = JdbcConnection.getConnection();
        website_table = Property.getProperty("database_table_2");

        try {
            // Getting the Column headers
            ExcelUtils.excelConfigure(WEBSITE_SAMPLE_DATA);
            getColumnHeaders();

            // Creating the Column Headers
            statement = table_connect.createStatement();
            String create_table = "CREATE TABLE " + website_table + " (" +
                    headers.get(0) + " INT UNIQUE AUTO_INCREMENT NOT NULL," +
                    headers.get(1) + " VARCHAR(256)," +
                    headers.get(2) + " VARCHAR(256)," +
                    headers.get(3) + " VARCHAR(256)," +
                    headers.get(4) + " VARCHAR(256)," +
                    headers.get(5) + " VARCHAR(256)," +
                    headers.get(6) + " INT," +
                    "PRIMARY KEY (" + headers.get(0) + "));";

            // Executing the Statement to create Column Headers
            statement.executeUpdate(create_table);
            Loggers.getLogger().info("Table with the name '" + website_table + "' is created");

            // Adding multiple row data to the Database Table
            for (int row = 1; row <= ExcelUtils.getLastRowNumber(); row++) {
                // Fetching each row data
                ExcelUtils.getRowData(row);

                executeCreate();
            }

            Loggers.getLogger().info("Website Sample data is added to the '" + website_table + "' table");

        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }

    }

    /**
     * Update the Table if table exists
     */
    public static void updateWebsiteData() {

        // Setting the Loggers
        Loggers.setLogger(CreateSampleDatabase.class.getName());

        // Establish connection with database
        table_connect = JdbcConnection.getConnection();
        website_table = Property.getProperty("database_table_2");

        // Setting the Test excel and Fetching the columns
        ExcelUtils.excelConfigure(UPDATE_WEBSITE_DATA + new WebsiteData().getUpdateFile() + ".xlsx");
        getColumnHeaders();

        try {
            // Fetching the Database Metadata and Finding the table in the database
            databaseMetaData = table_connect.getMetaData();
            resultSet = databaseMetaData.getTables(null, null, website_table, null);

            if (resultSet.next()) {
                for (int row = 1; row <= ExcelUtils.getLastRowNumber(); row++) {

                    // Fetching each row data
                    ExcelUtils.getRowData(row);

                    if (!ExcelUtils.getDataMap().get(headers.get(0)).isEmpty()) {
                        preparedStatement = table_connect.prepareStatement("UPDATE " + website_table + " SET " +
                                headers.get(1) + " = ?, " + headers.get(2) + " = ?, " + headers.get(3) + " = ?, " +
                                headers.get(4) + " = ?, " + headers.get(5) + " = ?, " + headers.get(6) + " = ? " +
                                " WHERE id = " + ExcelUtils.getDataMap().get(headers.get(0)) + ";");

                        // Setting the Column values
                        preparedStatement.setString(1, ExcelUtils.getDataMap().get(headers.get(1)));
                        preparedStatement.setString(2, ExcelUtils.getDataMap().get(headers.get(2)));
                        preparedStatement.setString(3, ExcelUtils.getDataMap().get(headers.get(3)));
                        preparedStatement.setString(4, ExcelUtils.getDataMap().get(headers.get(4)));
                        preparedStatement.setString(5, ExcelUtils.getDataMap().get(headers.get(5)));
                        preparedStatement.setInt(6, Integer.parseInt(ExcelUtils.getDataMap().get(headers.get(6))));

                        // Executing the PreparedStatement to enter the table values
                        preparedStatement.executeUpdate();
                    } else {
                        executeCreate();
                    }

                    Loggers.getLogger().info("The '" + website_table + "' data is updated");
                }
            } else {
                // Creating the entire table and data if table doesn't exist
                Loggers.getLogger().warn("Table " + website_table + " doesn't exist\n " +
                        "Creating New table with the name '" + website_table + "'...");
                createWebsiteData();
            }
        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }
    }

    /**
     * @return Update Filename
     */
    @Override
    public String getUpdateFile() {
        return Property.getProperty("website_update_file");
    }
}
