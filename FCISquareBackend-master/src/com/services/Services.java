package com.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;

import com.models.DBConnection;
import com.models.UserModel;

@Path("/")
public class Services {

	/*
	 * @GET
	 * 
	 * @Path("/signup")
	 * 
	 * @Produces(MediaType.TEXT_HTML) public Response signUp(){ return
	 * Response.ok(new Viewable("/Signup.jsp")).build(); }
	 */

	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass) {
		UserModel user = UserModel.addNewUser(name, email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}
	
	@POST
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id,
			@FormParam("lat") String lat, @FormParam("long") String lon) {
		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id), Double.parseDouble(lat), Double.parseDouble(lon));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello after editing";
		// Connection URL:
		// mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
	}
	
	@POST
	@Path("/follow")
	@Produces(MediaType.TEXT_PLAIN)
	public String followUser(@FormParam("followerId") String followerId , @FormParam("followingId") String followingId){
		Boolean ok = UserModel.followUser(Integer.parseInt(followerId) , Integer.parseInt(followingId));
		JSONObject json = new JSONObject();
		json.put("status", ok ? 1 : 0);
		return json.toJSONString();
	}
	@POST
	@Path("/unFollow")
	@Produces(MediaType.TEXT_PLAIN)
	public String unFollowUser(@FormParam("followerId") String followerId , @FormParam("followingId") String followingId){
		Boolean ok = UserModel.unFollowUser(Integer.parseInt(followerId) , Integer.parseInt(followingId));
		System.out.println("\n" + ok);
		JSONObject json = new JSONObject();
		json.put("status", ok ? 1 : 0);
		return json.toJSONString();
	}
	@POST
	@Path("/getFollowers")
	@Produces(MediaType.TEXT_PLAIN)
	public String unFollowUser(@FormParam("Id") String id){
		ArrayList<UserModel> followers = UserModel.getFollowers(Integer.parseInt(id));
		JSONObject json = new JSONObject();
		for(int i = 0; i < followers.size(); i ++){
			JSONObject j = new JSONObject();
			j.put("Name: ", followers.get(i).getName());
			json.put("follower " + (i+1) , j);
		}
		return json.toJSONString();
	}
	@POST
	@Path("/getLastPosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLastPosition(@FormParam("Id") String id){
		UserModel user = UserModel.getLastPosition(Integer.parseInt(id));
		JSONObject json = new JSONObject();
		json.put("lon: " , user.getLon());
		json.put("lon: " , user.getLon());
		return json.toJSONString();
	}
}
