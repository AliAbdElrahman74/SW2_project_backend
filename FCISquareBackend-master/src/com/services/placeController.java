package com.services;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.placeModel;

@Path("/placeController")
public class placeController {
	
	
	static double distance(double fromLat, double fromLon, double toLat, double toLon) {
	    double radius = 6378137;   // approximate Earth radius, *in meters*
	    double deltaLat = toLat - fromLat;
	    double deltaLon = toLon - fromLon;
	    double angle = 2 * Math.asin( Math.sqrt(
	        Math.pow(Math.sin(deltaLat/2), 2) + 
	        Math.cos(fromLat) * Math.cos(toLat) * 
	        Math.pow(Math.sin(deltaLon/2), 2) ) );
	    return radius * angle;
	}
	
	@POST
	@Path("/placeController/getAllPlaces")
	@Produces(MediaType.TEXT_PLAIN)
	public JSONArray getAllPlaces(@FormParam("lat") String lat,
			@FormParam("lon") String lon) throws SQLException {
		ArrayList<placeModel> p = placeModel.getAllPlaces() ;
		//Sort the places
		
		double lattemp = Double.parseDouble(lat);
		double lontemp = Double.parseDouble(lon);
		for (int i = 0 ; i < p.size() ; i++)
		{
			for (int j = i+1 ; j < p.size() ; j++)
			{
				if (distance(lattemp, lontemp, p.get(i).getLatitude(), p.get(i).getLongtude()) > distance(lattemp, lontemp, p.get(j).getLatitude(), p.get(j).getLongtude()))
				{
					placeModel temp ; 
					temp = p.get(i) ;
					p.set(i, p.get(j))  ;
					p.set(i, temp)  ;
				}
			}
			
		}
		JSONArray arr = new JSONArray() ;
		
		for (int  i  = 0 ; i < p.size() ; i++)
		{
			JSONObject json = new JSONObject();
			json.put("id", p.get(i).getId());
			json.put("name", p.get(i).getName());
			json.put("description", p.get(i).getDescription());
			json.put("latitude", p.get(i).getLatitude());
			json.put("longitude", p.get(i).getLongtude());
			json.put("numOfRates", p.get(i).getNumberofRates());
			json.put("rate", p.get(i).getRate());
			json.put("creatorId", p.get(i).getCreatorId());
			
			arr.add(json) ;

		}
		
		
		return arr;
	}
	
	@POST
	@Path("/placeController/getPlaceByName")
	@Produces(MediaType.TEXT_PLAIN)
	public JSONObject getPlaceByName(@FormParam("name") String name) throws SQLException {
		
		placeModel p = placeModel.getPlaceByName(name) ;
		
			JSONObject json = new JSONObject();
			json.put("id", p.getId());
			json.put("name", p.getName());
			json.put("description", p.getDescription());
			json.put("latitude", p.getLatitude());
			json.put("longitude", p.getLongtude());
			json.put("numOfRates", p.getNumberofRates());
			json.put("rate", p.getRate());
			json.put("creatorId", p.getCreatorId());
		
		
		return json;
	}
	
	
	@POST
	@Path("/placeController/addNewPlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String addNewPlace (@FormParam("placeName") String name , @FormParam("placedescription") String description
			, @FormParam("lantitude") String latitude , @FormParam("longitude") String longitude){
		double lat = Double.parseDouble(latitude);
		double lon = Double.parseDouble(longitude);
		boolean ok = placeModel.addNewPlace(name, description, lat, lon);
		JSONObject json = new JSONObject();
		json.put("status", ok ? 1 : 0);
		return json.toJSONString();
	}
	
	

}
