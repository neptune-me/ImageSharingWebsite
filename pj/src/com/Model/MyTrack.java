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
	 * �c��罻�Q��Ϣ��Ψ������֮һ
	 * �H�� details nav  �{��
	 * @param iid
	 */
	public void newImageVisit(int iid) {
		System.out.println("========new Visit image=======");
		if (!isContainImageID(iid)) {
			System.out.println("new Visit image---> �]ȥ�^");
			// ��������� ��size<10 û�� null
			// ������ size>10
			if (imageIDs.size() >= 10) {
				removeTheEarliestImage();
				System.out.println("new Visit image---> ���10���Ƴ��������");
			}
		} else {
			// ���� size��10 ��10 ��һ��
			removeTheParticularImageByImageID(iid);
			System.out.println("new Visit image---> ���ڣ��Ƴ�");
		}

		imageIDs.add(iid);
		System.out.println("new Visit image---> ����ꮅ");
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
		stringBuffer.append("<a href='details.nav?id="+imageIDs.get(index)+"'>");//ֱ��details��
		
		stringBuffer.append("  "+ getTitleByIndex(index)+" ");
		
		stringBuffer.append("</a>");
		stringBuffer.append("</td></tr>");
		
		return stringBuffer.toString();
	}

	/**
	 * �c��罻���ĵڶ�������
	 * @param index 0��������Ǹ�
	 * @return
	 */
	public String getTitleByIndex(int index) {
		ImageDAO imageDAO = ImageDAO.getImageDAO();
		int iid = imageIDs.get(index);
		String sql = "SELECT TITLE FROM travelimage WHERE imageid=?";
		
		return imageDAO.getForValue(String.class, sql, iid);
	}

	// ʹ��iid/title�ж�
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
