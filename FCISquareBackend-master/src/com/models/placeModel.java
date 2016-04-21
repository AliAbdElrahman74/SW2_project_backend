package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class placeModel {
	private String name;
	private String description;
	private double latitude  ;
	private int id;
	private double longtude;
	private int numberofRates;
	private double rate;
	private int creatorId;
	public placeModel()
	{
		name = "" ; 
		description = "" ;
		latitude = 0 ;
		longtude = 0 ;
		numberofRates = 0 ;
		rate = 0.0 ;
		creatorId = 0 ; 
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public int getNumberofRates() {
		return numberofRates;
	}
	public void setNumberofRates(int numberofRates) {
		this.numberofRates = numberofRates;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getLongtude() {
		return longtude;
	}
	public void setLongtude(int longtude) {
		this.longtude = longtude;
	}
	
	public static ArrayList<placeModel> getAllPlaces() throws SQLException
	{
		ArrayList<placeModel> p = new ArrayList<placeModel>() ;
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select * from PLACE ;";
		// System.out.println(sql);
		
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery() ; 
		while (rs.next()) {
			System.out.println("Hi");
			placeModel place = new placeModel();
			place.id = rs.getInt("PLACEID");
			place.name = rs.getString("PLACENAME");
			place.description = rs.getString("PLACEDESCRIPTION");
			place.latitude = rs.getDouble("LATITUDE");
			place.longtude = rs.getDouble("LONGITUDE");
			place.numberofRates = rs.getInt("NUMBEROFRATES");
			place.rate = rs.getDouble("RATE") ;
			place.creatorId = rs.getInt("USERID") ;
			p.add(place) ;
		}
		System.out.println(p.size());
		for (int i = 0 ; i < p.size() ; i++)
		{
			System.out.println(p.get(i).getName());
			System.out.println(p.get(i).getDescription());
			System.out.println(p.get(i).getId());
			System.out.println(p.get(i).getLatitude());
			System.out.println(p.get(i).getLongtude());
			System.out.println(p.get(i).getNumberofRates());
			System.out.println(p.get(i).getRate());			
		}
		
		return p ; 
	}
	
	public static placeModel getPlaceByName(String name) throws SQLException
	{
		Connection conn = DBConnection.getActiveConnection();
		String sql =  "select *  from PLACE WHERE PLACE.PLACENAME = '"+name+"' ;" ;		
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery() ; 
		if (rs.next()) {
			System.out.println("Hi");
			placeModel place = new placeModel();
			place.id = rs.getInt("PLACEID");
			place.name = rs.getString("PLACENAME");
			place.description = rs.getString("PLACEDESCRIPTION");
			place.latitude = rs.getDouble("LATITUDE");
			place.longtude = rs.getDouble("LONGITUDE");
			place.numberofRates = rs.getInt("NUMBEROFRATES");
			place.rate = rs.getDouble("RATE") ;
			place.creatorId = rs.getInt("USERID") ;
			return place ;
		}
		
		
		return null ;
		
	}
	
	public static placeModel getPlace(int placeID){
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "select * from PLACE where PLACEID = " + placeID +";";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				placeModel place = new placeModel();
				place.id = rs.getInt("PLACEID");
				place.creatorId = rs.getInt("USERID");
				place.name = rs.getString("PLACENAME");
				place.latitude = rs.getDouble("LATITUDE");
				place.longtude = rs.getDouble("LONGITUDE");
				place.description = rs.getString("PLACEDESCRIPTION");
				place.rate = rs.getDouble("RATE");
				place.numberofRates = rs.getInt("NUMBEROFRATES");
				return place;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean addNewPlace(String name , String description , double latitude , double longitude){
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "insert into PLACE (placename , placedescription , latitude , longitude) values('" 
			+ name + "' , '" + description + "' , " + latitude +" , " + longitude +");";
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
