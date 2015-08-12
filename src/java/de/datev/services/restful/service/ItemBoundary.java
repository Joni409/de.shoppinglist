package de.datev.services.restful.service;

import de.datev.services.models.ItemController;
import de.datev.services.models.ItemModel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
    public void CreateItem(String json) throws JSONException
    {
        /*JSONObject jsonData = new JSONObject(json);
        String name = jsonData.getString("name");
        String beschreibung = jsonData.getString("beschreibung");
        String color = jsonData.getString("color");
        ShoppingListController.CreateList(name, beschreibung, color);*/
    }
    
    @PUT
    @Consumes({"application/json"})
    public Response UpdateItem(String json, @PathParam("ItemID") String id) throws JSONException{
        JSONObject jsonData = new JSONObject(json);
        HashMap<String, String> updateData = new HashMap<String, String>();
        
        Iterator iteratorKeys = jsonData.keys();
        
        do{
            String currentKey = (String)iteratorKeys.next();
            updateData.put(currentKey, jsonData.getString(currentKey));
        }while(iteratorKeys.hasNext());
        
        boolean successful = ItemController.updateItem(updateData, id);
        
         if(successful)
        {
            return Response.status(Response.Status.OK).build();
        }
        else
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
