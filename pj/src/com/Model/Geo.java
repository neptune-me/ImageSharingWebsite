package com.Model;

import com.DAO.ImageDAO;

public class Geo {

	private Integer cityCode;
	private String countryCodeISO;// Country_RegionCodeISO
	private String continentCode;

	private String city;
	private String country;
	private String continent;

	public Geo(Integer cityCode, String countryCodeISO) {
		this.cityCode = cityCode;
		this.countryCodeISO = countryCodeISO;
	}
	public Geo() {
	}
	
	public void setCity(Object city) {
		this.city = (String)city;
	}

	public String getCity() {
		if (cityCode == null) {
			return " ";
		}
		ImageDAO dao = ImageDAO.getImageDAO();
		String sql = "SELECT AsciiName FROM geocities WHERE GeoNameID=?";
		city = dao.getForValue(String.class, sql, cityCode);
		return city;
	}

	public String getCountry() {
		if (countryCodeISO == null) {
			return " ";
		}
		ImageDAO dao = ImageDAO.getImageDAO();
		String sql = "SELECT Country_RegionName FROM geocountries_regions WHERE ISO=?";
		country = dao.getForValue(String.class, sql, countryCodeISO);

		sql = "SELECT Continent FROM geocountries_regions WHERE ISO=?";
		continentCode = dao.getForValue(String.class, sql, countryCodeISO);

		return country;
	}

	public String getContinent() {
		if (continentCode == null) {
			return " ";
		}
		ImageDAO dao = ImageDAO.getImageDAO();
		String sql = "SELECT ContinentName FROM geocontinents WHERE ContinentCode=?";
		continent = dao.getForValue(String.class, sql, continentCode);
		return continent;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(Object cityCode) {
		if (cityCode != null)
			this.cityCode = (int) cityCode;

	}

	public String getCountryCodeISO() {
		return countryCodeISO;
	}

	public void setCountryCodeISO(Object countryCodeISO) {
		if (countryCodeISO != null)
			this.countryCodeISO = (String) countryCodeISO;
	}

	public String getContinentCode() {
		return continentCode;
	}

	public void setContinentCode(Object continentCode) {
		if (countryCodeISO != null)
			this.continentCode = (String) continentCode;
	}

}
