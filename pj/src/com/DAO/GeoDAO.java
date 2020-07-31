package com.DAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.Model.Geo;
import com.Model.Image;
import com.Model.TravelUser;

public class GeoDAO extends DAO<Geo> {

	public static GeoDAO geoDAO = null;
	
	public static GeoDAO getGeoDAO() {
		if (geoDAO == null)
			return new GeoDAO();
		else
			return geoDAO;
	}

	public static void main(String[] args) {
		System.out.println("main function ");
		GeoDAO geoDAO = GeoDAO.getGeoDAO();
		
		
		
		
		
		
		
		System.out.println();
		String sql = "SELECT `ISO`, `Country_RegionName` FROM geocountries_regions WHERE 1 ORDER BY Country_RegionName";
//		System.out.println(geoDAO.getForValues(String.class, String.class, sql).size());
//		Map<String,String> map = geoDAO.getForValues(String.class, String.class, sql);
//		for(Map.Entry<String, String> entry : map.entrySet()){
//			String code = entry.getKey();
//			String value = entry.getValue();
//			System.out.println(code +": "+value);
//		}
		//System.out.println(geoDAO.getForValues(String.class, String.class, sql));

		Map<String,String> map = geoDAO.getAllCitiesByCountryCodeISO("CN");
		System.out.println(map.size());
	}
	
	public GeoDAO() {
		super(Geo.class);
		geoDAO = this;
	}
	
	
	public String getCountryByCountryCodeISO(String countryCodeISO) {
		String sql = "SELECT Country_RegionName FROM geocountries_regions WHERE ISO=? ";
		return getForValue(String.class, sql, countryCodeISO);
	}
	public String getCityByCityCode(int cityCode) {
		String sql = "SELECT AsciiName FROM geocities WHERE GeoNameID=? ";
		return getForValue(String.class, sql, cityCode);
	}
	
	public Map<String, String> getAllCountries() {
		//252
		String sql = "SELECT `ISO`, `Country_RegionName` FROM geocountries_regions ORDER BY Country_RegionName ";
		return getForValues(String.class, String.class, sql);
	}
	public Map<String, String> getAllCitiesByCountryCodeISO(String countryCodeISO) {
		
		String sql = "SELECT `GeoNameID`, `AsciiName` FROM geocities WHERE Country_RegionCodeISO=? ORDER BY AsciiName LIMIT 0,100";
		
		
		return getForValues(String.class, String.class, sql, countryCodeISO);
	}
	public ArrayList<Geo> getAllCitiesByCountryCodeISOForArrayList(String countryCodeISO) {
		
		String sql = "SELECT `GeoNameID` as CityCode, `AsciiName` as City FROM geocities "
				+ "WHERE Country_RegionCodeISO=? ORDER BY AsciiName LIMIT 0,100";
		
		
		return getForList(sql, countryCodeISO);
	}
	
	
	
}
