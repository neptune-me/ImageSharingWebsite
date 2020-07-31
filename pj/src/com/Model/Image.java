package com.Model;

import java.sql.Timestamp;

import com.DAO.ImageDAO;
import com.DAO.TravelUserDAO;

public class Image {

	private int imageID = 1;// 默認圖片
	private String title;
	private String description;// 记得重设getter
	private String path;
	private int uid;

	private int cityCode;
	private String countryCodeISO;// Country_RegionCodeISO
	private String content;
	private Timestamp dateUpload;

	private double latitude;
	private double longitude;

	/*
	 * init in getter
	 */
	// need imageid is not null
	private int likes;
	// need uid is not null
	private TravelUser user;
	// need cityCode countryCodeISO
	private Geo iGeo;

	/*
	 * @param printStyle ""
	 */
	public String toString() {
		StringBuffer deStringBuffer = new StringBuffer("-->" + imageID);
		deStringBuffer.append(" \n \t");
		deStringBuffer.append("title: " + title + "; description: " + description + "; likes: " + getLikes());
		deStringBuffer.append(" | \t");
		deStringBuffer.append("path: " + path + "; uid: " + uid + "; content: " + content);
		deStringBuffer.append(" | \t");
		deStringBuffer.append("cityCode: " + cityCode + "; countryCodeISO: " + countryCodeISO);
		deStringBuffer.append(" | \t");
		deStringBuffer.append("dateUpload: " + dateUpload);
		deStringBuffer.append(" | \t");
		deStringBuffer.append("; user: " + getUser());
		deStringBuffer.append("");

		return deStringBuffer.toString();
	}

	/**search nav使用？
	 * @return
	 */
	public String toSearchMessStr() {
		StringBuffer deStringBuffer = new StringBuffer();
		deStringBuffer.append("<div class='col-xs-12'>\r\n" + "    <article class='left'>\r\n"
				+ "        <div class='image_container'>\n");
		deStringBuffer.append(" <a title='' href='./details.nav?id=" + getImageID()
				+ "' data-toggle='tooltip' data-placement='right' \r\n" + "            data-original-title='ImageID: "
				+ getImageID() + " | via " + getUser().getUsername() + "'>\r\n"
				+ "                 <img class='image_self' src='travel-images/square-medium/" + getPath() + "'>\r\n"
				+ "            </a>\n");
		deStringBuffer.append(" </div>\n");

		deStringBuffer.append(" <div class='image_description_search col-xs-6'>\n");
		deStringBuffer.append("<h2>" + getTitle() + "</h2>");
		deStringBuffer.append("<p>" + getDescription() + "</p>");
		deStringBuffer
				.append("<p style='text-align:right' class='red'><span class='glyphicon glyphicon-thumbs-up'></span>  "
						+ getLikes() + "</p>");
		deStringBuffer.append("<p class='hei' style='text-align:right'>\n"
				+ " <span style='text-align:right' class='footer-time-left'>" + getDateUploadStr() + "</span> </p>\n");
		deStringBuffer.append(" </div>\r\n" + "</article>\r\n" + "</div>	");
		deStringBuffer.append("");

		return deStringBuffer.toString();
	}

	public String toSearchPicsStr() {
		StringBuffer deStringBuffer = new StringBuffer();
		deStringBuffer.append("<div class='col-xs-3 col-sm-3 images-container-square'>\r\n");
		deStringBuffer.append("    				<a title='' href='./details.nav?id="+ getImageID() +"' \r\n");
		deStringBuffer.append("    				data-original-title='" + getTitle() + " | via "
				+ getUser().getUsername() + "' data-toggle='tooltip'>\r\n");
		deStringBuffer.append("        				<img src='./travel-images/square-medium/" + getPath() + "'>\r\n");
		deStringBuffer.append("    				</a>\r\n</div>");

		deStringBuffer.append("");

		System.out.println("=====searchPicStr=====");
		System.out.println(deStringBuffer);
		
		return deStringBuffer.toString();
	}

	/*
	 * getter & setter
	 */
	public int getImageID() {
		return imageID;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(Object uid) {
		this.uid = (int) uid;
	}

	public void setImageID(Object imageID) {
		this.imageID = (int) imageID;
	}

	public String getTitle() {
		return title.replace("\"", "\\\"");
	}

	public void setTitle(Object title) {
		this.title = (String) title;
	}

	public String getDescription() {
		if (description == null)
			return "A picture about " + content;
		else
			return description;
	}

	public void setDescription(Object description) {
		this.description = (String) description;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(Object cityCode) {
		if (cityCode != null)
			this.cityCode = (int) cityCode;
		else
			this.cityCode = 0;
	}

	public String getCountryCodeISO() {
		return countryCodeISO;
	}

	public void setCountryCodeISO(Object countryCodeISO) {
		this.countryCodeISO = (String) countryCodeISO;
	}

	public TravelUser getUser() {
		if (user == null) {
			TravelUserDAO travelUserDAO = TravelUserDAO.getTravelUserDAO();
			user = travelUserDAO.getByUid(uid);
		}
		return user;
	}

	public void setUser(Object user) {
		this.user = (TravelUser) user;
	}

	public String getPath() {
		return path;
	}

	public void setPath(Object path) {
		this.path = (String) path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = (String) content;
	}

	public int getLikes() {
		ImageDAO imageDAO = ImageDAO.getImageDAO();
		likes = imageDAO.getLikeByImageID(imageID);

		return likes;
	}

	public void setLikes(Object likes) {
		this.likes = ((Long) likes).intValue();
	}

	public Timestamp getDateUpload() {
		return dateUpload;
	}

	public String getDateUploadStr() {
		String uString = getDateUpload().toString();
		uString = uString.substring(0, uString.lastIndexOf("."));
		return uString;
	}

	public void setDateUpload(Object dateUpload) {
		this.dateUpload = (Timestamp) dateUpload;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(Object latitude) {
		if (latitude != null)
			this.latitude = (double) latitude;
		else
			this.latitude = 0;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(Object longitude) {
		if (longitude != null)
			this.longitude = (double) longitude;
		else
			this.longitude = 0;
	}

	public Geo getiGeo() {
		if (iGeo == null) {
			iGeo = new Geo(cityCode, countryCodeISO);
		}
		return iGeo;
	}

	public void setiGeo(Object iGeo) {
		this.iGeo = (Geo) iGeo;
	}
}
