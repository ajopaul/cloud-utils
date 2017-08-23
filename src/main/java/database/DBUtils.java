package database;

/**
 * Created by ajopaul on 21/8/17.
 */
import util.SystemProperties;

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

    public void init() throws SQLException, ClassNotFoundException {
        dbUrl = SystemProperties.getPropValue("db_connection_url");
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
