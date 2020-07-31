package com.DAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.Model.Image;

public class ImageDAO extends DAO<Image> {

	private final String SQL_ALL_IMAGE_SELECT = " `ImageID`, `Title`, `Description`, `Latitude`, `Longitude`,"
			+ " `CityCode`, `Country_RegionCodeISO` AS CountryCodeISO, `UID` AS Uid, `PATH` AS Path, `Content`, `DateUpload` ";

	private final String SQL_ALL_IMAGE_SELECT_LIKE = "B.ImageID, `Title`, `Description`, `Latitude`, `Longitude`, "
			+ " `CityCode`, `Country_RegionCodeISO` AS CountryCodeISO, B.UID AS Uid, B.PATH AS Path, `Content`, `DateUpload`, "
			+ " COUNT(FavorID) AS Likes " + " FROM travelimagefavor AS A, travelimage AS B ";
	public static ImageDAO imageDAO = null;

	public static void main(String[] args) {

		ImageDAO imageDAO = getImageDAO();

		// imageDAO.getAllImages(10, 1);
		// imageDAO.getAllImages(10, 2);

		// System.out.println(imageDAO.getLikeByImageID(2));
//		imageDAO.getHotImages(3);

		// UPDATE `travelimage` SET
		// `ImageID`=[value-1],`Title`=[value-2],`Description`=[value-3],`Latitude`=[value-4],`Longitude`=[value-5],`CityCode`=[value-6],`Country_RegionCodeISO`=[value-7],`UID`=[value-8],`PATH`=[value-9],`Content`=[value-10],`DateUpload`=[value-11]
		// WHERE 1

		/*
		 * Timestamp daTimestamp = new Timestamp(new Date().getTime()); String sql =
		 * "UPDATE travelimage set DateUpload=? WHERE uid=3"; imageDAO.update(sql,
		 * daTimestamp);
		 */

//		imageDAO.getLatestImages(2);

//		System.out.println(imageDAO.getByImageId(2));
//		imageDAO.doLike(5, 12);

//		imageDAO.getSearchImagesByPages("title", "", "DateUpload", "DESC", 3, 1);

//		imageDAO.getSearchImagesByPages("title", "", "Likes", "DESC", 3, 1);
		imageDAO.getSearchImagesTotalPage("title", "a", "Likes", "DESC", 6);

		// ����DESC
//		System.out.println("==================Like DESC================");
//		imageDAO.getSearchImagesByPages("title", "", "Likes", "DESC", 3, 1);
//		System.out.println("==================Like ASC================");
//		imageDAO.getSearchImagesByPages("title", "", "Likes", "ASC", 3, 1);

//		System.out.println("==================DESC sort =================");
//		imageDAO.sortByLikesDESC(imageDAO.getSearchImagesByPages("title", "", "Likes", "DESC", 3, 1));
//		System.out.println("==================ASC sort =================");
//		imageDAO.sortByLikesASC(imageDAO.getSearchImagesByPages("title", "", "Likes", "DESC", 3, 1));
	}

	public ImageDAO() {
		super(Image.class);
		imageDAO = this;
	}

	public static ImageDAO getImageDAO() {
		if (imageDAO == null)
			return new ImageDAO();
		else
			return imageDAO;
	}
	
	public void deleteImageByImageID(int iid,int uid) {
		String sql = "DELETE FROM travelimage WHERE imageid=? AND uid=? ";
		update(sql, iid, uid);
		//ҲҪdelete���еĵ��ޣ�
		sql = "DELETE FROM travelimagefavor WHERE imageid=? ";
		//ҲҪdelete���е�����
	}

