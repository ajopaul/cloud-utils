package web;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.AbstractTest;
import dto.LogoModel;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import util.SystemProperties;

/**
 * Created by ajopaul on 22/8/17.
 */
@Ignore
public class WSMainTest extends AbstractTest{

    private static String WEB_SERVER_URL;
    private WebTarget target;

    @Before
    public void setup(){
        WEB_SERVER_URL = SystemProperties.getPropValue("web_server_url");
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


    @Test
    public void testLogoRows() throws Exception {
        String json = target.path("rest").
                path("logos").
                path("all").
                request().
                accept(MediaType.APPLICATION_JSON).
                get(String.class);
        ObjectMapper mapper = new ObjectMapper();

        List<LogoModel> rows = mapper.readValue(json, new TypeReference<List<LogoModel>>(){});
        System.out.println("Testing logos");
        rows.stream().filter(l -> null != l.getLogoUrl()).forEach(l -> {
            try {
                testUrlReachable(l.getLogoUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        System.out.println("Testing profile Images");
        rows.stream().filter(l -> null != l.getProfileImageUrl()).forEach(l -> {
            try {
                testUrlReachable(l.getProfileImageUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    @Test
    public void testCounts() throws Exception{
        String json = target.path("rest").
                path("logos").
                path("counts").
                request().
                accept(MediaType.APPLICATION_JSON).
                get(String.class);
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Integer> counts = mapper.readValue(json, Map.class);
        counts.keySet().stream().forEach(c -> {
            int count = counts.get(c);
            Assert.assertTrue(count >=0);

        });
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri(WEB_SERVER_URL).build();
    }
}
