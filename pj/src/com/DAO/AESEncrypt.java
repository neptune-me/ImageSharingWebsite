package com.DAO;

import java.security.SecureRandom;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

import com.Model.TravelUser;

/*
 * 如何使用
 * db
 * 1.traveluser中多两列 saltKey和vectorKey 
 * 2. addTravelUsr需要改写
 * 3.写个程序把所有的密码先加密
 * 4.register改写，添加加密/存key
 * 5.login重新写，添加取key和加密后的文-加密-比对
 * 
 * 
 * register
 * 加密部分写在了addTravelUser里面
 * 1.pass加密
 * 2.存入pass salt vector
 * 
 * login
 * 解密部分，写在travelUser的getPass里面
 * 1.已知username，取加密的pass
 * 2.取saltKey和vectorKey
 * 3.loading
 * */
public class AESEncrypt {

	private static String saltKey;
	private static String vectorKey;

	public static String getEncrypt(String pass) {
		if (saltKey == null) {
			generateKey();
		}
		String encrypt = pass;
		try {
			encrypt = encrypt(pass, saltKey, vectorKey);
			System.out.println("-->编码结果：" + encrypt);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("编码失败");
		}
		return encrypt;

	}

	public static String getDecrypt(String passEncrypted) {
		String pass = passEncrypted;
		try {
			pass = decrypt(passEncrypted, saltKey, vectorKey);
			System.out.println("-->解码结果：" + pass);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解码失败");
		}
		return pass;

	}

	public static void generateKey() {
		byte bytes[] = new byte[24];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);

		saltKey = Base64.encodeBase64String(bytes).substring(0, 16);

		random.nextBytes(bytes);
		vectorKey = Base64.encodeBase64String(bytes).substring(0, 16);

		System.out.println("generating-->salt key: " + saltKey);
		System.out.println("generating-->vector key: " + vectorKey);
	}

	public static void loadKey(String saltKey, String vectorKey) {
		setSaltKey(saltKey);
		setVectorKey(vectorKey);

		System.out.println("loading-->salt key: " + saltKey);
		System.out.println("loading-->vector key: " + vectorKey);
	}

	public static void main(String[] args) {

		TravelUserDAO travelUserDAO = TravelUserDAO.getTravelUserDAO();

		// saltKey;
		// vectorKey;
		String sql = "SELECT Username,Pass FROM traveluser ";

		System.out.println("---------------------------------");
		ArrayList<TravelUser> travelUsers = travelUserDAO.getForList(sql);
//		for (int i = 0; i < travelUsers.size(); i++) {
//			String username = travelUsers.get(i).getUsername();
//			String pass = travelUsers.get(i).getPass();
//
//			generateKey();
//			String encryptPass = getEncrypt(pass);
//
//			String sql2 = "UPDATE `traveluser` " + "SET pass=? , " + "Salt =? ," + "Vector = ? " + "WHERE username=?";
//			travelUserDAO.update(sql2, encryptPass, saltKey, vectorKey, username);
//			System.out.println("> " + username + ": " + encryptPass + ";" + saltKey + ";" + vectorKey);
//		}

		//
//		generateKey();
//
//		String pass = "qwerty";
//		System.out.println("-->pass: " + pass);
//		try {
//			String AESPass = encrypt(pass, AESEncrypt.saltKey, AESEncrypt.vectorKey);
//			System.out.println("-->encoded: " + AESPass);
//
//			String decoded = decrypt(AESPass, AESEncrypt.saltKey, AESEncrypt.vectorKey);
//			System.out.println("-->decoded: " + decoded);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		// 原文链接：https://blog.csdn.net/KH717586350/article/details/78866367
	}

	private static String encrypt(String content, String saltKey, String vectorKey) throws Exception {
		// 密码解释器
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// 由二进制String密钥字符串生成的密钥类
		SecretKey secretKey = new SecretKeySpec(saltKey.getBytes(), "AES");
		// 由二进制String向量key生成的初始化向量类
		IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
		// 用密钥和初始化向量，初始化加密器
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		// 二进制加密结果
		byte[] encrypted = cipher.doFinal(content.getBytes());
		// 用base64整理二进制数组形式的加密结果为String

		return Base64.encodeBase64String(encrypted);
	}

	private static String decrypt(String base64Content, String slatKey, String vectorKey) throws Exception {
		// 密码解释器
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// 由二进制String密钥字符串生成的密钥类
		SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
		// 由二进制String向量key生成的初始化向量类
		IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
		// 用密钥和初始化向量，初始化解密器
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		// 将String变成二进制的需要解密的二进制数组
		byte[] content = Base64.decodeBase64(base64Content);
		// 使用解密器得到二进制解密结果
		byte[] encrypted = cipher.doFinal(content);
		// 用二进制数组生成明文
		return new String(encrypted);
	}

	/*
	 * getters setters
	 */

	public static String getSaltKey() {
		return saltKey;
	}

	public static void setSaltKey(String saltKey) {
		AESEncrypt.saltKey = saltKey;
	}

	public static String getVectorKey() {
		return vectorKey;
	}

	public static void setVectorKey(String vectorKey) {
		AESEncrypt.vectorKey = vectorKey;
	}

	/*
	 * base64编码和解码
	 * 
	 */
	public static String decode(byte[] bytes) {
		return new String(Base64.decodeBase64(bytes));
	}

	// base64 编码
	public static String encode(byte[] bytes) {
		return new String(Base64.encodeBase64(bytes));
	}

}