	/**
	 * @param filter_condi
	 * @param input_content like '% %'
	 * @param order_way     like / time
	 * @param order         low:desc high:asc
	 * 
	 *                      search.jsp
	 * @return
	 */
	public ArrayList<Image> getSearchImagesByPages(String filter_condi, String input_content, String order_way,
			String order, int length, int page) {
		String sql = "";
		if (order_way.equals("DateUpload")) {
			sql = "SELECT " + SQL_ALL_IMAGE_SELECT + " FROM travelimage WHERE " + filter_condi
					+ " LIKE ? ORDER BY DateUpload " + order + " LIMIT ?,? ";

			// ���㿪ʼ��index
			int beginIndex = (page - 1) * length;

			return getForList(sql, "%" + input_content + "%", beginIndex, length);

		} else if (order_way.equals("Likes")) {
			sql = "SELECT " + SQL_ALL_IMAGE_SELECT + " FROM travelimage WHERE " + filter_condi + " LIKE ?";
			ArrayList<Image> images = getForList(sql, "%" + input_content + "%");
			if (order.equals("DESC"))
				sortByLikesDESC(images);
			else if (order.equals("ASC"))
				sortByLikesASC(images);

			int beginIndex = (page - 1) * length;
			int endIndex = (beginIndex + length) >= images.size() ? images.size() : beginIndex + length;
			System.out.println(images.size());
			List<Image> list = images.subList(beginIndex, endIndex);

			ArrayList<Image> images2 = new ArrayList<>();

			Iterator<Image> iterator = list.iterator();
			while (iterator.hasNext()) {
				images2.add(iterator.next());

			}
			return images2;
		}
		return new ArrayList<Image>();

	}
	
	public void modifyImage(String title, String description, int cityCode, String countryCodeISO, String content, int iid ) {
		String sql = "UPDATE `travelimage` SET Title=?, Description=?, CityCode=?, Country_RegionCodeISO=?, Content=? ,DateUpload=? "
				+ "WHERE ImageId=?";
		
		Timestamp newDate = new Timestamp(new Date().getTime());
		
		
		ImageDAO.getImageDAO().update(sql, title, description, cityCode, countryCodeISO, content,newDate, iid);

	}

	public int getSearchImagesTotalPage(String filter_condi, String input_content, String order_way, String order,
			int length) {
		// String sql = "SELECT " + SQL_ALL_IMAGE_SELECT_LIKE + "WHERE
		// A.ImageID=B.ImageID AND " + filter_condi + " LIKE ? GROUP BY ImageID ORDER BY
		// " + order_way + " " + order;

		String sql = "SELECT " + SQL_ALL_IMAGE_SELECT + "FROM travelimage WHERE " + filter_condi + " LIKE ?";

		// Ӌ�����퓴a
		int totalNumber = getRowCount(sql, "%" + input_content + "%");
		int totalPage = getTotalPage(length, totalNumber);
		System.out.println("==>totalNumber: " + totalNumber);
		return totalPage;
	}

	/**
	 * @param iid
	 * @param uid
	 * 
	 *            details.jsp
	 */
	public void doLike(int iid, int uid) {
		// ���ѽ��c�^ד
		if (isLike(iid, uid))
			deleteLikeByImageId(iid, uid);
		else
			addLikeByImageId(iid, uid);
	}

	public boolean isLike(int iid, int uid) {
		String sql = "SELECT * FROM travelimagefavor WHERE uid=? AND imageid=? ";
		int count = getRowCount(sql, uid, iid);
		return !(count == 0);
	}

	public void addLikeByImageId(int iid, int uid) {
		String sql = "INSERT INTO `travelimagefavor`(`FavorID`, `UID`, `ImageID`) VALUES (0,?,?)";
		update(sql, uid, iid);
		System.out.println("add Like");
	}

	public void deleteLikeByImageId(int iid, int uid) {
		String sql = "DELETE FROM `travelimagefavor` WHERE UID=? AND ImageID=?";
		update(sql, uid, iid);
		System.out.println("delect Like");
	}

	public Image getByImageId(int iid) {
		String sql = "SELECT " + SQL_ALL_IMAGE_SELECT + " FROM travelimage WHERE imageid=?";
		return get(sql, iid);
	}

	public ArrayList<Image> getAllImages() {
		// ������������
		String sqlForRowNumber = "SELECT * FROM travelimage WHERE Path IS NOT NULL";
		int totalNumber = getRowCount(sqlForRowNumber);

		return getAllImages(totalNumber, 1);
	}

