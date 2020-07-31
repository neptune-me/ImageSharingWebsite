package com.Model;

import java.sql.Timestamp;

import com.DAO.CommentDAO;
import com.DAO.TravelUserDAO;

public class Comment {

	private int commentID;
	private int userID;
	private int imageID;
	private Timestamp dateComment;
	private String comment;
	
	//Ó‹Ëã
	private int agreements;
	private  TravelUser user;
	private  CommentDAO commentDAO = CommentDAO.getCommentDAO();
	private  TravelUserDAO  travelUserDAO = TravelUserDAO.getTravelUserDAO();
	
	
	public TravelUser getUser() {
		//æœ½Ó”µ“þŽìÓ‹Ëã
		if (user == null) {
			user = travelUserDAO.getByUid(userID);
		}
		return user;
	}

	public void setUser(TravelUser user) {
		this.user = user;
	}

	public int getAgreements() {
		agreements = commentDAO.getAgreeNumberByCommentID(commentID);
		//æœ½Ó”µ“þŽìÓ‹Ëã
		return agreements;
	}

	public void setAgreements(Object agreements) {
		this.agreements = (int)agreements;
	}

	@Override
	public String toString() {
		return "\nComment [commentID=" + commentID + ", userID=" + userID + ", imageID=" + imageID + ", dateComment="
				+ dateComment + ", comment=" + comment + "]";
	}

	public String toHTML() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<>");
		
		return stringBuffer.toString();
	}
	
	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(Object commentID) {
		this.commentID = (int) commentID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(Object userID) {
		this.userID = (int)userID;
	}
	public int getImageID() {
		return imageID;
	}
	public void setImageID(Object imageID) {
		this.imageID = (int)imageID;
	}
	public Timestamp getDateComment() {
		return dateComment;
	}
	public String getDateCommentStr() {
		String stri = dateComment.toString();
		return stri.substring(0, stri.lastIndexOf("."));
		
	}
	
	public void setDateComment(Object dateComment) {
		this.dateComment = (Timestamp)dateComment;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(Object comment) {
		this.comment = (String)comment;
	}
	
	
}
