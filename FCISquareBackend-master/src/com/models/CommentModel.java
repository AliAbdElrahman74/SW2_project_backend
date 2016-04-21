package com.models;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;


public class CommentModel {
	private int commentID ;
	private Timestamp commentDate ;
	private String commentText ;
	private int checkInID;
	private int userID;
	
	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	public Timestamp getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Timestamp commentDate) {
		this.commentDate = commentDate;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public void addComment() {
		
	}
	
	public void removeComment(int id ){
		
	}
	public int getCheckInID() {
		return checkInID;
	}
	public void setCheckInID(int checkInID) {
		this.checkInID = checkInID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public static ArrayList<CommentModel> getUserComments(int userid) {
		try {
			ArrayList<CommentModel> arr = new  ArrayList<CommentModel>();
			Connection conn = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM comment where USERID = " + userid;
			PreparedStatement stmt;
			stmt =  conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();;
			while(rs.next()) {
				CommentModel comm = new CommentModel();
				comm.setCommentDate(rs.getTimestamp("COMMENTDATE"));
				comm.setCommentID(rs.getInt("COMMENTID"));
				comm.setCommentText(rs.getString("COMMENTTEXT"));
				comm.setCheckInID(rs.getInt("CHECKINID"));
				comm.setUserID(rs.getInt("USERID"));
				arr.add(comm);
			}
			return arr;
		}catch(SQLException e) {
			return null;
		}
	}
	
	
	public static int addComment(String text , Integer checkInId , Integer userId) 
	{
		Connection conn = (Connection) DBConnection.getActiveConnection();
		String sql = "INSERT INTO   COMMENT (USERID , CHECKINID , COMMENTTEXT  )  values (  " + userId.toString() + " , " +checkInId.toString() +  " , " + text+  " ,  ) ;";
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		return 1 ; 
	}
	
	public static ArrayList<userModel> getAllusers() throws SQLException
	{
		ArrayList<userModel> users= new ArrayList<userModel>() ;
		Connection conn = (Connection) DBConnection.getActiveConnection();
		String sql = "Select  USERID from  COMMENT INNER JOIN  CHECKIN WHERE COMMENT.CHECKINID = CHECKIN.CHECKINID ;" ;
		PreparedStatement stmt;

		stmt = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery() ;
		while (rs.next())
		{
			userModel user = userModel.getuser(rs.getInt("USERID")) ;
			users.add(user) ;
		}
		
		return users ; 
		
	}
	
	public static ArrayList<CommentModel> getUserCommentsHistoryOnCheckins(int userID)
	{
		Connection con = DBConnection.getActiveConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		CommentModel commentModel = new CommentModel();
		ArrayList<CommentModel> commentModelArray = new ArrayList<CommentModel>();
		String query = "Select * from COMMENT where USERID = ? order by COMMENTDATE DESC";
		
		try
		{
			ps = con.prepareStatement(query);
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			while(rs.next())
			{
				commentModel.commentID = rs.getInt(1);
				commentModel.userID = rs.getInt(2);
				commentModel.checkInID = rs.getInt(3);
				commentModel.commentText = rs.getString(4);
				commentModel.commentDate = rs.getTimestamp(5);
				commentModelArray.add(commentModel);
			}
			return commentModelArray;
		}
		catch(SQLException e)
		{
			return null;
		}
	}

}