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
	 * �õ�iid����������
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
	 * �õ�uid����������
	 * @param iid
	 * @return
	 */
	public ArrayList<Comment> getCommentsByUID(int uid)  {
		return null;
	}
	
	

	/**
	 * �������
	 * @param uid
	 * @param iid
	 * @param comment
	 * @return
	 * �õ�����
	 * -1 ����ʧ��
	 */
	public int sendCommentAndGetKey(int uid, int iid, String comment) {
		String sql = "INSERT INTO `comment`(`CommentID`, `UID`, `ImageID`, `Comment`)  " + "VALUES(0,?,?,?)";
		// dateComment����Ĭ��ֵ

		return getKeyAfterInsert(sql, uid, iid, comment);
	}
	
	
	
	// ===================================================table agreementNumber�ֽ���=====================================================

	
	/**
	 * Ϊ����ʾ���۵ĵ���class
	 * @param iid
	 * @param uid
	 * @return
	 * ����uid iid ��commentid����
	 */
	public ArrayList<Integer> getMyAgreementByImageIDAndUserID(int iid,int uid) {
		String sql = "SELECT A.commentID FROM commentagree as A, comment as C WHERE A.commentID=C.commentID AND A.uid=? AND C.imageID=?";
		return getForValues(Integer.class, sql, uid,iid);
		
	}
	
	
	
	
	
	
	/**
	 * �õ�comment ������
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
	 * doAgree.do��������Ҫִ��
	 * ���� uid cid  �����ж����//ɾ��
	 * 
	 * @param uid
	 * @param commentid
	 * 
	 *  table commentagree AgreeID`, `UID`, `CommentID
	 * 
	 * @return ����״̬
	 * 			-1 / 0 / 1 
	 * 		ʧ�� / ʧ�� / �ɹ�
	 */
	public int doAgreementByUidImageID(int uid, int commentID) {
		int agreeState = 0;
		int doState = 0;
		System.out.println("============================doAgree========================");
		System.out.println("\t--> Before daAgree");
		if (!isAgreeByUidImageID(uid, commentID)) {
			//��ͬ�⣬���agree
			addAgree(uid, commentID);
			System.out.println("\t--> ���� add Agree");

			if (isAgreeByUidImageID(uid, commentID)) {
				// ͬ
				agreeState = 1;
				System.out.println("\t--> add agree �ɹ� ");
				return 1;
			} else {
				// ���ǲ�ͬ
				agreeState = -1;
				System.out. println("add agree ʧ�� ");
				return -1;
			}
		} else if (isAgreeByUidImageID(uid, commentID)) {
			// ͬ��
			deleteAgree(uid, commentID);
			System.out.println("\t--> ���� delete Agree");
			if (isAgreeByUidImageID(uid, commentID)) {
				// ����ͬ
				agreeState = 1;
				System.out.println("\t--> delete Agree ʧ��");
				return -1;
			} else {
				// ��
				agreeState = -1;
				System.out.println("\t--> delete agree �ɹ�");
				return 1;
			}

		}
		System.out.println("�޲���");
		return 0;
	}

	/**
	 * comment �Ƿ� uid ��ͬ
	 * @param uid
	 * @param commentID
	 * @return
	 * 
	 * ����doAgree ������ doAgree.do 
	 * ajax �з��� isAgree 
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

	/** ���/ɾ��agree��¼���������� doAgreement() ����
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
