package database;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ajopaul on 21/8/17.
 */
public class DBUtilsTest {

    @Test
    public void testFetchResults() throws Exception {
        DBUtils.ResultSetHandler resultSet = DBUtils.getInstance().fetchResults("select * from white_label");
        int user_idCol = resultSet.columnNames.get("user_id");
        String user = String.valueOf(resultSet.rowsList.get(0).get(user_idCol));
        System.out.println("User_id " + user);
        Assert.assertNotNull(user);
    }
}

