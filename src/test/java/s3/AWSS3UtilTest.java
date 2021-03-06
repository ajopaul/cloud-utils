package s3;

import common.AbstractTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import util.SystemProperties;

import java.io.IOException;

/**
 * Created by ajopaul on 23/8/17.
 */
//@Ignore
public class AWSS3UtilTest extends AbstractTest {

    String keyName = null;

    @Before
    public void setup(){
        keyName = SystemProperties.getPropValue("test_key_name");
    }

    @Test
    public void testUrlDownload() throws IOException {
        String url = AWSS3Util.getUrl(keyName);
        testUrlReachable(url);
    }
}
