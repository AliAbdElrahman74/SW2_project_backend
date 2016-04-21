package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BrandNotificationModel extends NotificationModel {
	public static ArrayList<NotificationModel> getnotifications(int userID){
		try {
			ArrayList<NotificationModel> tips = new ArrayList<>() ;
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from NOTIFICATION where USERID = " + userID;
			PreparedStatement statement;
			statement = conn.prepareStatement(sql) ;
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				NotificationModel tip = new BrandNotificationModel();
				tip.notificationID = rs.getInt(1) ;
				tip.userID = rs.getInt(2) ;
				tip.tipID = rs.getInt(6) ;
				tip.description = rs.getString(7);
				tip.date = rs.getTimestamp(8) ;
				if(tip.tipID != 0)
					tips.add(tip) ;
			}
			return tips ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				NotificationModel brandPost = new BrandNotificationModel();
				brandPost.favorID = rs.getInt(3) ;
				brandPost.userID = rs.getInt(2) ;
				String sql2 = "Select CHECKIN.CHECKINID from FAVOR,CHECKIN where CHECKIN.USERID = BRAND.USERID";
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