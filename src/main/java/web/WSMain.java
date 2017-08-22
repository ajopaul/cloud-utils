package web;

import json.JsonGenerator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by ajopaul on 22/8/17.
 */
 @Path("/logos")
public class WSMain {
    @GET
	@Path("/{param}")
    //@Produces(MediaType.TEXT_PLAIN)
	public Response getMsg(@PathParam("param") String msg) {

		String output =  "Hello" + msg;
       // return output;
		return Response.status(200).entity(output).build();

	}

    @GET
    @Path("/all")
	public Response getBrokerLogos() throws SQLException, ClassNotFoundException {
        JsonGenerator jsonGenerator = new JsonGenerator();
        String json = jsonGenerator.jsonGenerator();
        return null;
    }
}
