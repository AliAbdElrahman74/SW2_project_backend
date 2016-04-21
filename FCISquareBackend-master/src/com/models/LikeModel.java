package com.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;



public class LikeModel {
	private int favorID;
	private int userID;
	private int checkInID;
	private int TipID;
	private Timestamp timeStamp;
	
    public void addLike(){

    }
    public void removeLike(){

    }
	public int getFavorID() {
		return favorID;
	}
	public void setFavorID(int favorID) {
		this.favorID = favorID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getCheckInID() {
		return checkInID;
	}
	public void setCheckInID(int checkInID) {
		this.checkInID = checkInID;
	}
	public int getTipID() {
		return TipID;
	}
	public void setTipID(int tipID) {
		TipID = tipID;
	}
	
	public static ArrayList<LikeModel> getLikesOnCheckIn(int userId) {
		try {
			ArrayList<LikeModel> arr = new  ArrayList<LikeModel>();
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM favor where USERID = " + userId + " and CHECKINID != \'null\'";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();;
			while(rs.next()) {
				LikeModel like = new LikeModel();
				like.setCheckInID(rs.getInt("CHECKINID"));
				like.setFavorID(rs.getInt("FAVORID"));
				like.setUserID(userId);
				arr.add(like);
			}
			return arr;
		}catch(SQLException e) {
			return null;
		}
	}
	public static LikeModel likeACheckin(int userID, int checkinID)
    {
    	Connection con = DBConnection.getActiveConnection();
    	String query = "insert into FAVOR (USERID , CHECKINID) values (? , ?);";
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
			ps = con.prepareStatement(query , Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, userID);
			ps.setInt(2, checkinID);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			LikeModel likeModel = null;
			if(rs.next())
	    	{
	    		likeModel = new LikeModel();
	    		likeModel.favorID = rs.getInt(1);
	    		likeModel.checkInID = checkinID;
	    		likeModel.userID = userID;
	    		likeModel.TipID = 0;
	    	}
			query = "select FAVORDATE from FAVOR where FAVORID = ?";
    		ps = con.prepareStatement(query);
    		ps.setInt(1, likeModel.favorID);
    		rs = ps.executeQuery();
    		if(rs.next())
    		{
    			likeModel.timeStamp = rs.getTimestamp(1);
    		}
    		return likeModel;
		} catch (SQLException e) {
			return null;
		}
    }

	
	public static ArrayList<LikeModel> getUserLikesHistoryOnCheckins(int userID)
	{
		Connection con = DBConnection.getActiveConnection();
		String query = "select * from FAVOR where USERID = ? and CHECKINID is not null ORDER BY FAVORDATE DESC";
		PreparedStatement ps = null;
		ResultSet rs = null;
		LikeModel likeModel = new LikeModel();
		ArrayList<LikeModel> likeModelArray = new ArrayList<LikeModel>();
		try
		{
			ps = con.prepareStatement(query);
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			while(rs.next())
			{
				likeModel.favorID = rs.getInt(1);
				likeModel.userID = rs.getInt(2);
				likeModel.checkInID = rs.getInt(3);
				likeModel.TipID = rs.getInt(4);
				likeModel.setTimeStamp(rs.getTimestamp(5));
				likeModelArray.add(likeModel);
			}
			return likeModelArray;
		}
		catch(SQLException e)
		{
			return null;
		}
	}
	public Timestamp getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
}