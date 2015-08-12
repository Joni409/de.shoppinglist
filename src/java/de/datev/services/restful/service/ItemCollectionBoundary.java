package de.datev.services.restful.service;

import de.datev.services.models.ItemController;
import de.datev.services.models.ItemModel;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author TG00030
 */
@Path("lists/{ListenID}/items/")
public class ItemCollectionBoundary {
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getItems(@PathParam("ListenID") String id, @QueryParam("function") String function)
    {
        
        if(function != null && function.equals("checkNotifications"))
        {
            List<ItemModel> allListResult = ItemController.getAllItems();
            List<ItemModel> resultWithCheckedNotifications = new ArrayList<ItemModel>();
            for(ItemModel currentItem : allListResult)
            {
                if(currentItem.getNotification())
                {
                    resultWithCheckedNotifications.add(currentItem);
                }
            }
            return Response.status(Response.Status.OK).entity(resultWithCheckedNotifications).build();
        }
        
        List<ItemModel> oneListResult = ItemController.getItems(id);
        return Response.status(Response.Status.OK).entity(oneListResult).build();
    }
    
    
    @POST
    @Consumes({"application/json"})
    public void createItem(String json) throws JSONException
    {
        JSONObject jsonData = new JSONObject(json);
        String name = jsonData.getString("name");
        String einkaufsdatum = jsonData.getString("einkaufsdatum");
        String preis = jsonData.getString("preis");
        String gekauft = jsonData.getString("gekauft");
        String erlediger = jsonData.getString("erlediger");
        ItemController.createItem(name, einkaufsdatum, preis, gekauft, erlediger);
    }
}
