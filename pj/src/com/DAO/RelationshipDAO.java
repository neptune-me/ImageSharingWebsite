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
	 * �������Ѻ���
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
	 * ���ܺ����������
	 * 
	 * @param toUID
	 * @param fromUID
	 * @return
	 * 
	 * 		1/�����ɹ� 0/����ʧ��
	 */
	public int receiveRequestOfMakingFriends(int toUID, int fromUID) {
		
		
		int toAsUser1 = judgeRelationBetweenUser1AndUser2(toUID, fromUID); // 0
		int fromAsUser1 = judgeRelationBetweenUser1AndUser2(fromUID, toUID); // -1
		System.out.println("relationshipDAO ==> �������l���^Ո���?" + toAsUser1);
		System.out.println("relationshipDAO ==> �����Ұl���^Ո���?" + fromAsUser1);
		if (fromAsUser1 == -1 && toAsUser1 == 0) {
			System.out.println("relationshipDAO ==> �H��һ�l��ԃ�Y��from others to me");
			String sql = "UPDATE userrelationship SET RelationState=1 WHERE user1=? AND user2=?";
			update(sql, fromUID, toUID);
			System.out.println("relationshipDAO ==> �ҽ��������ĺ���Ո��");
			return 1;
		} else {
			System.out.println("relationshipDAO ==> ���ߵ��P�S�^���}�s������̎��");
			return 0;
		}
	}

	/**
	 * �ܾ����� = ɾ����������
	 * 
	 * @param toUID
	 * @param fromUID
	 * @return
	 * 
	 * 		1/ɾ���ɹ� 0/û�в���
	 */
	public int refuseRequestOfMakingFriends(int toUID, int fromUID) {
		if (toUID == fromUID) {
			System.out.println("relationshipDAO ==> �Լ����Լ��l�͵ĺ���Ո��");
			String sql = "DELETE FROM userrelationship WHERE user1=? AND user2=? AND RelationState=0 ";
			update(sql, fromUID, toUID);
			System.out.println("relationshipDAO ==> ��ɾ��������");

		}

		int toAsUser1 = judgeRelationBetweenUser1AndUser2(toUID, fromUID); // 0
		int fromAsUser1 = judgeRelationBetweenUser1AndUser2(fromUID, toUID); // -1
		System.out.println("relationshipDAO ==> �������l���^Ո���?" + toAsUser1);
		System.out.println("relationshipDAO ==> �����Ұl���^Ո���?" + fromAsUser1);
		if (fromAsUser1 == -1 && toAsUser1 == 0) {
			System.out.println("relationshipDAO ==> �H��һ�l��ԃ�Y��from others to me");
			String sql = "DELETE FROM userrelationship WHERE user1=? AND user2=? AND RelationState=0 ";
			update(sql, fromUID, toUID);
			System.out.println("relationshipDAO ==> �Ҿܾ������ĺ���Ո�󣬲�ɾ��������");
			return 1;
		} else {
			System.out.println("relationshipDAO ==> ���ߵ��P�S�^���}�s������̎��");
			return 0;
		}
	}

	/**
	 * @param fromUID
	 * @param toUID   from localhost �򌦷��l�ͺ���Ո��
	 * 
	 *                �{�� judgeRelationBetweenUser1AndUser2(), hasSent(), isFriend()
	 *                addRelationShip(), deleteRelationshipById()
	 *                getRelationshipIDByU1AndU2()
	 * @return 0 / 1 ������ �Ǻ���/�������
	 */
	public int sendRequestForMakeFriends(int fromUID, int toUID) {
		int fromAsUser1 = judgeRelationBetweenUser1AndUser2(fromUID, toUID);
		int toAsUser1 = judgeRelationBetweenUser1AndUser2(toUID, fromUID);

		// ������ʶ�����Է���
		if (!hasSent(fromUID, toUID)) {
			addRelationShip(fromUID, toUID);
			System.out.println("relationshipDAO ==> ����Ո��l���ꮅ");
			return 1;
		}
		// ����ѽ��ܣ����ܷ���
		if (isFriend(fromUID, toUID)) {
			System.out.println("relationshipDAO ==> �Ѿ��Ǻ��ѣ�");
			return 0; // 0
		}
		// �Ȳ��ǻ������R��Ҳ���Ǻ���
		// ɾ��fromUID���͵�relation��¼
		if (fromAsUser1 == -1)
			deleteRelationshipById(getRelationshipIDByU1AndU2(fromUID, toUID));
		// ɾ��toUID���͵�relation��¼
		if (toAsUser1 == -1)
			deleteRelationshipById(getRelationshipIDByU1AndU2(toUID, fromUID));
		System.out.println("relationshipDAO ==> �h��������Ո��ӛ�");

		addRelationShip(fromUID, toUID);
		System.out.println("relationshipDAO ==> ����Ո��l���ꮅ");
		return 1;
	}

	/**
	 * @param uid ���ҵ����� `RelationshipID`, `User1`, `User2`, `RelationState`
	 *            userrelationship �{�� getFriendsIdByUid()
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
	 * @param uid ��relationship��������uid1 = ? arraylist1 uid2 = ? arraylist2 a1+a2
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
	 * ��һ���������͹���
	 * 
	 * @param user1ID
	 * @param user2ID
	 * @return false 0&0 ��ȫ����ʶ ˫����û�з��� true -1&0 / 0&-1 ��һ�����ͣ������Ǻ���
	 * 
	 */
	public boolean hasSent(int user1ID, int user2ID) {
		int re1 = judgeRelationBetweenUser1AndUser2(user1ID, user2ID);
		int re2 = judgeRelationBetweenUser1AndUser2(user2ID, user1ID);

		return !(re1 == 0 && re2 == 0); // u1 u2��û����Է����ͼ�¼
	}

	/**
	 * @param fromUID
	 * @param toUID
	 * @return 0 ������u1δ���� -1 ���Ѻã�u1���͹���u2δ���� 1 �Ѻ�, ������u1�����ĺ���
	 */
	public int judgeRelationBetweenUser1AndUser2(int fromUID, int toUID) {
		// ������u1δ����
		int relation = 0;

		String sql = "SELECT * FROM userrelationship WHERE user1=? AND user2=? AND RelationState=0";
		int cou1 = getRowCount(sql, fromUID, toUID);
		// ���Ѻã���>0 ��u1���͹���u2δ����
		if (cou1 > 0)
			relation = -1;

		sql = "SELECT * FROM userrelationship WHERE user1=? AND user2=? AND RelationState=1";
		cou1 = getRowCount(sql, fromUID, toUID);
		// �Ѻã���>0 �������u1�����ĺ���
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
