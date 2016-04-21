package com.models;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class NotificationModel {
	
	protected int notificationID , userID , favorID , commentID , followID , tipID; 
	protected String description ; 
	protected Timestamp date ;
	public int getNotificationID() {
		return notificationID;
	}
	public void setNotificationID(int notificationID) {
		this.notificationID = notificationID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getFavorID() {
		return favorID;
	}
	public void setFavorID(int favorID) {
		this.favorID = favorID;
	}
	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	public int getFollowID() {
		return followID;
	}
	public void setFollowID(int followID) {
		this.followID = followID;
	}
	public int getTipID() {
		return tipID;
	}
	public void setTipID(int tipID) {
		this.tipID = tipID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public static ArrayList<NotificationModel> getnotifications(int userID){
		return null ;
	}
	
	public static CheckInModel respondToNotification(int userID){
		return null ;
	}
	
	public static String getType(int notificationID){
		try {
			String type = null ;
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from NOTIFICATION where NOTIFICATIONID = " + notificationID + " ;";
			PreparedStatement statement , statement1;
			statement = conn.prepareStatement(sql) ;
			ResultSet rs = statement.executeQuery();
			if(rs.getInt(3) != 0)
				type = "favor" ;
			else if(rs.getInt(4) != 0)
				type = "comment" ;
			else if(rs.getInt(5) != 0)
				type = "follow" ;
			else if(rs.getInt(6) != 0)
				type = "tip" ;
			
			return type ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}
	
	
}