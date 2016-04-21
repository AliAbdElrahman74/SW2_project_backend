package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.Statement;

public class CommentNotificationModel extends NotificationModel {
	public static ArrayList<NotificationModel> getnotifications(int userID){
		try {
			ArrayList<NotificationModel> comments = new ArrayList<>() ;
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from NOTIFICATION where USERID = " + userID + " ;";
			PreparedStatement statement;
			statement = conn.prepareStatement(sql) ;
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				NotificationModel comment = new CommentNotificationModel();
				comment.notificationID = rs.getInt(1) ;
				comment.userID = rs.getInt(2) ;
				comment.commentID = rs.getInt(4) ;
				comment.description = rs.getString(7);
				comment.date = rs.getTimestamp(8) ;
				if(comment.commentID != 0)
					comments.add(comment) ;
			}
			return comments ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("AY7AGA");
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
				NotificationModel commentPost = new CommentNotificationModel();
				commentPost.favorID = rs.getInt(3) ;
				commentPost.userID = rs.getInt(2) ;
				String sql2 = "Select CHECKIN.CHECKINID from FAVOR,CHECKIN where CHECKIN.USERID = COMMENT.USERID";
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