/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.datev.services.restful.service;

import de.datev.services.models.UserControler;
import de.datev.services.models.UserModel;
import java.sql.ResultSet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author TG00023
 */
@Path("user/{UserID}")
public class UserBoundary
{
	@GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getList(@PathParam("UserID") int id)
    {
        UserModel result = UserControler.getUser(id);
        return Response.status(Response.Status.OK).entity(result).build();
    }
}