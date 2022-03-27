import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.sql.*;

import org.apache.ibatis.jdbc.ScriptRunner;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class SimpleJDBCDemo {

    private static final String SQLITE_URL = "jdbc:sqlite:shield.db";
    private static final String MYSQL_URL = "jdbc:mysql://localhost/shield";
    private static final String USER = "root";
    private static final String PASSWORD = "my-secret-pw";
    public static void main(String[] args) {
        try (
                //Connection conn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
                Connection conn = DriverManager.getConnection(SQLITE_URL);
                Statement statement = conn.createStatement();
        ){
            // run sql scripts
            createDataBase(conn);
            populateDatabase(conn);

            // run select queries
            executeSimpleQuery(conn, statement);
            executeComplexQuery(conn, statement);

            // insert a new record
            insertNewHero(conn,statement);
            executeComplexQuery(conn, statement);

            // update a record
            updateRecord(conn,statement);
            executeComplexQuery(conn, statement);

            // Add multiple records in a transaction
            insertMultipleRecords(conn, statement);

            // use RowSet
            // checkHeroPowersWithRowSet();
            checkHeroPowersWithOutRowSet(conn, statement);

        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createDataBase(Connection conn) throws FileNotFoundException {
        // create database and populate values with an sql script
        ScriptRunner sr = new ScriptRunner(conn);

        // get script URL from resources folder
        URL create_db_script = SimpleJDBCDemo.class.getClassLoader().getResource("create_db.sql");

        //Creating a reader object
        Reader reader = new BufferedReader(new FileReader(create_db_script.getFile()));

        //Running the script
        sr.setEscapeProcessing(false); // specific for sqlite
        sr.runScript(reader);
    }

    private static void populateDatabase(Connection conn) throws FileNotFoundException {
        // create database and populate values with an sql script
        ScriptRunner sr = new ScriptRunner(conn);

        // get script URL from resources folder
        URL create_db_script = SimpleJDBCDemo.class.getClassLoader().getResource("populate_data.sql");

        //Creating a reader object
        Reader reader = new BufferedReader(new FileReader(create_db_script.getFile()));

        //Running the script
        sr.setEscapeProcessing(false); // specific for sqlite
        sr.runScript(reader);
    }

    private static void executeSimpleQuery(Connection conn, Statement statement) throws SQLException {
        String QUERY = "SELECT * FROM hero;";
        System.out.println("Executing " + QUERY);
        ResultSet rs = statement.executeQuery(QUERY);
        printResultSet(rs);
        rs.close();
    }

    private static void executeComplexQuery(Connection conn, Statement statement) throws SQLException {
        String QUERY = "SELECT hero_name, first_name, last_name, profession FROM hero "
                + "INNER JOIN day_job "
                + "ON hero.day_job_id = day_job.id "
                + "ORDER BY last_name;";
        System.out.println("Executing " + QUERY);
        ResultSet rs = statement.executeQuery(QUERY);
        printResultSet(rs);
        rs.close();
    }

    private static void insertNewHero(Connection conn, Statement statement) throws SQLException {
        String QUERY = "INSERT INTO hero (hero_name, first_name, last_name, day_job_id) VALUES ('Black Widow', 'Natasha', 'Romanov', 4);";
        System.out.println("Executing " + QUERY);
        int rowCount = statement.executeUpdate(QUERY);
        System.out.printf("%d lines added!\n", rowCount);
    }

    private static void updateRecord(Connection conn, Statement statement) throws SQLException {
        String QUERY = "UPDATE hero SET day_job_id = 5 WHERE first_name = 'Natasha';";
        System.out.println("Executing " + QUERY);
        int rowCount = statement.executeUpdate(QUERY);
        System.out.printf("%d lines updated!\n", rowCount);
    }

    private static void insertMultipleRecords(Connection conn, Statement statement) throws SQLException {
        // transaction, prepared statement and RowSet
        conn.setAutoCommit(false);

        // Get black widow ID
        String QUERY1 = "SELECT ID FROM hero WHERE first_name = 'Natasha';";
        ResultSet rs = statement.executeQuery(QUERY1);
        rs.next();
        int blackWidowId = rs.getInt(1);

        // Get Fire weapons ID
        String QUERY2 = "SELECT id FROM power WHERE power='Fire weapons';";
        rs = statement.executeQuery(QUERY2);
        rs.next();
        int fireWeaponsId = rs.getInt(1);

        // Get Calms hulk ID
        String QUERY3 = "SELECT id FROM power WHERE power='Calm Hulk';";
        rs = statement.executeQuery(QUERY3);
        rs.next();
        int calmHulkId = rs.getInt(1);

        rs.close();

        PreparedStatement pStatement = conn.prepareStatement(
                "INSERT INTO hero_power "
                    + "(hero_id, power_id) "
                    + "VALUES (?,?), (?,?)");
        pStatement.setInt(1, blackWidowId);
        pStatement.setInt(2, fireWeaponsId);
        pStatement.setInt(3, blackWidowId);
        pStatement.setInt(4, calmHulkId);
        int rowCount = pStatement.executeUpdate();
        System.out.printf("%d lines updated!\n\n", rowCount);

        conn.commit();

    }

    // DOESN't WORK WITH SQLITE
    private static void checkHeroPowersWithRowSet() {
        try (JdbcRowSet rowSet =
                     RowSetProvider.newFactory().createJdbcRowSet()) {

            // specify JdbcRowSet properties
            rowSet.setUrl(MYSQL_URL);
            rowSet.setUsername(USER);
            rowSet.setPassword(PASSWORD);
            rowSet.setCommand("SELECT hero_name, power, description FROM hero " +
                    "INNER JOIN hero_power ON hero.id = hero_power.hero_id " +
                    "INNER JOIN power ON hero_power.power_id = power.id;"
            );
            rowSet.setEscapeProcessing(false);
            rowSet.execute();
            printResultSet(rowSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void checkHeroPowersWithOutRowSet(Connection conn, Statement statement) throws SQLException {
        String QUERY = "SELECT hero_name, power, description FROM hero " +
                "INNER JOIN hero_power ON hero.id = hero_power.hero_id " +
                "INNER JOIN power ON hero_power.power_id = power.id;";
        System.out.println("Executing " + QUERY);
        ResultSet rs = statement.executeQuery(QUERY);
        printResultSet(rs);
        rs.close();
    }

    private static void printResultSet(ResultSet rs) throws SQLException {
        int numberOfColumns = rs.getMetaData().getColumnCount();
        int numCharacters = 0;
        for (int i = 1; i <= numberOfColumns; i++) {
            System.out.printf("%-12s\t", rs.getMetaData().getColumnName(i));
            numCharacters += rs.getMetaData().getColumnName(i).length();
        }
        System.out.println();

        String line = new String(new char[numCharacters + rs.getMetaData().getColumnCount() * 8]).replace("\0", "=");
        System.out.println(line);

        while(rs.next()) {
            for (int i = 1; i <= numberOfColumns; i++)
                System.out.printf("%-12s\t", rs.getObject(i));
            System.out.println();
        }
        System.out.println();
    }
}
