package web;

import dto.LogoModel;
import json.JsonGenerator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ajopaul on 22/8/17.
 */
 @Path("/logos")
public class WSMain {
    @GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		String output =  msg;
		return Response.status(200).entity(output).build();
	}

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getBrokerLogos() throws SQLException, ClassNotFoundException {
        JsonGenerator jsonGenerator = new JsonGenerator();
        List<LogoModel> rows = jsonGenerator.getLogoRows();
        return Response.status(200).entity(rows).build();
    }
}
