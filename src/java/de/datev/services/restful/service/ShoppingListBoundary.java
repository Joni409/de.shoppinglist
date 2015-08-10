/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.datev.services.restful.service;

import de.datev.services.models.ShoppingListModel;
import de.datev.services.models.ShoppingListController;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("lists/{ListenID}")
public class ShoppingListBoundary {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getList(@PathParam("ListenID") int id)
    {
        ShoppingListModel result = ShoppingListController.getList(id);
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
