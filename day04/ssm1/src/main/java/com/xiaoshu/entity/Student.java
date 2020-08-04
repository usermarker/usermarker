package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

public class Student implements Serializable {
    @Id
    private Integer sid;

    @Column(name = "s_Name")
    private String sName;

    private String sex;

    private String hobby;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    private String state;

    private Integer maid;
    
    @Transient
    private Major major;

    private static final long serialVersionUID = 1L;

    
    public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	/**
     * @return sid
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * @param sid
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * @return s_Name
     */
    public String getsName() {
        return sName;
    }

    /**
     * @param sName
     */
    public void setsName(String sName) {
        this.sName = sName == null ? null : sName.trim();
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * @return hobby
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * @param hobby
     */
    public void setHobby(String hobby) {
        this.hobby = hobby == null ? null : hobby.trim();
    }

    /**
     * @return birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return maid
     */
    public Integer getMaid() {
        return maid;
    }

    /**
     * @param maid
     */
    public void setMaid(Integer maid) {
        this.maid = maid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sid=").append(sid);
        sb.append(", sName=").append(sName);
        sb.append(", sex=").append(sex);
        sb.append(", hobby=").append(hobby);
        sb.append(", birthday=").append(birthday);
        sb.append(", state=").append(state);
        sb.append(", maid=").append(maid);
        sb.append("]");
        return sb.toString();
    }

	public Student(Integer sid, String sName, String sex, String hobby, Date birthday, String state, Integer maid,
			Major major) {
		super();
		this.sid = sid;
		this.sName = sName;
		this.sex = sex;
		this.hobby = hobby;
		this.birthday = birthday;
		this.state = state;
		this.maid = maid;
		this.major = major;
	}

	public Student() {
		super();
	}
    
    
}