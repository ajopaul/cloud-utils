package json;
import dto.LogoModel;
import json.JsonGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ajopaul on 21/8/17.
 */
public class JsonGeneratorTest {

    JsonGenerator jsonGenerator;
    @Before
    public void setup() throws SQLException, ClassNotFoundException {
        jsonGenerator  = new JsonGenerator();
        jsonGenerator.init();
    }

    @Test
    public void testLogosRows() throws SQLException, ClassNotFoundException {
        List<LogoModel> logos = jsonGenerator.getLogoRows();
        Assert.assertTrue(logos.size() > 0);
    }

    @Test
    public void testJsonGen() throws SQLException, ClassNotFoundException {
        String json = jsonGenerator.jsonGenerator();
        System.out.println(json);
        Assert.assertTrue(json.length() > 0);
    }
}
