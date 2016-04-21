package com.services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.models.SavedPlacesModel;;

@Path("/SavedPlacesController")
public class SavedPlacesController {
	@POST
	@Path("/SavedPlacesController/savePlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String savePlace(@FormParam("userID") String userID , @FormParam("placeID") String placeID){
		boolean ok = SavedPlacesModel.savePlace(Integer.parseInt(userID) , Integer.parseInt(placeID));
		JSONObject json = new JSONObject();
		json.put("status", ok ? 1 : 0);
		return json.toJSONString();
	}
}