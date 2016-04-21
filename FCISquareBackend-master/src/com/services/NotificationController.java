package com.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.BrandNotificationModel;
import com.models.CheckInModel;
import com.models.CommentNotificationModel;
import com.models.FollowNotificationModel;
import com.models.LikeNotificationModel;
import com.models.NotificationModel;

@Path("/")
public class NotificationController {
	@POST
	@Path("/NotificationController/getnotifications")
	@Produces(MediaType.TEXT_PLAIN)
	public String getnotifications(@FormParam("userID") String userID){
		
		int itemp = Integer.parseInt(userID);
		ArrayList<NotificationModel> comments = CommentNotificationModel.getnotifications(itemp);
		ArrayList<NotificationModel> tips = BrandNotificationModel.getnotifications(itemp);
		ArrayList<NotificationModel> follows = FollowNotificationModel.getnotifications(itemp);
		ArrayList<NotificationModel> likes = LikeNotificationModel.getnotifications(itemp);
		
		ArrayList<NotificationModel> all = new ArrayList<>();
		for(int i=0 ; i < likes.size(); i++)
			all.add(likes.get(i)) ;
		for(int i=0 ; i < comments.size(); i++)
			all.add(comments.get(i)) ;
		for(int i=0 ; i < tips.size(); i++)
			all.add(tips.get(i)) ;
		for(int i=0 ; i < follows.size(); i++)
			all.add(follows.get(i)) ;
		
		for(int i=0 ; i < all.size(); i++){
			for(int j=i+1 ; j < all.size(); j++){
				if(all.get(i).getDate().before(all.get(j).getDate())){
					NotificationModel temp = all.get(i) ;
					all.set(i, all.get(j)) ;
					all.set(j, temp) ;
				}
			}
		}
		
		JSONArray arr = new JSONArray();
		
		for(int i=0 ; i < all.size(); i++){
			JSONObject json = new JSONObject();
			if(all.get(i).getFavorID() != 0)
				json.put( "favorID" , all.get(i).getFavorID() ) ;
			else if(all.get(i).getCommentID() != 0)
				json.put( "commentID" , all.get(i).getCommentID() );
			else if(all.get(i).getFollowID() != 0)
				json.put( "followID" , all.get(i).getFollowID() );
			else if(all.get(i).getTipID() != 0)
				json.put( "tipID" , all.get(i).getTipID() );
			json.put("notificationID", all.get(i).getNotificationID());
			json.put("userID", all.get(i).getUserID());
			json.put("notificationDescription", all.get(i).getDescription());
			json.put("notificationDate", all.get(i).getDate());
			arr.add(json) ;
		}
		
		return arr.toJSONString() ;	
	}
	
	@POST
	@Path("/NotificationController/respondtonotification")
	@Produces(MediaType.TEXT_PLAIN)
	public String respondToNotification(@FormParam("notificationID") String notificationID){
		JSONObject json = new JSONObject();
		int temp = Integer.parseInt(notificationID);
		String type = NotificationModel.getType(temp) ;
		if(type == "favor"){
			CheckInModel notificationLike = LikeNotificationModel.respondToNotification(temp);
			json.put("checkinID", notificationLike.getId());
		}
		else if(type == "comment"){
			CheckInModel notificationComment = CommentNotificationModel.respondToNotification(temp);
			json.put("checkinID", notificationComment.getId());
		}
		else if(type == "follow"){
			CheckInModel notificationFollow = FollowNotificationModel.respondToNotification(temp);
			json.put("checkinID", notificationFollow.getId());
		}
		else{
			CheckInModel notificationTip = BrandNotificationModel.respondToNotification(temp);
			json.put("checkinID", notificationTip.getId());
		}
		return json.toJSONString();
	}
	
	@POST
	@Path("/NotificationController/generatenotification")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean generateNotification(@FormParam("userID") String userID){
		
		return false;
		
	}
	
	
}