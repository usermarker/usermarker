package com.xiaoshu.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Major implements Serializable {
    @Id
    private Integer maid;

    // 字段
    @Column(name = "s_Name")
    private String sName;

    private static final long serialVersionUID = 1L;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", maid=").append(maid);
        sb.append(", sName=").append(sName);
        sb.append("]");
        return sb.toString();
    }
}