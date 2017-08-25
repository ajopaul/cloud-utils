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

    public static Environment ENVIRONMENT = getENVIRONMENT();

    enum Environment {DEV, UAT, PROD};


    private DBUtils()  {
    }

    private static Environment getENVIRONMENT(){
        String env = SystemProperties.getPropValue("environment");
        Environment environment = Environment.DEV;
        switch (env){
            case "dev":
                environment = Environment.DEV;
                break;
            case "uat":
                environment = Environment.UAT;
                break;
            case "prod":
                environment = Environment.PROD;
                break;
        }
        return environment;
    }

    private static Session createTunnelSession() throws SQLException, ClassNotFoundException, JSchException {
        Session session = null;
        switch (ENVIRONMENT){
            case DEV:
                break;
            case UAT:
                session = tunnel();
                break;
            case PROD:
                session = tunnel();
                break;
        }
        return session;
    }

    private static Session tunnel() throws JSchException {
        Session session ;
        JSch jsch = new JSch();
        jsch.addIdentity(SSH_KEY_IDENTITY);
        session = jsch.getSession(USERNAME, HOST, 22);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        session.setPortForwardingL(3356, DB_HOST, 3306);
        System.out.println("Connected to "+ENVIRONMENT);
        return session;
    }

    private static Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String dbUrl = SystemProperties.getPropValue("db_connection_url");
        Connection connect = DriverManager
                .getConnection(dbUrl);
        return connect;
    }

    public static ResultSetHandler fetchResults(String sql) throws SQLException, ClassNotFoundException, JSchException {
          Connection connect ;

          ResultSet resultSet = null;
          Statement statement = null;
          Session session = createTunnelSession();

        connect = createConnection();

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
                        case Types.DECIMAL:
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
            close(resultSet, statement, connect, session);
        }
        return resultSetHandler;
    }

    private static void close(ResultSet resultSet, Statement statement, Connection connect, Session session) throws SQLException {
        if(null != resultSet)
            resultSet.close();
        if(null != statement)
            statement.close();
        if(null != connect)
            connect.close();
        if(null != session) {
            session.disconnect();
        }
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