	/**
	 * @param length һҳ�м������
	 * @param page   �ڼ�ҳ begin from 1
	 * 
	 *               1. ��ԃ�õ��Y������totalNumber 2. Ӌ�����퓴a 3. ��ʽ��page��ֹ��� 4. Ӌ��õ��_ʼindex
	 *               5. ���в�ԃ ݔ���Y��
	 * 
	 * @return
	 */
	public ArrayList<Image> getAllImages(int length, int page) {
		// ������������
		String sqlForRowNumber = "SELECT * FROM travelimage WHERE Path IS NOT NULL";
		int totalNumber = getRowCount(sqlForRowNumber);

		// Ӌ�����퓴a
		int totalPage = getTotalPage(length, totalNumber);

		// ����page����ֹpage���/<0
		page = getTurePage(page, totalPage);

		// ���㿪ʼ��index
		int beginIndex = (page - 1) * length;

		String sql = "SELECT " + SQL_ALL_IMAGE_SELECT + "FROM travelimage " + " LIMIT ?,? ";
		return getForList(sql, beginIndex, length);
	}

	/**
	 * @param length �S�C��Ƭ�Ă��� ���ʹ�� �@ȡ�S�C��Ƭ���@ʾ
	 * 
	 * @return
	 */
	public ArrayList<Image> getRandomImages(int length) {
		// ������������
		String sqlForRowNumber = "SELECT * FROM travelimage WHERE Path IS NOT NULL";
		int totalNumber = getRowCount(sqlForRowNumber);

		// ��ȡ�������
		int[] numbers = new int[length];
		for (int i = 0; i < numbers.length; i++)
			numbers[i] = (int) (Math.random() * totalNumber);

		ArrayList<Image> images = getAllImages(totalNumber, 1);
		ArrayList<Image> imagesRandomInLength = new ArrayList<>();

		//
		for (int i = 0; i < numbers.length; i++)
			imagesRandomInLength.add(images.get(numbers[i]));

		return imagesRandomInLength;
	}

	/**
	 * @param length �׏�
	 * 
	 *               �@ȡ���µ���Ƭ
	 * @return
	 */
	public ArrayList<Image> getLatestImages(int length) {
		String sql = "SELECT " + SQL_ALL_IMAGE_SELECT + " FROM travelimage ORDER BY `DateUpload` DESC LIMIT 0,?";
		return getForList(sql, length);
	}

	/**
	 * @param length �׏� �@ȡ���Ď׏���Ƭfor�^�D݆��
	 * 
	 * @return
	 */
	public ArrayList<Image> getHotImages(int length) {

		// �ʂ�sql�Z��
		String sql = "SELECT " + SQL_ALL_IMAGE_SELECT_LIKE
				+ " WHERE A.ImageID=B.ImageID GROUP BY ImageID ORDER BY Likes DESC LIMIT 0,? ";
		return getForList(sql, length);

	}

	public int getLikeByImageID(int iid) {
		int like = 0;
		String sql = "SELECT COUNT(FavorID) AS like_number " + " FROM travelimagefavor AS A "
				+ " WHERE A.ImageID=? GROUP BY ImageID ORDER BY like_number";
		Long temp = getForValue(Long.class, sql, iid);
		if (temp != null)
			like = getForValue(Long.class, sql, iid).intValue();

		return like;
	}

	public void sortByLikesDESC(ArrayList<Image> images) {
		images.sort(new Comparator<Image>() {
			@Override
			public int compare(Image o1, Image o2) {
				return o2.getLikes() - o1.getLikes();
			}
		});

//		System.out.println("-----------> DESC\n" + images);

	}

	public void sortByLikesASC(ArrayList<Image> images) {
		images.sort(new Comparator<Image>() {

			@Override
			public int compare(Image o1, Image o2) {
				return o1.getLikes() - o2.getLikes();
			}

		});

//		System.out.println("-----------> ASC\n" + images);
	}

