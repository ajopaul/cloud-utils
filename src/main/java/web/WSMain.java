package web;

import dto.LogoModel;
import logo.LogoService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
	public Response getBrokerLogos() throws Exception {
        LogoService logoService = new LogoService();
        List<LogoModel> rows = logoService.getLogoRows();
        return Response.status(200).entity(rows).build();
    }

    @GET
    @Path("/counts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogoCounts() throws Exception {
        LogoService logoService = new LogoService();
        Map<String, Integer> rows = logoService.getLogoCounts();
        return Response.status(200).entity(rows).build();
    }
}
