/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.datev.services.restful.service;

import de.datev.services.models.ShoppingListController;
import de.datev.services.models.ShoppingListModel;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author TG00030
 */
@Path("lists/")
public class ShoppingListCollectionBoundary {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getLists()
    {
        List<ShoppingListModel> result = ShoppingListController.getLists();
        return Response.status(Response.Status.OK).entity(result).build();
    }
    
    
    @POST
    @Consumes({"application/json"})
    public void CreateList(String json) throws JSONException
    {
        JSONObject jsonData = new JSONObject(json);
        String name = jsonData.getString("name");
        String beschreibung = jsonData.getString("beschreibung");
        String color = jsonData.getString("color");
        ShoppingListController.CreateList(name, beschreibung, color);
    }
}
