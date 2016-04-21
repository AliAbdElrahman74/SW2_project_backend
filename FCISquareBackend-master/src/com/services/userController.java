package com.services;


import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.*;
import com.sun.org.apache.bcel.internal.generic.INEG;

import sun.security.pkcs.ParsingException;



@Path("/")
public class userController {
	  @POST 
	  @Path("/showhomepage")
	  @Produces(MediaType.TEXT_PLAIN)
	  public static String showHomePage(@FormParam("id") String id) {
		  ArrayList<CheckInModel> arr = userModel.getHomeCheckIn(Integer.parseInt(id));
		  JSONArray j = new JSONArray();
		  JSONObject json = new JSONObject();
		  for(int i = 0; i < arr.size(); i ++) {
			  json.put("CheckIn " + i, arr.get(i).getId());
		  }
		  j.add(json);
		  json = new JSONObject();
		  ArrayList<placeModel> temparr = userModel.getHomePlaces(Integer.parseInt(id));
		  for(int i = 0; i < temparr.size(); i ++){
			  json.put("Place " + i , temparr.get(i).getId());
		  }
		  j.add(json);
		  return j.toJSONString();
	  }
	  
		@POST
		@Path("/UserController/GetUserActionHistory")
		@Produces(MediaType.TEXT_PLAIN)
		public String getUserActionHistory(@FormParam("userID") String userID)
		{
			int temp = Integer.parseInt(userID);
			ArrayList<LikeModel> likeModelArray = LikeModel.getUserLikesHistoryOnCheckins(temp);
			ArrayList<CommentModel> commentModelArray = CommentModel.getUserCommentsHistoryOnCheckins(temp);
			ArrayList<CheckInModel> checkinModelArray = CheckInModel.getUserCheckinsHistory(temp);
			
			JSONArray array = new JSONArray();
			JSONObject obj;
			int like = 0 , comment = 0 , checkin = 0;
			while(true)
			{
				
				if(like < likeModelArray.size() && comment < commentModelArray.size())
				{
					if(likeModelArray.get(like).getTimeStamp().after(commentModelArray.get(comment).getCommentDate()))
					{
						if(checkin < checkinModelArray.size())
						{
							if(likeModelArray.get(like).getTimeStamp().after(checkinModelArray.get(checkin).getCheckinDate()))
							{
								array.add(LikeController.getJsonLikeModel(likeModelArray.get(like)));
								like++;
							}
							else
							{
								array.add(CheckInController.getJsonCheckinModel(checkinModelArray.get(checkin)));
								checkin++;
							}
						}
						else
						{
							array.add(LikeController.getJsonLikeModel(likeModelArray.get(like)));
							like++;
						}
					}
					else
					{
						if(checkin < checkinModelArray.size())
						{
							if(commentModelArray.get(comment).getCommentDate().after(checkinModelArray.get(checkin).getCheckinDate()))
							{
								array.add(CommentController.getJsonCommentModel(commentModelArray.get(comment)));
								comment++;
							}
							else
							{
								array.add(CheckInController.getJsonCheckinModel(checkinModelArray.get(checkin)));
								checkin++;
							}
						}
						else
						{
							array.add(CommentController.getJsonCommentModel(commentModelArray.get(comment)));
							comment++;
						}
					}
				}
				else if(like < likeModelArray.size() && checkin < checkinModelArray.size())
				{
					if(likeModelArray.get(like).getTimeStamp().after(checkinModelArray.get(checkin).getCheckinDate()))
					{
						array.add(LikeController.getJsonLikeModel(likeModelArray.get(like)));
						like++;
					}
					else
					{
						array.add(CheckInController.getJsonCheckinModel(checkinModelArray.get(checkin)));
						checkin++;
					}
				}
				else if(comment < commentModelArray.size() && checkin < checkinModelArray.size())
				{
					if(commentModelArray.get(comment).getCommentDate().after(checkinModelArray.get(checkin).getCheckinDate()))
					{
						array.add(CommentController.getJsonCommentModel(commentModelArray.get(comment)));
						comment++;
					}
					else
					{
						array.add(CheckInController.getJsonCheckinModel(checkinModelArray.get(checkin)));
						checkin++;
					}
				}
				else if(like < likeModelArray.size())
				{
					array.add(LikeController.getJsonLikeModel(likeModelArray.get(like)));
					like++;
				}
				else if(comment < commentModelArray.size())
				{
					array.add(CommentController.getJsonCommentModel(commentModelArray.get(comment)));
					comment++;
				}
				else if(checkin < checkinModelArray.size())
				{
					array.add(CheckInController.getJsonCheckinModel(checkinModelArray.get(checkin)));
					checkin++;
				}
				else
					return array.toJSONString();
			}
		}
	  
}