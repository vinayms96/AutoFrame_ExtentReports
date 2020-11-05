package com.magento.mysql;

import com.magento.interfaces.Constants;
import com.magento.interfaces.UpdateTestData;
import com.magento.loggers.Loggers;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import static com.magento.mysql.CreateSampleDatabase.*;

public class CustomerData implements Constants, UpdateTestData {
    private static PreparedStatement preparedStatement;
    private static String customer_table;

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
    private static void executeCreate() {
        try {

            // Creating the Table values
            preparedStatement = table_connect.prepareStatement("INSERT INTO " + customer_table + "(" +
                    headers.get(1) + "," + headers.get(2) + "," + headers.get(3) + "," + headers.get(4) + "," + headers.get(5) + "," +
                    headers.get(6) + "," + headers.get(7) + "," + headers.get(8) + "," + headers.get(9) + "," + headers.get(10) + "," +
                    headers.get(11) + "," + headers.get(12) + ")" + " VALUES " + "(?,?,?,?,?,?,?,?,?,?,?,?);");

            // Setting the Column values by fetching it from Excel
            preparedStatement.setString(1, ExcelUtils.getDataMap().get(headers.get(1)));
            preparedStatement.setString(2, ExcelUtils.getDataMap().get(headers.get(2)));
            preparedStatement.setString(3, ExcelUtils.getDataMap().get(headers.get(3)));
            preparedStatement.setLong(4, Long.parseLong(ExcelUtils.getDataMap().get(headers.get(4))));
            preparedStatement.setString(5, ExcelUtils.getDataMap().get(headers.get(5)));
            preparedStatement.setString(6, ExcelUtils.getDataMap().get(headers.get(6)));
            preparedStatement.setInt(7, Integer.parseInt(ExcelUtils.getDataMap().get(headers.get(7))));
            preparedStatement.setString(8, ExcelUtils.getDataMap().get(headers.get(8)));
            preparedStatement.setString(9, ExcelUtils.getDataMap().get(headers.get(9)));
            preparedStatement.setInt(10, Integer.parseInt(ExcelUtils.getDataMap().get(headers.get(10))));
            preparedStatement.setString(11, ExcelUtils.getDataMap().get(headers.get(11)));
            preparedStatement.setInt(12, Integer.parseInt(ExcelUtils.getDataMap().get(headers.get(12))));

            // Executing the PreparedStatement to enter the table values
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }
    }

    /**
     * Create the Sample Table Structure
     */
    public static void createCustomerData() {

        // Setting the Loggers
        Loggers.setLogger(CreateSampleDatabase.class.getName());

        // Establish connection with database
        table_connect = JdbcConnection.getConnection();
        customer_table = Property.getProperty("database_table_1");

        try {
            // Getting the Column headers
            ExcelUtils.excelConfigure(CUSTOMER_SAMPLE_DATA);
            getColumnHeaders();

            // Creating the Column Headers
            statement = table_connect.createStatement();
            String create_table = "CREATE TABLE " + customer_table + " (" +
                    headers.get(0) + " INT UNIQUE AUTO_INCREMENT NOT NULL," +
                    headers.get(1) + " VARCHAR(256)," +
                    headers.get(2) + " VARCHAR(256)," +
                    headers.get(3) + " VARCHAR(256)," +
                    headers.get(4) + " VARCHAR(256)," +
                    headers.get(5) + " VARCHAR(256)," +
                    headers.get(6) + " VARCHAR(256)," +
                    headers.get(7) + " INT," +
                    headers.get(8) + " VARCHAR(256)," +
                    headers.get(9) + " VARCHAR(256)," +
                    headers.get(10) + " INT," +
                    headers.get(11) + " VARCHAR(256)," +
                    headers.get(12) + " INT," +
                    "PRIMARY KEY (" + headers.get(0) + "));";

            // Executing the Statement to create Column Headers
            statement.executeUpdate(create_table);
            Loggers.getLogger().info("Table with the name '" + customer_table + "' is created");

            // Adding multiple row data to the Database Table
            for (int row = 1; row <= ExcelUtils.getLastRowNumber(); row++) {
                // Fetching each row data
                ExcelUtils.getRowData(row);

                executeCreate();
            }

            Loggers.getLogger().info("Customer Sample data is added to the '" + customer_table + "' table");

        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }

    }


