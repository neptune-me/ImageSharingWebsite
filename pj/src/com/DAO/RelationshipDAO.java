package com.DAO;

import java.util.ArrayList;
import java.util.Iterator;

import com.Model.TravelUser;

public class RelationshipDAO extends DAO<TravelUser> {

	public static RelationshipDAO relationshipDAO = null;
	private final String SQL_FOR_ALL_SELECT = "SELECT Uid as UserID, Username, Email, Pass, DateJoined ";

	/**
	 * userrelationship `RelationshipID`, `User1`, `User2`, `RelationState`
	 * 
	 * @param aStrings
	 */
	public static void main(String[] aStrings) {
		RelationshipDAO relationshipDAO = getRelationshipDAO();

		for(int i = 2; i <30 ; i++)
		relationshipDAO.sendRequestForMakeFriends(i, 1);
		//relationshipDAO.receiveRequestOfMakingFriends(21, 2);
	}

	public RelationshipDAO() {
		super(TravelUser.class);
		relationshipDAO = this;
	}

	public static RelationshipDAO getRelationshipDAO() {
		if (relationshipDAO == null)
			return new RelationshipDAO();
		else
			return relationshipDAO;
	}

	/**
	 * 和找朋友很像
	 * 
	 * @param uid
	 * @return
	 */
	public ArrayList<TravelUser> getUncheckMessageByUid(int uid) {
		String sql = "SELECT user1 FROM userrelationship WHERE user2=? AND RelationState=0 ";

		ArrayList<TravelUser> users = new ArrayList<>();

		ArrayList<Integer> uids = getForValues(Integer.class, sql, uid);

		Iterator<Integer> iterator = uids.iterator();
		while (iterator.hasNext()) {
			int uidTemp = iterator.next();
			TravelUser temp = TravelUserDAO.getTravelUserDAO().getByUid(uidTemp);
			users.add(temp);

		}
		return users;

	}

	/**
	 * 接受好友添加请求
	 * 
	 * @param toUID
	 * @param fromUID
	 * @return
	 * 
	 * 		1/操作成功 0/操作失败
	 */
	public int receiveRequestOfMakingFriends(int toUID, int fromUID) {
		
		
		int toAsUser1 = judgeRelationBetweenUser1AndUser2(toUID, fromUID); // 0
		int fromAsUser1 = judgeRelationBetweenUser1AndUser2(fromUID, toUID); // -1
		System.out.println("relationshipDAO ==> 我向他發送過請求嗎?" + toAsUser1);
		System.out.println("relationshipDAO ==> 他向我發送過請求嗎?" + fromAsUser1);
		if (fromAsUser1 == -1 && toAsUser1 == 0) {
			System.out.println("relationshipDAO ==> 僅有一條查詢結果from others to me");
			String sql = "UPDATE userrelationship SET RelationState=1 WHERE user1=? AND user2=?";
			update(sql, fromUID, toUID);
			System.out.println("relationshipDAO ==> 我接受了他的好友請求");
			return 1;
		} else {
			System.out.println("relationshipDAO ==> 二者的關係較爲複雜，不予處理");
			return 0;
		}
	}

	/**
	 * 拒绝请求 = 删除请求数据
	 * 
	 * @param toUID
	 * @param fromUID
	 * @return
	 * 
	 * 		1/删除成功 0/没有操作
	 */
	public int refuseRequestOfMakingFriends(int toUID, int fromUID) {
		if (toUID == fromUID) {
			System.out.println("relationshipDAO ==> 自己對自己發送的好友請求");
			String sql = "DELETE FROM userrelationship WHERE user1=? AND user2=? AND RelationState=0 ";
			update(sql, fromUID, toUID);
			System.out.println("relationshipDAO ==> 我删除了数据");

		}

		int toAsUser1 = judgeRelationBetweenUser1AndUser2(toUID, fromUID); // 0
		int fromAsUser1 = judgeRelationBetweenUser1AndUser2(fromUID, toUID); // -1
		System.out.println("relationshipDAO ==> 我向他發送過請求嗎?" + toAsUser1);
		System.out.println("relationshipDAO ==> 他向我發送過請求嗎?" + fromAsUser1);
		if (fromAsUser1 == -1 && toAsUser1 == 0) {
			System.out.println("relationshipDAO ==> 僅有一條查詢結果from others to me");
			String sql = "DELETE FROM userrelationship WHERE user1=? AND user2=? AND RelationState=0 ";
			update(sql, fromUID, toUID);
			System.out.println("relationshipDAO ==> 我拒绝了他的好友請求，并删除了数据");
			return 1;
		} else {
			System.out.println("relationshipDAO ==> 二者的關係較爲複雜，不予處理");
			return 0;
		}
	}

