package com.Model;

import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.DAO.JDBCUtils;

public class TravelUser {
	private int userID = 0; // UserID
	private String username = null; // Username
	private String email = null; // Email
	private String pass = null; // Pass
	private int state = 1; // State

	java.sql.Timestamp dateJoined = null; // DateJoined
	java.sql.Timestamp dateLastModified = null; // DateLastModified

	private int[] myImages = null;
	private int[] myFavouriteImages = null;

	private TravelUser[] myFriends = null;

	public TravelUser() {
	}

	public Map<String, String> getForSession() {
		Map<String, String> travelUserMap = new HashMap<String, String>();
		travelUserMap.put("uid", getUserID()+"");
		travelUserMap.put("username", getUsername());
		travelUserMap.put("email", getEmail());
		
		travelUserMap.put("state", state+"");
		
		System.out.println(this);
		
		return travelUserMap;
	}
	
	public TravelUser(Integer userID, String username, String email) {
		this.userID = userID;
		this.username = username;
		this.email = email;

		
	}

	public void logout() {
		// 連接數據庫
		Connection connection = JDBCUtils.getConntection();

		// 配置sql語句
		String sql = "UPDATE FROM WHERE UID = ?";

		// 改變登出時間
	}

		/* 修改密碼 */
	public void modifyUserPass(String password) {

	}

	@Override
	public String toString() {
		StringBuffer deStringBuffer = new StringBuffer("-->" + userID);
		deStringBuffer.append("\n\t");
		deStringBuffer.append("username: " + username + "; email: " + email);
		deStringBuffer.append("\n\t");
		deStringBuffer.append("pass: " + pass + "; state: " + state + "; dateJoined: " + dateJoined);
		deStringBuffer.append("\n");
		deStringBuffer.append("");
		
		return deStringBuffer.toString();
	}

	/*
	 * getter & setter
	 */
	public int[] getMyImages() {
		return myImages;
	}

	public void setMyImages(Object myImages) {
		this.myImages = (int[]) myImages;
	}

	public int[] getMyFavouriteImages() {
		return myFavouriteImages;
	}

	public void setMyFavouriteImages(Object myFavouriteImages) {
		this.myFavouriteImages = (int[]) myFavouriteImages;
	}

	public TravelUser[] getMyFriends() {
		return myFriends;
	}

	public void setMyFriends(Object myFriends) {
		this.myFriends = (TravelUser[]) myFriends;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(Object username) {
		this.username = (String) username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(Object email) {
		this.email = (String) email;
	}

	public int getUserID() {
		return this.userID;
	}

	public void setUserID(Object userID) {
		this.userID = (int) userID;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(Object pass) {
		this.pass = (String) pass;
	}

	public int getState() {
		return state;
	}

	public void setState(Object state) {
		this.state = (int) state;
	}

	public java.sql.Timestamp getDateJoined() {
		return dateJoined;
	}
	
	public String getDateJoinedStr() {
		String string = getDateJoined().toString(); 
		return string.substring(0,string.lastIndexOf("."));
	}

	public void setDateJoined(Object dateJoined) {
		this.dateJoined = (java.sql.Timestamp) dateJoined;
	}

	public java.sql.Timestamp getDateLastModified() {
		return dateLastModified;
	}

	public void setDateLastModified(Object dateLastModified) {
		this.dateLastModified = (java.sql.Timestamp) dateLastModified;
	}
}
