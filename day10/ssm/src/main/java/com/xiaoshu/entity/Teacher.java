package com.xiaoshu.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Teacher implements Serializable {
    @Id
    private Integer tid;

    private String tname;

    private Integer shengid;

    private Integer shiid;

    private Integer quid;

    private static final long serialVersionUID = 1L;

    /**
     * @return tid
     */
    public Integer getTid() {
        return tid;
    }

    /**
     * @param tid
     */
    public void setTid(Integer tid) {
        this.tid = tid;
    }

    /**
     * @return tname
     */
    public String getTname() {
        return tname;
    }

    /**
     * @param tname
     */
    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    /**
     * @return shengid
     */
    public Integer getShengid() {
        return shengid;
    }

    /**
     * @param shengid
     */
    public void setShengid(Integer shengid) {
        this.shengid = shengid;
    }

    /**
     * @return shiid
     */
    public Integer getShiid() {
        return shiid;
    }

    /**
     * @param shiid
     */
    public void setShiid(Integer shiid) {
        this.shiid = shiid;
    }

    /**
     * @return quid
     */
    public Integer getQuid() {
        return quid;
    }

    /**
     * @param quid
     */
    public void setQuid(Integer quid) {
        this.quid = quid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tid=").append(tid);
        sb.append(", tname=").append(tname);
        sb.append(", shengid=").append(shengid);
        sb.append(", shiid=").append(shiid);
        sb.append(", quid=").append(quid);
        sb.append("]");
        return sb.toString();
    }
}