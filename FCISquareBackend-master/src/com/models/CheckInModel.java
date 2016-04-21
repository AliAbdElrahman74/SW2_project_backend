package com.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.simple.JSONArray;

public class CheckInModel {

	private int id ;
	private int userId;
	private int placeId;
	private Timestamp checkinDate;

	public void setCheckinDate(Timestamp checkinDate) {
		this.checkinDate = checkinDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Timestamp getCheckinDate() {
		return checkinDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	
	public static ArrayList<CheckInModel> getUserCheckIn(int userid) {
		try{
			ArrayList<CheckInModel> arr = new ArrayList<CheckInModel>();
			Connection conn =  DBConnection.getActiveConnection();
			String sql = "SELECT * FROM checkin where USERID = " + userid;
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				CheckInModel temp = new CheckInModel();
				temp.setId(rs.getInt("CHECKINID"));
				temp.setCheckinDate(rs.getTimestamp("CHECKINDATE"));
				temp.setPlaceId(rs.getInt("PLACEID"));
				temp.setUserId(userid);
				arr.add(temp);
			}
			return arr;
		} catch(SQLException e) {
			return null;
		}
		
	}
	
	public static CheckInModel getCheckIn(int id) {
		try{
			CheckInModel checkin = new CheckInModel();
			Connection conn = (Connection) DBConnection.getActiveConnection();
			String sql = "SELECT * FROM checkin	 where CHECKINID = " + id;
			PreparedStatement stmt;
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				checkin.setId(rs.getInt("CHECKINID"));
				checkin.setCheckinDate(rs.getTimestamp("CHECKINDATE"));
				checkin.setPlaceId(rs.getInt("PLACEID"));
				checkin.setUserId(rs.getInt("USERID"));
			}
			return checkin;
		} catch(SQLException e) {
			return null;
		}	
	}

	

	public static void saveCheckIn( Integer userId ,Integer placeId ) throws SQLException
	{
		Connection conn = DBConnection.getActiveConnection();
		String sql =  "INSERT INTO CHECKIN (USERID , PLACEID  ) values (   , " + userId.toString() + " , " +placeId.toString() + " ,  ) ;" ;		
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.executeUpdate() ; 
		
	}
	
	public static int numberOfCheckIns() throws SQLException
	{
		int count = 0;
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select * from CHECKIN ;";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery() ; 
		while (rs.next()) {
			count++ ;
		}
		return count ; 
		
	}
	
	
	public static ArrayList<userModel> getUsersForPlace (Integer placeId) throws SQLException
	{
		ArrayList<userModel> users = new ArrayList<userModel>() ;
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select USERID from CHECKIN  WHERE PLACEID = " + placeId + " ;";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery() ; 
		while(rs.next())
		{
			System.out.println(rs.getInt("USERID"));
			userModel user = userModel.getuser(rs.getInt("USERID")) ;
			users.add(user) ;
		}
		for (int i = 0 ; i < users.size() ; i++)
		{
			System.out.println(users.get(i).getName());
		}
		
		return users ;
	}
	public static ArrayList<CheckInModel> getUserCheckinsHistory(int userID)
	{
		Connection con = DBConnection.getActiveConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		CheckInModel checkinModel = new CheckInModel();
		ArrayList<CheckInModel> checkinModelArray = new ArrayList<CheckInModel>();
		String query = "Select * from CHECKIN where USERID = ? order by CHECKINDATE desc";
		
		try
		{
			ps = con.prepareStatement(query);
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			while(rs.next())
			{
				checkinModel.id = rs.getInt(1);
				checkinModel.userId = rs.getInt(2);
				checkinModel.placeId = rs.getInt(3);
				checkinModel.checkinDate = rs.getTimestamp(4);
				checkinModelArray.add(checkinModel);
			}
			return checkinModelArray;
		}
		catch(SQLException e)
		{
			return null;
		}
	}
}
