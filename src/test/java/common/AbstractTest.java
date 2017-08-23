package common;

import org.apache.http.HttpStatus;
import org.junit.Assert;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ajopaul on 23/8/17.
 */
public abstract class AbstractTest {
    public void testUrlReachable(String fileUrl) throws IOException {
        testUrlReachable(fileUrl, false);
    }

    public void testUrlReachable(String fileUrl, boolean checkFail) throws IOException{
        System.out.println("File Path: "+fileUrl);
        URL u = new URL ( fileUrl);
        HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection ();
        huc.setRequestMethod("GET");
        huc.connect () ;
        int code = huc.getResponseCode() ;
        System.out.println("Respose code: "+code);

        if(checkFail){
            Assert.assertNotEquals(code, HttpStatus.SC_OK);
        }else {
            Assert.assertEquals(HttpStatus.SC_OK, code);
        }
    }
}