	/**
	 * @param length      һ�����@ʾ�חl�Y��
	 * @param totalNumber �Y�����Ŀ���
	 * 
	 *                    Ӌ�����퓴a
	 * @return
	 */
	public int getTotalPage(int length, int totalNumber) {
		int totalPage = totalNumber / length; // 81/10 = 8

		if (totalNumber % length != 0) // 81%10 = 1 != 0
			totalPage++;

		System.out.println("==>total page: " + totalPage);
		return totalPage;
	}

	/**
	 * @param page      �ڎ��
	 * @param totalPage �G퓴a
	 * 
	 *                  page = 1 begin index= 0;page =2 begin index = count;
	 * @return
	 */
	public int getTurePage(int page, int totalPage) {
		if (page <= 0)
			page = 1;
		else if (page >= totalPage)
			page = totalPage;
		return page;
	}

	/**
	 * @param uid
	 * @return
	 */
	public ArrayList<Image> getFavorImagesByUid(int uid) {
		String sql = "SELECT F.imageID as ImageID, `Title`, `Description`,  `Latitude`, `Longitude`,"
				+ "`CityCode`, `Country_RegionCodeISO` AS CountryCodeISO, "
				+ "`Path`, I.uid as `Uid` , `Content`, `DateUpload`  " + "FROM travelimage as I, travelimagefavor as F "
				+ "WHERE F.uid=? AND I.imageID=F.imageID";
		return getForList(sql, uid);
	}

	public ArrayList<Image> getFavorImagesByUid(int uid, int length, int page) {
		String sql = "SELECT F.imageID as ImageID, `Title`, `Description`,  `Latitude`, `Longitude`,"
				+ "`CityCode`, `Country_RegionCodeISO` AS CountryCodeISO, "
				+ "`Path`, I.uid as `Uid` , `Content`, `DateUpload`  " + "FROM travelimage as I, travelimagefavor as F "
				+ "WHERE F.uid=? AND I.imageID=F.imageID LIMIT ?,?";

		// ������������
		int totalNumber = getFavorImagesNumberByUid(uid);

		// Ӌ�����퓴a
		int totalPage = getTotalPage(length, totalNumber);

		// ����page����ֹpage���/<0
		page = getTurePage(page, totalPage);

		// ���㿪ʼ��index
		int beginIndex = (page - 1) * length;

		return getForList(sql, uid, beginIndex, length);
	}

	public int getFavorImagesNumberByUid(int uid) {
		String sql = "SELECT * FROM travelimage as I, travelimagefavor as F " + "WHERE F.uid=? AND I.imageID=F.imageID";
		return getRowCount(sql, uid);

	}

	public ArrayList<Image> getMyImagesByUid(int uid) {
		String sql = "SELECT " + SQL_ALL_IMAGE_SELECT + " FROM travelimage WHERE uid=?";
		return getForList(sql, uid);
	}

	public ArrayList<Image> getMyImagesByUid(int uid, int length, int page) {
		String sql = "SELECT " + SQL_ALL_IMAGE_SELECT + " FROM travelimage WHERE uid=? LIMIT ?,?";

		// ������������
		int totalNumber = getMyImagesNumberByUid(uid);

		// Ӌ�����퓴a
		int totalPage = getTotalPage(length, totalNumber);

		// ����page����ֹpage���/<0
		page = getTurePage(page, totalPage);

		// ���㿪ʼ��index
		int beginIndex = (page - 1) * length;
		if (beginIndex <= 0) {
			beginIndex = 0;
		}

		return getForList(sql, uid, beginIndex, length);
	}
	
	public int getMyImagesNumberByUid(int uid) {
		String sql = "SELECT * FROM travelimage WHERE uid=? ";
		return getRowCount(sql, uid);
		
	}

	/*
	 * Tests
	 * 
	 */
	public void testGetFavorImagesByUid() {
		ImageDAO imageDAO = getImageDAO();
		ArrayList<Image> images = getFavorImagesByUid(15);
		System.out.println(images);
	}

	public void testGetMyImageByUid() {
		ImageDAO imageDAO = getImageDAO();
		ArrayList<Image> images = imageDAO.getMyImagesByUid(1);
		System.out.println(images);
	}
}
