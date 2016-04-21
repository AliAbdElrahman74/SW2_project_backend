 package com.services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.CheckInModel;
import com.models.placeModel;
import com.models.userModel;

@Path("/")
public class CheckInController {
	

	@POST
	@Path("/CheckInController/saveCheckIn")
	@Produces(MediaType.TEXT_PLAIN)
	public String saveCheckIn(@FormParam("userId") String userId ,
		@FormParam("placeId")String placeId ) throws SQLException {
		
		 CheckInModel.saveCheckIn( Integer.parseInt(userId), Integer.parseInt(placeId));
		System.out.println("deokdeokdeok");
		 JSONObject json = new JSONObject();		
		json.put("status", 1) ;
		return json.toJSONString();
	
	}
	
	
	@POST
	@Path("/CheckInController/numberOfCheckIns")
	@Produces(MediaType.TEXT_PLAIN)
	public String numberOfCheckIns() throws SQLException {
		
		 JSONObject json = new JSONObject();		
		 json.put("count", CheckInModel.numberOfCheckIns()) ;
		return json.toJSONString();
	}
	
	
	@POST
	@Path("/CheckInController/usersForPlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String usersForPlace(@FormParam("id") String PlaceId) throws SQLException {
		
		JSONArray arr = new JSONArray() ;
		ArrayList<userModel> users = CheckInModel.getUsersForPlace(Integer.parseInt(PlaceId)) ;
		System.out.println("dkejdeijdeojd");
		for (int i = 0 ; i < users.size()  ; i++)
		{
			JSONObject json = new JSONObject();		
			json.put("Name",users.get(i).getName() ) ;
			json.put("password", users.get(i).getPassword()) ;
			json.put("email",users.get(i).getEmail() ) ;
			json.put("id",users.get(i).getId() ) ;
			json.put("gender",users.get(i).getGender() ) ;
			json.put("birthofDate", users.get(i).getBirthofDate()) ;
			json.put("profilePicture",users.get(i).getProfilePicture() ) ;
			arr.add(json) ;
		}
		
		return arr.toString();
	}
	
	public static JSONObject getJsonCheckinModel(CheckInModel checkinModel)
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "checkin");
		obj.put("checkinID", checkinModel.getId());
		obj.put("userID", checkinModel.getUserId());
		obj.put("placeID", checkinModel.getPlaceId());
		obj.put("checkinTimeStamp", checkinModel.getCheckinDate());
		return obj;
	}

}
