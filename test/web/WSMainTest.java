package web;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ajopaul on 22/8/17.
 */
public class WSMainTest {

    private static String WEB_SERVER_URL;
    private WebTarget target;

    @Before
    public void setup(){
        WEB_SERVER_URL = "http://localhost:8088/cloud";
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        target = client.target(getBaseURI());
        org.junit.Assume.assumeTrue(checkWebServerIsUp());
    }

    private boolean checkWebServerIsUp(){
        boolean isServerUp = false;
        try{
            Response response =  target.request().get(Response.class);
            System.out.println("Check Web Server status "+response);
            if(null != response && response.getStatus() == 200)
                isServerUp = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return isServerUp;
    }

    @Test
    public void testHello(){
        String response = target.path("rest").
                            path("logos").
                            path("ajopaul").
                            request().
                            accept(MediaType.TEXT_PLAIN).
                            get(Response.class)
                            .toString();
        String plainAnswer = target.path("rest").
                              path("logos").
                              path("ajopaul").
                              request().
                              accept(MediaType.TEXT_PLAIN).
                              get(String.class);
        Assert.assertEquals("ajopaul", plainAnswer);
    }

    @Test
    public void testJson() {
        String response = target.path("rest").
                            path("logos").
                            path("all").
                            request().
                            accept(MediaType.APPLICATION_JSON).
                            get(String.class);
        System.out.println("Response: \n"+response);
        Assert.assertNotNull(response);
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri(WEB_SERVER_URL).build();
    }
}
