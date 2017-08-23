package s3;

import common.AbstractTest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by ajopaul on 23/8/17.
 */
public class AWSS3UtilTest extends AbstractTest {

    String keyName = null;

    @Before
    public void setup(){
        keyName = "2205/profile/fc4d00b7-45cd-4d15-a708-c127f64b7418";
    }

    @Test
    public void testUrlDownload() throws IOException {
        String url = AWSS3Util.getUrl(keyName);
        testUrlReachable(url);
    }
}
