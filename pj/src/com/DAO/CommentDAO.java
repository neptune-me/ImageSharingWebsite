package com.DAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import com.Model.Comment;

public class CommentDAO extends DAO<Comment> {

	public static void main(String[] aStrings) {
		CommentDAO commentDAO = getCommentDAO();

		 commentDAO.sendCommentAndGetKey(20, 1, "!!?");

		// ArrayList<Comment> comments = commentDAO.getCommentsByImageID(1);
		// System.out.println(comments);
	}

	private static CommentDAO commentDAO = null;

	public static CommentDAO getCommentDAO() {
		if (commentDAO == null)
			return new CommentDAO();
		else
			return commentDAO;
	}

	public CommentDAO() {
		super(Comment.class);
		commentDAO = this;
	}

	/**
	 * table: comment
	 * 得到iid的所有评论
	 * @param iid
	 * @return
	 */
	public ArrayList<Comment> getCommentsByImageID(int iid) {
		String sql = "SELECT CommentID, UID as UserID, ImageID, DateComment, Comment " + "FROM comment WHERE imageid=?";

		return getForList(sql, iid);
	}
	public ArrayList<Comment> getCommentsByImageIDOrderByLike(int iid) {
		
		
		String sql = "SELECT CommentID, UID as UserID, ImageID, DateComment, Comment " + "FROM comment WHERE imageid=?";
		ArrayList<Comment> comments =  getForList(sql, iid);
		
		comments.sort(new Comparator<Comment>() {
			@Override
			public int compare(Comment c1, Comment c2) {
				return c2.getAgreements() - c1.getAgreements();
			}
		});
		
		//
		return comments;
	}
	
	/**
	 * table: comment
	 * 得到uid的所有评论
	 * @param iid
	 * @return
	 */
	public ArrayList<Comment> getCommentsByUID(int uid)  {
		return null;
	}
	
	

	/**
	 * 添加评论
	 * @param uid
	 * @param iid
	 * @param comment
	 * @return
	 * 得到主键
	 * -1 插入失败
	 */
	public int sendCommentAndGetKey(int uid, int iid, String comment) {
		String sql = "INSERT INTO `comment`(`CommentID`, `UID`, `ImageID`, `Comment`)  " + "VALUES(0,?,?,?)";
		// dateComment采用默认值

		return getKeyAfterInsert(sql, uid, iid, comment);
	}
	
	
	
	// ===================================================table agreementNumber分界线=====================================================

	
	/**
	 * 为了显示评论的点赞class
	 * @param iid
	 * @param uid
	 * @return
	 * 返回uid iid 的commentid数组
	 */
	public ArrayList<Integer> getMyAgreementByImageIDAndUserID(int iid,int uid) {
		String sql = "SELECT A.commentID FROM commentagree as A, comment as C WHERE A.commentID=C.commentID AND A.uid=? AND C.imageID=?";
		return getForValues(Integer.class, sql, uid,iid);
		
	}
	
	
	
	
	
	
	/**
	 * 得到comment 的赞数
	 * @param commentId
	 * @return
	 */
	public int getAgreeNumberByCommentID(int commentId) {
		int agreementNumber = 0;
		String sql = "SELECT COUNT(AgreeID) AS agreementNumber FROM commentagree "
				+ " WHERE CommentID=? GROUP BY CommentID ORDER BY agreementNumber";
		Long temp = getForValue(Long.class, sql, commentId);
		if(temp != null)
			agreementNumber = temp.intValue();
		return agreementNumber;
	}
	
	
	/**
	 * doAgree.do操作的主要执行
	 * 传入 uid cid  自行判断添加//删除
	 * 
	 * @param uid
	 * @param commentid
	 * 
	 *  table commentagree AgreeID`, `UID`, `CommentID
	 * 
	 * @return 操作状态
	 * 			-1 / 0 / 1 
	 * 		失败 / 失败 / 成功
	 */
	public int doAgreementByUidImageID(int uid, int commentID) {
		int agreeState = 0;
		int doState = 0;
		System.out.println("============================doAgree========================");
		System.out.println("\t--> Before daAgree");
		if (!isAgreeByUidImageID(uid, commentID)) {
			//不同意，添加agree
			addAgree(uid, commentID);
			System.out.println("\t--> 操作 add Agree");

			if (isAgreeByUidImageID(uid, commentID)) {
				// 同
				agreeState = 1;
				System.out.println("\t--> add agree 成功 ");
				return 1;
			} else {
				// 还是不同
				agreeState = -1;
				System.out. println("add agree 失败 ");
				return -1;
			}
		} else if (isAgreeByUidImageID(uid, commentID)) {
			// 同意
			deleteAgree(uid, commentID);
			System.out.println("\t--> 操作 delete Agree");
			if (isAgreeByUidImageID(uid, commentID)) {
				// 还是同
				agreeState = 1;
				System.out.println("\t--> delete Agree 失败");
				return -1;
			} else {
				// 不
				agreeState = -1;
				System.out.println("\t--> delete agree 成功");
				return 1;
			}

		}
		System.out.println("无操作");
		return 0;
	}

	/**
	 * comment 是否被 uid 赞同
	 * @param uid
	 * @param commentID
	 * @return
	 * 
	 * 用于doAgree 方法、 doAgree.do 
	 * ajax 中返回 isAgree 
	 */
	public boolean isAgreeByUidImageID(int uid, int commentID) {
		String sql = "SELECT * FROM commentagree WHERE uid=? AND commentid=?";

		int count = getRowCount(sql, uid, commentID);
		if (count > 0) {
			System.out.println("			agree");
			return true;

		} else {
			System.out.println("			disagree");
			return false;
		}
	}

	/** 添加/删除agree记录，仅被方法 doAgreement() 调用
	 * @param uid
	 * @param commentID
	 */
	public void addAgree(int uid, int commentID) {
		String sql = "INSERT INTO `commentagree`(`AgreeID`, `UID`, `CommentID`) VALUES(0,?,?)";
		update(sql, uid, commentID);
	}

	public void deleteAgree(int uid, int commentID) {
		String sql = "DELETE FROM `commentagree` WHERE uid=? AND commentID=?";
		update(sql, uid, commentID);
	}
}
