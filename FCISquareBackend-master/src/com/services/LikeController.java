package com.services;

import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.models.LikeModel;

@Path("/")
public class LikeController 
{
	@POST
	@Path("/LikeController/LikeACheckin")
	@Produces(MediaType.TEXT_PLAIN)
	public String likeACheckin(@FormParam("userID") String userID, @FormParam("checkinID") String checkinID)
	{
		LikeModel likeModel = LikeModel.likeACheckin(Integer.parseInt(userID), Integer.parseInt(checkinID));
		if(likeModel == null)
			return null;
		JSONObject Json = new JSONObject();
		Json.put("likeID", likeModel.getFavorID());
		Json.put("userID", likeModel.getUserID());
		Json.put("checkinID", likeModel.getCheckInID());
		Json.put("tipID", likeModel.getTipID());
		Json.put("likeTimeStamp", likeModel.getTimeStamp());
		return Json.toJSONString();
	}

	public static JSONObject getJsonLikeModel(LikeModel likeModel)
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "like");
		obj.put("likeID", likeModel.getFavorID());
		obj.put("userID", likeModel.getUserID());
		obj.put("checkinID", likeModel.getCheckInID());
		obj.put("tipID", likeModel.getTipID());
		obj.put("likeTimeStamp", likeModel.getTimeStamp());
		return obj;
	}
}