	/**
	 * @param fromUID
	 * @param toUID   from localhost 向對方發送好友請求
	 * 
	 *                調用 judgeRelationBetweenUser1AndUser2(), hasSent(), isFriend()
	 *                addRelationShip(), deleteRelationshipById()
	 *                getRelationshipIDByU1AndU2()
	 * @return 0 / 1 不操作 是好友/操作完成
	 */
	public int sendRequestForMakeFriends(int fromUID, int toUID) {
		int fromAsUser1 = judgeRelationBetweenUser1AndUser2(fromUID, toUID);
		int toAsUser1 = judgeRelationBetweenUser1AndUser2(toUID, fromUID);

		// 互不相识，可以发送
		if (!hasSent(fromUID, toUID)) {
			addRelationShip(fromUID, toUID);
			System.out.println("relationshipDAO ==> 好友請求發送完畢");
			return 1;
		}
		// 如果已接受，不能发送
		if (isFriend(fromUID, toUID)) {
			System.out.println("relationshipDAO ==> 已经是好友！");
			return 0; // 0
		}
		// 既不是互不相識、也不是好友
		// 删除fromUID发送的relation记录
		if (fromAsUser1 == -1)
			deleteRelationshipById(getRelationshipIDByU1AndU2(fromUID, toUID));
		// 删除toUID发送的relation记录
		if (toAsUser1 == -1)
			deleteRelationshipById(getRelationshipIDByU1AndU2(toUID, fromUID));
		System.out.println("relationshipDAO ==> 刪除曾經的請求記錄");

		addRelationShip(fromUID, toUID);
		System.out.println("relationshipDAO ==> 好友請求發送完畢");
		return 1;
	}

	/**
	 * @param uid 找我的朋友 `RelationshipID`, `User1`, `User2`, `RelationState`
	 *            userrelationship 調用 getFriendsIdByUid()
	 * @return
	 */
	public ArrayList<TravelUser> getMyFriendsByUid(int uid) {

		ArrayList<TravelUser> users = new ArrayList<>();

		ArrayList<Integer> uids = getFriendsIdByUid(uid);
		Iterator<Integer> iterator = uids.iterator();
		while (iterator.hasNext()) {
			int uidTemp = iterator.next();
			TravelUser temp = TravelUserDAO.getTravelUserDAO().getByUid(uidTemp);
			users.add(temp);

		}

		return users;

	}

	/**
	 * @param uid 在relationship表中搜索uid1 = ? arraylist1 uid2 = ? arraylist2 a1+a2
	 * @return a3
	 */
	private ArrayList<Integer> getFriendsIdByUid(int uid) {
		String sql = "SELECT user2 FROM userrelationship WHERE relationstate=1 AND user1=?  ";
		ArrayList<Integer> a1 = getForValues(Integer.class, sql, uid);

		sql = "SELECT user1 FROM userrelationship WHERE relationstate=1 AND user2=? ";
		ArrayList<Integer> a2 = getForValues(Integer.class, sql, uid);

		for (int i = 0; i < a2.size(); i++) {
			if (!a1.contains(a2.get(i)))
				a1.add(a2.get(i));
		}

		return a1;
	}

	public void addRelationShip(int fromUID, int toUID) {
		String sql = "INSERT INTO userrelationship(`RelationshipID`, `User1`, `User2`, `RelationState`) VALUES(0,?,?,0)";
		update(sql, fromUID, toUID);
	}

	public boolean isFriend(int user1ID, int user2ID) {
		int re1 = judgeRelationBetweenUser1AndUser2(user1ID, user2ID);
		int re2 = judgeRelationBetweenUser1AndUser2(user2ID, user1ID);
		System.out.println("===> U1 to U2: " + re1);
		System.out.println("===> U2 to U1:" + re2);
		return (re1 == 1 || re2 == 1);
	}

	/**
	 * 有一方曾经发送过？
	 * 
	 * @param user1ID
	 * @param user2ID
	 * @return false 0&0 完全不认识 双方互没有发送 true -1&0 / 0&-1 有一方发送，可能是好友
	 * 
	 */
	public boolean hasSent(int user1ID, int user2ID) {
		int re1 = judgeRelationBetweenUser1AndUser2(user1ID, user2ID);
		int re2 = judgeRelationBetweenUser1AndUser2(user2ID, user1ID);

		return !(re1 == 0 && re2 == 0); // u1 u2都没有向对方发送记录
	}

	/**
	 * @param fromUID
	 * @param toUID
	 * @return 0 中立，u1未发送 -1 不友好，u1发送过且u2未接受 1 友好, 二人是u1主动的好友
	 */
	public int judgeRelationBetweenUser1AndUser2(int fromUID, int toUID) {
		// 中立，u1未发送
		int relation = 0;

		String sql = "SELECT * FROM userrelationship WHERE user1=? AND user2=? AND RelationState=0";
		int cou1 = getRowCount(sql, fromUID, toUID);
		// 不友好，若>0 则u1发送过且u2未接受
		if (cou1 > 0)
			relation = -1;

		sql = "SELECT * FROM userrelationship WHERE user1=? AND user2=? AND RelationState=1";
		cou1 = getRowCount(sql, fromUID, toUID);
		// 友好，若>0 则二人是u1主动的好友
		if (cou1 > 0)
			relation = 1;

		return relation;

	}

	public void deleteRelationshipById(int rid) {
		String sql = "DELETE FROM userrelationship WHERE RelationshipID=? ";
		update(sql, rid);
	}

	public int getRelationshipIDByU1AndU2(int user1, int user2) {
		String sql = "SELECT RelationshipID FROM userrelationship WHERE user1=? AND user2=? ";
		return getForValue(Integer.class, sql, user1, user2);
	}

}
