package com.DAO;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.Model.*;

public class DAO<T> {

	private Class<T> clazz = null;

	public static void main(String[] args) {
		System.out.println("main function ");
		DAO<TravelUser> userDAO = new DAO<TravelUser>(TravelUser.class);

	}

	public DAO() {
	}

	public DAO(Class clazz) {
		this.clazz = clazz;
		System.out.println("\n==========New DAO==============\n");
	}

	public void update(String sql, Object... args) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int queryCount = -1;
		try {
			connection = JDBCUtils.getConntection();
			preparedStatement = connection.prepareStatement(sql);

			for (int i = 0; i < args.length; i++)
				preparedStatement.setObject(i + 1, args[i]);

			queryCount = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(preparedStatement, connection);
		}

		System.out.println("成功更新了 " + queryCount + " 条记录。");

	}

	public int getKeyAfterInsert(String sql, Object... args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Long idLength = (long) -1;

		try {
			connection = JDBCUtils.getConntection();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			for (int i = 0; i < args.length; i++)
				preparedStatement.setObject(i + 1, args[i]);

			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				idLength = resultSet.getLong(1);
				System.out.println("主键：" + idLength);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(preparedStatement, connection, resultSet);
		}

		return idLength.intValue();
	}

	/*
	 * 返回一个实例对象
	 */
	public T get(String sql, Object... args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		T instance = null;

		System.out.println("返回1*实例对象\n");
		try {
			connection = JDBCUtils.getConntection();
			preparedStatement = connection.prepareStatement(sql);

			for (int i = 0; i < args.length; i++)
				preparedStatement.setObject(i + 1, args[i]);

			resultSet = preparedStatement.executeQuery();

//			System.out.println();
			if (resultSet.next()) {
				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

				instance = clazz.newInstance();
				for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
					String columnLabel = resultSetMetaData.getColumnLabel(i + 1);
					Object value = resultSet.getObject(columnLabel);

					// reflexion
					String methodName = "set" + columnLabel;

					Method method = clazz.getDeclaredMethod(methodName, Object.class);

//					System.out.print(columnLabel + "--> " + value + "\n");
					method.invoke(instance, value);
//					System.out.println(instance.toString());

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(preparedStatement, connection, resultSet);
		}

		return instance;
	}

	/*
	 * 获取T组成的list
	 */
	public ArrayList<T> getForList(String sql, Object... args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<T> entities = new ArrayList<>();

		try {
			//
			connection = JDBCUtils.getConntection();
			preparedStatement = connection.prepareStatement(sql);

			// execute
			for (int i = 0; i < args.length; i++)
				preparedStatement.setObject(i + 1, args[i]);
			System.out.println(preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();

//			Map<String,Object> map = new HashMap<String, Object>();

			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int colNumber = resultSetMetaData.getColumnCount();
			while (resultSet.next()) {
				T instance = clazz.newInstance();

				for (int i = 0; i < colNumber; i++) {
					String columnLabel = resultSetMetaData.getColumnLabel(i + 1);
					Object value = resultSet.getObject(columnLabel);
					// 反射
					String methodName = "set" + columnLabel;

					Method method = clazz.getDeclaredMethod(methodName, Object.class);
					// System.out.println(methodName+": "+value);
					method.invoke(instance, value);

				}
				entities.add(instance);
			}

			System.out.println();

			// for (T x : entities)
			// System.out.println(x.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(preparedStatement, connection, resultSet);
		}

		return entities;
	}

	/*
	 * 单一的值
	 */
	public <E> E getForValue(Class<E> classE, String sql, Object... args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Object entity = null;

		try {
			//
			connection = JDBCUtils.getConntection();
			preparedStatement = connection.prepareStatement(sql);

			// execute
			for (int i = 0; i < args.length; i++)
				preparedStatement.setObject(i + 1, args[i]);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				entity = resultSet.getObject(1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(preparedStatement, connection, resultSet);
		}

		return (E) entity;
	}

	/*
	 * 单一列的多个值？
	 */
	public <E> ArrayList<E> getForValues(Class<E> classE, String sql, Object... args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<E> es = new ArrayList<>();
		Object entity = null;

		try {
			//
			connection = JDBCUtils.getConntection();
			preparedStatement = connection.prepareStatement(sql);

			// execute
			for (int i = 0; i < args.length; i++)
				preparedStatement.setObject(i + 1, args[i]);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				entity = resultSet.getObject(1);

				es.add((E) entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(preparedStatement, connection, resultSet);
		}
		return es;
	}

	public <K, V> Map<K, V> getForValues(Class<K> classK, Class<V> classV, String sql, Object... args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		Map<K, V> map = new LinkedHashMap<K, V>();

		try {
			//
			connection = JDBCUtils.getConntection();
			preparedStatement = connection.prepareStatement(sql);

			// execute
			for (int i = 0; i < args.length; i++)
				preparedStatement.setObject(i + 1, args[i]);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Object entityK = resultSet.getObject(1);// col-1
				Object entityV = resultSet.getObject(2);// col-2
				// System.out.println("-->"+entityK+": "+entityV);
				map.put((K) entityK, (V) entityV);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(preparedStatement, connection, resultSet);
		}

		return map;
	}

	public int getRowCount(String sql, Object... args) {
		int count = -1;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			//
			connection = JDBCUtils.getConntection();
			preparedStatement = connection.prepareStatement(sql);

			// execute
			for (int i = 0; i < args.length; i++)
				preparedStatement.setObject(i + 1, args[i]);
			resultSet = preparedStatement.executeQuery();

			resultSet.last();
			count = resultSet.getRow();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(preparedStatement, connection, resultSet);
		}
		String argstr = " && ";
		for (int i = 0; i < args.length; i++)
			argstr += args[i];
		System.out.println("--> 查询结果有" + count + "项！/n" + argstr);

		return count;
	}

	/*
	 * Tests
	 */
	@Test
	public void testGet() {
		DAO<TravelUser> userDAO = new DAO<TravelUser>(TravelUser.class);
		userDAO.get("SELECT uid as UserID,Username, Email FROM traveluser WHERE uid=?", 29);
	}

	@Test
	public void testUpdate() {
		DAO<TravelUser> userDAO = new DAO<TravelUser>(TravelUser.class);
		java.sql.Date sqlDate = new Date(new java.util.Date().getTime());
		userDAO.update(
				"Insert INTO traveluser (`UID`, `Email`, `UserName`, `Pass`, `DateJoined`, `DateLastModified`) "
						+ "VALUES (0, ?, ?, ?, ?, ?)",
				"qwertylff@outlook.com", "qwertylff2", "pass1234", sqlDate, sqlDate);
	}

	@Test
	public void testGetForList() {
		DAO<TravelUser> userDAO = new DAO<TravelUser>(TravelUser.class);
		userDAO.getForList("SELECT uid as UserID, Username, Email FROM traveluser ");
	}

}
