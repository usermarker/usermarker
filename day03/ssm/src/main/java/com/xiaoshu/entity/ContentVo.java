package com.xiaoshu.entity;

public class ContentVo extends Content{
	private String sname;

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	@Override
	public String toString() {
		return "ContentVo [sname=" + sname + "]";
	}
	
}
