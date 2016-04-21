package com.models;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LikeNotificationModel extends NotificationModel {
	public static ArrayList<NotificationModel> getnotifications(int userID){
		try {
			ArrayList<NotificationModel> likes = new ArrayList<>() ;
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from NOTIFICATION where USERID = " + userID;
			PreparedStatement statement;
			statement = conn.prepareStatement(sql) ;
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				NotificationModel like = new LikeNotificationModel();
				like.notificationID = rs.getInt(1) ;
				like.userID = rs.getInt(2) ;
				like.favorID = rs.getInt(3) ;
				like.description = rs.getString(7);
				like.date = rs.getTimestamp(8);
				if(like.favorID != 0)
					likes.add(like) ;
			}
			return likes ;
		} catch (SQLException e) {
			System.out.println("skssksk");
			
		}
		return null;
	}
	public static CheckInModel respondToNotification(int notificationID){
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from NOTIFICATION where NOTIFICATIONID = " + notificationID + " ;";
			PreparedStatement statement , statement1;
			statement = conn.prepareStatement(sql) ;
			ResultSet rs = statement.executeQuery();
			CheckInModel checkin = new CheckInModel() ;
			if(rs.next()){
				NotificationModel likePost = new LikeNotificationModel();
				likePost.favorID = rs.getInt(3) ;
				likePost.userID = rs.getInt(2) ;
				String sql2 = "Select CHECKIN.CHECKINID from FAVOR,CHECKIN where CHECKIN.USERID = FAVOR.USERID";
				statement1 = conn.prepareStatement(sql2) ;
				ResultSet rs2 = statement1.executeQuery();
				if(rs2.next()){
					checkin.setId(rs2.getInt(1));
					return checkin ;
				}
			}
			return checkin;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}