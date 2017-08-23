package logo;
import dto.LogoModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ajopaul on 21/8/17.
 */
public class LogoServiceTest {

    LogoService logoService;
    @Before
    public void setup() throws SQLException, ClassNotFoundException {
        logoService = new LogoService();
    }

    @Test
    public void testLogosRows() throws SQLException, ClassNotFoundException {
        List<LogoModel> logos = logoService.getLogoRows();
        Assert.assertTrue(logos.size() > 0);
    }

    @Test
    public void testJsonGen() throws SQLException, ClassNotFoundException {
        String json = logoService.jsonGenerator();
        System.out.println(json);
        Assert.assertTrue(json.length() > 0);
    }
}
