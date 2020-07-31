package com.DAO;

import com.Model.TravelUser;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TravelUserDAO extends DAO<TravelUser> {

	public static TravelUserDAO travelUserDAO = null;
	private final String SQL_FOR_ALL_SELECT = "SELECT Uid as UserID, Username, Email, Pass, DateJoined, State  ";

	public static void main(String[]strings) {
		TravelUserDAO travelUserDAO = TravelUserDAO.getTravelUserDAO();
		travelUserDAO.changeStateByUid(1);
		
	}
	public TravelUserDAO() {
		super(TravelUser.class);
		travelUserDAO = this;
	}

	public static TravelUserDAO getTravelUserDAO() {
		if (travelUserDAO == null)
			return new TravelUserDAO();
		else
			return travelUserDAO;
	}

	public ArrayList<TravelUser> getTravelUserSearchByUserNameOrEmail(String searchInfo) {
		String sql = SQL_FOR_ALL_SELECT + "FROM traveluser " + "WHERE username LIKE ? OR email LIKE ?";
		// info%表示前缀匹配？
		return getForList(sql, searchInfo + "%", searchInfo + "%");

	}

	public int changeStateByUid(int uid) {
		String sql = "SELECT state FROM traveluser WHERE UID=?";
		int showState = getForValue(Integer.class, sql, uid);

		if (showState == 1) {
			System.out.println("trveluserDAO==> 关闭收藏显示");
			String sql2 = "UPDATE traveluser SET state=0 WHERE UID=?";
			update(sql2, uid);

			if (getForValue(Integer.class, sql, uid) == 0) {
				System.out.println("trveluserDAO==> 成功关闭收藏显示");
				return 0;
			} else {
				System.out.println("trveluserDAO==> 关闭收藏显示失败");
				return 1;
			}
		} else {
			System.out.println("trveluserDAO==> 开启收藏显示");
			String sql2 = "UPDATE traveluser SET state=1 WHERE UID=?";
			update(sql2, uid);

			if (getForValue(Integer.class, sql, uid) == 1) {
				System.out.println("trveluserDAO==> 开启收藏显示成功");
				return 1;
			} else {
				System.out.println("trveluserDAO==> 開啓收藏显示失败");
				return 0;
			}
		}
	}

	public void addTraveluser(String username, String pass, String email) {
		String sql = "Insert INTO traveluser (`UID`, `Email`, `UserName`, `Pass`,`Salt`, `Vector`,  `State`, `DateJoined`, `DateLastModified`) VALUES (0, ?, ?, ?, ?, ?, 1, ?, ?)";
		pass = AESEncrypt.getEncrypt(pass);
		String salt = AESEncrypt.getSaltKey();
		String vector = AESEncrypt.getVectorKey();

		java.sql.Timestamp newDate = new java.sql.Timestamp(new java.util.Date().getTime());
		update(sql, email, username, pass, salt, vector, newDate, newDate);

	}

	public void deleteTravelDAO(int uid) {

	}

	public void logoutUpdateDateLastModifiedByUid(int uid) {
		Timestamp newDate = new Timestamp(new Date().getTime());
		String sql = "UPDATE traveluser SET `DateLastModified`=? WHERE uid=? ";
		update(sql, newDate, uid);
	}

	public TravelUser getByUsernameOrEmail(String username) {
		String sql = SQL_FOR_ALL_SELECT + " FROM traveluser WHERE username=? OR email=?";
		return get(sql, username, username);
	}

	public TravelUser getByUsername(String username) {
		String sql = SQL_FOR_ALL_SELECT + " FROM traveluser  WHERE username=?";
		return get(sql, username);
	}

	public TravelUser getByUid(int uid) {
		String sql = SQL_FOR_ALL_SELECT + " FROM traveluser WHERE uid=?";
		return get(sql, uid);
	}

	public Map<Integer, String> getKeysByUsername(String username) {
		Map<Integer, String> keyMap = new HashMap<Integer, String>();

		String sql = "SELECT Salt FROM traveluser WHERE username=? OR email=?";
		String saltKey = getForValue(String.class, sql, username, username);
		keyMap.put(0, saltKey);

		sql = "SELECT Vector FROM traveluser WHERE username=? OR email=?";
		String vectorKey = getForValue(String.class, sql, username, username);
		keyMap.put(1, vectorKey);
		return keyMap;
	}

}
