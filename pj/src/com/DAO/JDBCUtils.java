package com.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.junit.jupiter.api.Test;

import com.mysql.jdbc.PreparedStatement;

/*
 * 操作JBDC的工具类
 * 封装工具方法
 *  ver1.0
 * */
public class JDBCUtils {
	private static Connection onlyConnection = null;

	/*
	 * 釋放資源
	 */
	public static void release(Statement statement, Connection connection) {
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/*
	 * 釋放資源
	 */
	public static void release(Statement statement, Connection connection, ResultSet resultSet) {
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 1. 获取连接的方法
	 * 
	 */
	public static Connection getConntection() {
//		try {
			//if (onlyConnection == null || onlyConnection.isClosed()) {
				Connection connection = null;

				String driver = "com.mysql.cj.jdbc.Driver";
				String jdbcUrl = "jdbc:mysql://localhost:3306/test?characterEncoding=utf-8&serverTimezone=UTC&useSSL=false&allowMultiQueries=true";
				String user = "root";
				String password = "123456";
				try {
					Class.forName(driver);
					// 若没有将jar包加入build path 则找不到驱动
					//System.out.println(Class.forName("com.mysql.jdbc.Driver"));
				} catch (ClassNotFoundException e) {
					System.out.println("No mySql JDBC found");
					e.printStackTrace();
				}
				
				try {
					connection = DriverManager.getConnection(jdbcUrl, user, password);
//				} catch (Exception e) {
//					System.out.println("No mySql Connection found");
//					e.printStackTrace();
//				}
				
				//onlyConnection = connection;
			//}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;
	}

	@Test
	public void testGetConnection() throws Exception {
		System.out.println(getConntection());
	}

	public static String getHTML(int iid, String username, String path, String description, String title) {
		String str = "";

		str += "<div class=\"col-xs-12\">  \n	<article class=\"left\" id='" + iid + "'> \n"
				+ "\n<div class=\"image_container\">\n";
		System.out.println("   ==>" + str);

		str += "<a href=\"./details.php?id=" + iid + "\" data-toggle='tooltip' data-placement='right' "
				+ "title='ImageID: " + iid + " | via " + username + "'>\n";
		System.out.println("   ==>" + str);

		//
		str += "<img src='./travel-images/square-medium/" + path + "' class=\"image_self\"></a>\n";
		str += "</div><div class=\"image_description\">\n";

		str += "<h2>" + title + "</h2>\n<p>" + description + "</p>\n";
		System.out.println("   ==>" + str);

		str += "<form method='POST'>";
		str += "<input type=\"type\" hidden='hidden' name='iid' value='" + iid + "'>\n";
		str += "<input type=\"submit\" name='delete' class=\"btDelete red btn btn-default\" value=\"Delete\">\n";
		str += "</form>\n";

		str += "</div></article></div>\n";
		System.out.println("   ==>" + str);

		return str;
	}

	public static PreparedStatement updatePreparedStatement(PreparedStatement preparedStatement, Object... args) {
		try {
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}
}
