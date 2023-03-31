package com.edu.shopclient.domain;

/*레코드 한건을 담게될 데이터 전달용 객체 (DTO)*/
public class TopCategory {
	private int topcategory_idx;
	private String topcategory_name;
	
	//shift+alt+s+r+(alt+a)+o+enter
	public int getTopcategory_idx() {
		return topcategory_idx;
	}
	public void setTopcategory_idx(int topcategory_idx) {
		this.topcategory_idx = topcategory_idx;
	}
	public String getTopcategory_name() {
		return topcategory_name;
	}
	public void setTopcategory_name(String topcategory_name) {
		this.topcategory_name = topcategory_name;
	}	
}
