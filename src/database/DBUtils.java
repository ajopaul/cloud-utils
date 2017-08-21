package database;

/**
 * Created by ajopaul on 21/8/17.
 */
import java.sql.*;

public class DBUtils {
    Connection connect;
    String dbUrl;
    ResultSet resultSet;
    Statement statement;

    private DBUtils(){

    }

    private static DBUtils dbUtils;

    public static DBUtils getInstance(){
        return new DBUtils();
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public void init() throws SQLException, ClassNotFoundException {
        dbUrl = "jdbc:mysql://localhost:3306/fleats?characterEncoding=UTF-8&useOldAliasMetadataBehavior=true&user=fleats&password=fleats";
        createConnection();
    }

    private void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        // Setup the connection with the DB
        connect = DriverManager
                .getConnection(dbUrl);
    }

    public ResultSet fetchResults(String sql) throws SQLException, ClassNotFoundException {
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery(sql);
            return resultSet;
    }

    public void close() throws SQLException {
        if(null != resultSet)
            resultSet.close();
        if(null != statement)
            statement.close();
        if(null != connect)
            connect.close();;
    }
}
