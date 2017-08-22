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

    private WebTarget target;

    @Before
    public void setup(){
        ClientConfig config = new ClientConfig();

        Client client = ClientBuilder.newClient(config);

        target = client.target(getBaseURI());
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
        System.out.println(response);
        String plainAnswer = target.path("rest").path("logos").path("ajopaul").request().accept(MediaType.TEXT_PLAIN).get(String.class);
        Assert.assertEquals("ajopaul", plainAnswer);
        //String plainAnswer =
          //      target.path("rest").path("hello").request().accept(MediaType.TEXT_PLAIN).get(String.class);
//        String xmlAnswer =
//                target.path("rest").path("hello").request().accept(MediaType.TEXT_XML).get(String.class);
//        String htmlAnswer=
//                target.path("rest").path("hello").request().accept(MediaType.TEXT_HTML).get(String.class);
//
//        System.out.println(response);
//        System.out.println(plainAnswer);
//        System.out.println(xmlAnswer);
//        System.out.println(htmlAnswer);
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/cloud").build();
    }
}
