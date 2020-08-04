package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class StudentVO extends Student{

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date start;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date end;

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "StudentVO [start=" + start + ", end=" + end + "]";
	}
	
	
	
}
