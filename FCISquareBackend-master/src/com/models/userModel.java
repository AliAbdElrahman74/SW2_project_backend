package com.models;
import java.awt.Image;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;




public class userModel {
	private String name;
	private String password;
	private String email;
	private int id;
	private char gender;
	private Date birthofDate;
	private Image profilePicture;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public Image getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(Image profilePicture) {
		this.profilePicture = profilePicture;
	}
	public Date getBirthofDate() {
		return birthofDate;
	}
	public void setBirthofDate(Date birthofDate) {
		this.birthofDate = birthofDate;
	}
	
	public static ArrayList<userModel> getFollowing(int id) {
		ArrayList<userModel> arr = new ArrayList<userModel>();
		try {
			Connection conn = (Connection) DBConnection.getActiveConnection();
			String sql = "SELECT * FROM userfollow where FOLLOWERID  = " + id;

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			ResultSet rs =stmt.executeQuery() , rs1;
			while (rs.next()) {
				sql = "SELECT * FROM USER where USERID  = " + rs.getInt("FOLLOWINGID");
				stmt = conn.prepareStatement(sql);
				rs1 = stmt.executeQuery();
				if(rs1.next()) {
					userModel temp = new userModel();
					temp.setBirthofDate(rs1.getDate("BIRTHDATE"));
					temp.setEmail(rs1.getString("EMAIL"));
					//temp.setGender(rs1.getString("GENDER").charAt(0));
					temp.setId(rs1.getInt("USERID"));
					temp.setName(rs1.getString("FIRSTNAME"));
					temp.setName(temp.getName() + " " + rs1.getString("LASTNAME"));
					temp.setPassword(rs1.getString("PASSWORD"));
					temp.profilePicture = (Image) rs1.getBlob("PROFILEPICTURE");
					arr.add (temp);
				}
			}
			return arr;
		} catch (SQLException e) {
				return null;
		}
	}
	
	public static ArrayList<CheckInModel> getHomeCheckIn(int id) {
		ArrayList<CheckInModel> arr = new ArrayList<CheckInModel>();
		ArrayList<CheckInModel> Checktemp = new ArrayList<CheckInModel>();
		/// get my checkin
		Checktemp = CheckInModel.getUserCheckIn(id);
		for(int i = 0; i < Checktemp.size(); i ++) {
			arr.add(Checktemp.get(i));
		}
		ArrayList<userModel> following = userModel.getFollowing(id);
		/// get my following checks in
		for(int i = 0; i < following.size(); i ++) {
			Checktemp = CheckInModel.getUserCheckIn(following.get(i).getId());
			for(int j = 0; j < Checktemp.size(); j ++) {
				arr.add(Checktemp.get(j));
			}
		}
		
	    /// following's comment on checkins
		ArrayList<CommentModel> commTemp = CommentModel.getUserComments(id);
		for(int j = 0; j < commTemp.size(); j ++) {
			CheckInModel t = new CheckInModel();
			t = CheckInModel.getCheckIn(commTemp.get(j).getCheckInID());
			arr.add(t);
		}
		/// following's Favor on checkins
		ArrayList<LikeModel> liketemp = LikeModel.getLikesOnCheckIn(id);
		for(int i = 0; i < liketemp.size(); i ++) {
			CheckInModel t = new CheckInModel();
			t = CheckInModel.getCheckIn(liketemp.get(i).getCheckInID());
			arr.add(t);
		}
		return arr;	
	}
	
	public static ArrayList<placeModel> getHomePlaces(int id) {
		ArrayList<placeModel> places = new ArrayList<placeModel>();
		ArrayList<placeModel> temp = new ArrayList<placeModel>();
		ArrayList<userModel> following = userModel.getFollowing(id);
		/// get my following checks in
		for(int i = 0; i < following.size(); i ++) {
			temp = SavedPlacesModel.getUserSavedPlace(following.get(i).getId());
			for(int j = 0; j < temp.size(); j ++) {
				places.add(temp.get(j));
			}
		}
		return places;
	}
	
	public static userModel getuser(int id) {
		Connection conn = (Connection) DBConnection.getActiveConnection();
		String sql = "SELECT * FROM USER where USERID  = " + id;

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			ResultSet rs1 = stmt.executeQuery() ;
			System.out.println("before");
			if(rs1.next()) {
				System.out.println("After");
				userModel temp = new userModel();
				temp.setBirthofDate(rs1.getDate("BIRTHDATE"));
				temp.email = rs1.getString("EMAIL") ;
				temp.setGender(rs1.getString("GENDER").charAt(0));
				temp.setId(rs1.getInt("USERID"));
				temp.setName(rs1.getString("FIRSTNAME"));
				temp.setName(temp.getName() + " " + rs1.getString("LASTNAME"));
				temp.setPassword(rs1.getString("PASSWORD"));
				temp.profilePicture = (Image) rs1.getBlob("PROFILEPICTURE");
				return temp;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ay7aga");
		}
		
		return null;
	}
}