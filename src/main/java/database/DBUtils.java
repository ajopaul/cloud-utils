package database;

/**
 * Created by ajopaul on 21/8/17.
 */
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import util.SystemProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {
    public static final String SSH_KEY_IDENTITY = SystemProperties.getPropValue("key_identity");
    public static final String USERNAME = SystemProperties.getPropValue("ssh_username");
    public static final String HOST = SystemProperties.getPropValue("ssh_host");
    public static final String DB_HOST = SystemProperties.getPropValue("db_host");

    Connection connect;
    String dbUrl;
    ResultSet resultSet;
    Statement statement;
    Session session;

    private DBUtils() throws JSchException, SQLException, ClassNotFoundException {
        init();
    }

    private static DBUtils dbUtils;

    public static DBUtils getInstance() throws JSchException, SQLException, ClassNotFoundException {
        if(null == dbUtils)
            return new DBUtils();
        else
            return dbUtils;
    }

    private void init() throws SQLException, ClassNotFoundException, JSchException {
        dbUrl = SystemProperties.getPropValue("db_connection_url");
        JSch jsch = new JSch();
            jsch.addIdentity(SSH_KEY_IDENTITY);
            session = jsch.getSession(USERNAME, HOST, 22);
            session.setConfig( "StrictHostKeyChecking", "no" );
            session.connect();
            session.setPortForwardingL(3356, DB_HOST, 3306);
        createConnection();
    }

    private void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager
                .getConnection(dbUrl);
    }

    public ResultSetHandler fetchResults(String sql) throws SQLException, ClassNotFoundException {
        ResultSetHandler resultSetHandler = new ResultSetHandler();
        try {
            statement = connect.createStatement();
            resultSet = statement
                    .executeQuery(sql);
            ResultSetMetaData meta = resultSet.getMetaData();
            int columnCount = meta.getColumnCount();
            StringBuffer buf = new StringBuffer();

            for (int i = 0; i < columnCount; i++) {
                resultSetHandler.columnNames.put(meta.getColumnLabel(i+1), i);
            }
            System.out.println(buf);
            while (resultSet.next()) {
                List<Object> rowValues = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    int colType = meta.getColumnType(i);
                    switch (colType) {
                        case Types.INTEGER:
                        case Types.BIGINT:
                            rowValues.add(resultSet.getInt(i));
                            break;
                        case Types.VARCHAR:
                            rowValues.add(resultSet.getString(i));
                            break;
                        case Types.TIMESTAMP:
                        case Types.DATE:
                            rowValues.add(resultSet.getDate(i));
                            break;

                    }
                }
                resultSetHandler.rowsList.add(rowValues);
            }
        }finally {
            close();
        }
        return resultSetHandler;
    }

    public void close() throws SQLException {
        if(null != resultSet)
            resultSet.close();
        if(null != statement)
            statement.close();
        if(null != connect)
            connect.close();
        if(null != session)
            session.disconnect();
    }

    public static class ResultSetHandler{
        public Map<String, Integer> columnNames = new HashMap<>();
        public List<List<Object>> rowsList = new ArrayList<>();

        public ResultSetHandler(){
            columnNames.clear();
            rowsList.clear();
        }

    }
}
