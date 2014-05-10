package application;

import java.io.IOException;
import java.sql.*;

/**
 * Created by Polina on 23.04.2014.
 */
public class Application {

    public static void main( String[] args ) throws ClassNotFoundException, SQLException, IOException {
        Connection connection = null;
        String url = "jdbc:h2:mem:";
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection(url);
        createTables(connection);

        String insertStringSt = "INSERT INTO FirstTable (ID, NAME, CREATION_DATE) VALUES(1, 'vasya', NOW())";
        String insertStringPrSt = "INSERT INTO FirstTable (ID, NAME, CREATION_DATE) VALUES(?, ?, NOW())";

        long timeStatement = execStatement(connection, insertStringSt);
        cleanTable(connection);

        long timePrepearedStatement = execPrepearedStatement(connection, insertStringPrSt);
        cleanTable(connection);

        long timePrepearedBatchStatement = execPrepearedBatchStatement(connection, insertStringPrSt);
        cleanTable(connection);

        System.out.println("timeStatement: " + timeStatement + "\n" +
                "timePrepearedStatement: " + timePrepearedStatement + "\n" +
                "timePrepearedBatchStatement: " + timePrepearedBatchStatement);

        connection.close();
    }

    private static long execStatement(Connection connection, String insertString) throws SQLException {
        connection.setAutoCommit(false);
        long time1 = System.currentTimeMillis();
        int rowsCount = 1000;

        for (int i = 0; i < rowsCount; i++) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertString);
        }
        connection.commit();

        long time2 = System.currentTimeMillis();
        return time2 - time1;
    }

    private static long execPrepearedStatement(Connection connection, String insertString) throws SQLException {
        connection.setAutoCommit(false);
        long time1 = System.currentTimeMillis();

        PreparedStatement statement = connection.prepareStatement(insertString);
        int rowsCount = 1000;
        for( int i = 0; i < rowsCount; i++ ) {
            statement.setInt(1, i);
            statement.setString(2, "user" + i);
            statement.executeUpdate();
        }

        connection.commit();

        long time2 = System.currentTimeMillis();

        return time2 - time1;
    }

    private static long execPrepearedBatchStatement(Connection connection, String insertString) throws SQLException {
        connection.setAutoCommit(false);
        long time1 = System.currentTimeMillis();

        PreparedStatement statement = connection.prepareStatement(insertString);
        int rowsCount = 1000;
        for( int i = 0; i < rowsCount; i++ ) {
            statement.setInt(1, i);
            statement.setString(2, "user" + i);
            statement.addBatch();
        }
        statement.executeBatch();

        connection.commit();

        long time2 = System.currentTimeMillis();

        return time2 - time1;

    }

    private static void createTables(Connection connection) throws SQLException, IOException {

        String query = "create table firsttable (id number , name varchar(50), CREATION_DATE timestamp)";
        connection.createStatement().executeUpdate(query);

    }

    private static void cleanTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("TRUNCATE TABLE FirstTable");
    }
}
