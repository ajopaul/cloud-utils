package database;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ajopaul on 21/8/17.
 */
public class DBUtilsTest {
    DBUtils dbUtils;

    @Before
    public void setup() throws SQLException, ClassNotFoundException {
        dbUtils = DBUtils.getInstance();
        dbUtils.init();
    }

    @Test
    public void testFetchResults() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = dbUtils.fetchResults("select * from white_label");
        while(resultSet.next()) {
            String user = resultSet.getString("user_id");
            Assert.assertNotNull(user);
        }
    }

    @After
    public void destroy() throws SQLException {
        dbUtils.close();
    }
}
