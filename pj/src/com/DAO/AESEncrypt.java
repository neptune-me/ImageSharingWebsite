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
 * ���ʹ��
 * db
 * 1.traveluser�ж����� saltKey��vectorKey 
 * 2. addTravelUsr��Ҫ��д
 * 3.д����������е������ȼ���
 * 4.register��д����Ӽ���/��key
 * 5.login����д�����ȡkey�ͼ��ܺ����-����-�ȶ�
 * 
 * 
 * register
 * ���ܲ���д����addTravelUser����
 * 1.pass����
 * 2.����pass salt vector
 * 
 * login
 * ���ܲ��֣�д��travelUser��getPass����
 * 1.��֪username��ȡ���ܵ�pass
 * 2.ȡsaltKey��vectorKey
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
			System.out.println("-->��������" + encrypt);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("����ʧ��");
		}
		return encrypt;

	}

	public static String getDecrypt(String passEncrypted) {
		String pass = passEncrypted;
		try {
			pass = decrypt(passEncrypted, saltKey, vectorKey);
			System.out.println("-->��������" + pass);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("����ʧ��");
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

		// ԭ�����ӣ�https://blog.csdn.net/KH717586350/article/details/78866367
	}

	private static String encrypt(String content, String saltKey, String vectorKey) throws Exception {
		// ���������
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// �ɶ�����String��Կ�ַ������ɵ���Կ��
		SecretKey secretKey = new SecretKeySpec(saltKey.getBytes(), "AES");
		// �ɶ�����String����key���ɵĳ�ʼ��������
		IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
		// ����Կ�ͳ�ʼ����������ʼ��������
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		// �����Ƽ��ܽ��
		byte[] encrypted = cipher.doFinal(content.getBytes());
		// ��base64���������������ʽ�ļ��ܽ��ΪString

		return Base64.encodeBase64String(encrypted);
	}

	private static String decrypt(String base64Content, String slatKey, String vectorKey) throws Exception {
		// ���������
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// �ɶ�����String��Կ�ַ������ɵ���Կ��
		SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
		// �ɶ�����String����key���ɵĳ�ʼ��������
		IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
		// ����Կ�ͳ�ʼ����������ʼ��������
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		// ��String��ɶ����Ƶ���Ҫ���ܵĶ���������
		byte[] content = Base64.decodeBase64(base64Content);
		// ʹ�ý������õ������ƽ��ܽ��
		byte[] encrypted = cipher.doFinal(content);
		// �ö�����������������
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
	 * base64����ͽ���
	 * 
	 */
	public static String decode(byte[] bytes) {
		return new String(Base64.decodeBase64(bytes));
	}

	// base64 ����
	public static String encode(byte[] bytes) {
		return new String(Base64.encodeBase64(bytes));
	}

}
