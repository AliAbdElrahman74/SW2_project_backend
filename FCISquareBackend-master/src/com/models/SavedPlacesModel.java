package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SavedPlacesModel {

	private int userId ;
	private int placeId;
	public int getPlaceId() {
		return placeId;
	}
	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public static ArrayList<placeModel> getUserSavedPlace(int userid) {
		try {
			ArrayList<placeModel> arr = new  ArrayList<placeModel>();
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM usersavedplace where USERID = " + userid;
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();;
			while(rs.next()) {
				placeModel place = placeModel.getPlace(rs.getInt("PLACEID"));
				arr.add(place);
			}
			return arr;
		}catch(SQLException e) {
			return null;
		}
	}
	
	public static ArrayList<userModel> getPlaceUsers(int placeID){
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "select USERID from USERSAVEDPLACE where PLACEID = " + placeID + ";";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<userModel> users = new ArrayList<>();
			while (rs.next()) {
				users.add(userModel.getuser(rs.getInt("USERID")));
			}
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean savePlace(int userID , int placeID){
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "insert into USERSAVEDPLACE values(" + userID +", " + placeID + ");";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
}