package com.xiaoshu.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Student implements Serializable {
    @Id
    private Integer sid;

    private String sname;

    private String sage;

    private static final long serialVersionUID = 1L;

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
     * @return sname
     */
    public String getSname() {
        return sname;
    }

    /**
     * @param sname
     */
    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    /**
     * @return sage
     */
    public String getSage() {
        return sage;
    }

    /**
     * @param sage
     */
    public void setSage(String sage) {
        this.sage = sage == null ? null : sage.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sid=").append(sid);
        sb.append(", sname=").append(sname);
        sb.append(", sage=").append(sage);
        sb.append("]");
        return sb.toString();
    }
}