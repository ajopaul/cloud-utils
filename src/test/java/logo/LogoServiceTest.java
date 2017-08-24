package logo;
import com.jcraft.jsch.JSchException;
import dto.LogoModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by ajopaul on 21/8/17.
 */
public class LogoServiceTest {

    LogoService logoService;
    @Before
    public void setup() throws Exception {
        logoService = new LogoService();
    }

    @Test
    public void testLogosRows() throws Exception {
        List<LogoModel> logos = logoService.getLogoRows();
        Assert.assertTrue(logos.size() > 0);
    }

    @Test
    public void testJsonGen() throws Exception {
        String json = logoService.jsonGenerator();
        System.out.println(json);
        Assert.assertTrue(json.length() > 0);
    }

    @Test
    public void testCount() throws Exception {
        Map<String, Integer> counts = logoService.getLogoCounts();
        Integer logo_active_count = counts.get("logo_active_count");
        System.out.println(logo_active_count);
        Assert.assertTrue(logo_active_count >= 0);
        Integer profile_active_count = counts.get("profile_active_count");
        System.out.println(profile_active_count);
        Assert.assertTrue(profile_active_count >= 0);
    }
}