    /**
     * Update the Table if table exists
     */
    public static void updateCustomerData() {

        // Setting the Loggers
        Loggers.setLogger(CreateSampleDatabase.class.getName());

        // Establish connection with database
        table_connect = JdbcConnection.getConnection();
        customer_table = Property.getProperty("database_table_1");

        // Setting the Test excel and Fetching the columns
        ExcelUtils.excelConfigure(UPDATE_CUSTOMER_DATA + new CustomerData().getUpdateFile() + ".xlsx");
        getColumnHeaders();

        try {
            // Fetching the Database Metadata and Finding the table in the database
            databaseMetaData = table_connect.getMetaData();
            resultSet = databaseMetaData.getTables(null, null, customer_table, null);

            if (resultSet.next()) {
                for (int row = 1; row <= ExcelUtils.getLastRowNumber(); row++) {

                    // Fetching each row data
                    ExcelUtils.getRowData(row);

                    if (!ExcelUtils.getDataMap().get(headers.get(0)).isEmpty()) {
                        preparedStatement = table_connect.prepareStatement("UPDATE " + customer_table + " SET " +
                                headers.get(1) + " = ?, " + headers.get(2) + " = ?, " + headers.get(3) + " = ?, " + headers.get(4) + " = ?, " +
                                headers.get(5) + " = ?, " + headers.get(6) + " = ?, " + headers.get(7) + " = ?, " + headers.get(8) + " = ?, " +
                                headers.get(9) + " = ?, " + headers.get(10) + " = ?, " + headers.get(11) + " = ?, " + headers.get(12) + " = ?" +
                                " WHERE id = " + ExcelUtils.getDataMap().get(headers.get(0)) + ";");

                        // Setting the Column values
                        preparedStatement.setString(1, ExcelUtils.getDataMap().get(headers.get(1)));
                        preparedStatement.setString(2, ExcelUtils.getDataMap().get(headers.get(2)));
                        preparedStatement.setString(3, ExcelUtils.getDataMap().get(headers.get(3)));
                        preparedStatement.setLong(4, Long.parseLong(ExcelUtils.getDataMap().get(headers.get(4))));
                        preparedStatement.setString(5, ExcelUtils.getDataMap().get(headers.get(5)));
                        preparedStatement.setString(6, ExcelUtils.getDataMap().get(headers.get(6)));
                        preparedStatement.setInt(7, Integer.parseInt(ExcelUtils.getDataMap().get(headers.get(7))));
                        preparedStatement.setString(8, ExcelUtils.getDataMap().get(headers.get(8)));
                        preparedStatement.setString(9, ExcelUtils.getDataMap().get(headers.get(9)));
                        preparedStatement.setInt(10, Integer.parseInt(ExcelUtils.getDataMap().get(headers.get(10))));
                        preparedStatement.setString(11, ExcelUtils.getDataMap().get(headers.get(11)));
                        preparedStatement.setInt(12, Integer.parseInt(ExcelUtils.getDataMap().get(headers.get(12))));

                        // Executing the PreparedStatement to enter the table values
                        preparedStatement.executeUpdate();
                    } else {
                        executeCreate();
                    }

                    Loggers.getLogger().info("The '" + customer_table + "' data is updated");
                }
            } else {
                // Creating the entire table and data if table doesn't exist
                Loggers.getLogger().warn("Table " + customer_table + " doesn't exist\n " +
                        "Creating New table with the name '" + customer_table + "'...");
                createCustomerData();
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
        return Property.getProperty("customer_update_file");
    }
}