package de.datev.services.restful.service;

import de.datev.services.models.ItemController;
import de.datev.services.models.ItemModel;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author TG00030
 */
@Path("lists/{ListenID}/items/{ItemID}")
public class ItemBoundary {
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getItem(@PathParam("ItemID") String id)
    {
        ItemModel result = ItemController.getItem(id);
        return Response.status(Response.Status.OK).entity(result).build();
    }
    
    
    @POST
    @Consumes({"application/json"})
    public void CreateList(String json) throws JSONException
    {
        /*JSONObject jsonData = new JSONObject(json);
        String name = jsonData.getString("name");
        String beschreibung = jsonData.getString("beschreibung");
        String color = jsonData.getString("color");
        ShoppingListController.CreateList(name, beschreibung, color);*/
    }
}
