package com.Model;

import java.util.ArrayList;
import java.util.Stack;

import com.DAO.ImageDAO;

public class MyTrack {

	private ArrayList<Integer> imageIDs = null;
	private int length = 10;

	public MyTrack() {
		imageIDs = new ArrayList<>();

	}

	/**
	 * 與外界交換信息的唯二方法之一
	 * 僅被 details nav  調用
	 * @param iid
	 */
	public void newImageVisit(int iid) {
		System.out.println("========new Visit image=======");
		if (!isContainImageID(iid)) {
			System.out.println("new Visit image---> 沒去過");
			// 如果不存在 且size<10 没满 null
			// 不存在 size>10
			if (imageIDs.size() >= 10) {
				removeTheEarliestImage();
				System.out.println("new Visit image---> 大於10，移除昨最早的");
			}
		} else {
			// 存在 size》10 《10 都一样
			removeTheParticularImageByImageID(iid);
			System.out.println("new Visit image---> 存在，移除");
		}

		imageIDs.add(iid);
		System.out.println("new Visit image---> 添加完畢");
	}
	
	public int getLengthOfId() {
		return imageIDs.size();
	}
	
	public int getImageIdByIndex(int index) {
		return imageIDs.get(index);
	}
	
	@Override
	public String toString() {
		return "MyTrack [imageIDs=" + imageIDs.toString() + ", length=" + length + ", size=" + imageIDs.size()+"]";
	}
	
	public String byIndex(int index) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<tr><td>");

		stringBuffer.append(" ");
		//<button class="btn btn-xs btn-default" active>(index+1)</button>
		stringBuffer.append(" <button class=\"btn btn-xs btn-default\" active>"+ (index+1) +"</button> ");
		stringBuffer.append("<a href='details.nav?id="+imageIDs.get(index)+"'>");//直接details？
		
		stringBuffer.append("  "+ getTitleByIndex(index)+" ");
		
		stringBuffer.append("</a>");
		stringBuffer.append("</td></tr>");
		
		return stringBuffer.toString();
	}

	/**
	 * 與外界交流的第二個方法
	 * @param index 0是最早的那个
	 * @return
	 */
	public String getTitleByIndex(int index) {
		ImageDAO imageDAO = ImageDAO.getImageDAO();
		int iid = imageIDs.get(index);
		String sql = "SELECT TITLE FROM travelimage WHERE imageid=?";
		
		return imageDAO.getForValue(String.class, sql, iid);
	}

	// 使用iid/title判断
	public boolean isContainImageID(int iid) {

		return imageIDs.contains(iid);

	}

	public int indexOfExistedImageID(int iid) {
		return imageIDs.indexOf(iid);
	}

	public void removeTheEarliestImage() {
		System.out.println("------->remove the first" + imageIDs.toString());
		imageIDs.remove(0);
		System.out.println(imageIDs.toString());
	}

	public void removeTheParticularImageByImageID(int iid) {
		int index = indexOfExistedImageID(iid);
		System.out.println("-->particular: " + imageIDs.toString());
		imageIDs.remove(index);
		System.out.println("--> index: " + index);
		System.out.println("--> after: " + imageIDs.toString());
	}


}
