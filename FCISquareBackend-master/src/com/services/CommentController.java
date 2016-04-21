package com.services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;
import org.json.simple.JSONObject;

import com.models.CheckInModel;
import com.models.CommentModel;
import com.models.userModel;

@Path("/CommentController")
public class CommentController {
	
	
	
	@POST
	@Path("/CommentController/addComment")
	@Produces(MediaType.TEXT_PLAIN)
	public JSONObject saveCheckIn(
			@FormParam("text") String text ,
			@FormParam("checkInId") String checkInId,
			@FormParam("userId") String userId) throws SQLException {
				
		 JSONObject json = new JSONObject();	
		 json.put("status", CommentModel.addComment( text, Integer.parseInt(checkInId) , Integer.parseInt(userId))) ;
		 
		 // here we will notify all the users for any new comment
		 
		 ArrayList<userModel> users = CommentModel.getAllusers() ;
		
		return json;
	}

	public static JSONObject getJsonCommentModel(CommentModel commentModel)
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "comment");
		obj.put("commentID", commentModel.getCommentID());
		obj.put("commentTimeStamp", commentModel.getCommentDate());
		obj.put("commentText", commentModel.getCommentText());
		obj.put("userID", commentModel.getUserID());
		obj.put("checkinID", commentModel.getCheckInID());
		return obj;
	}
	
}
