package com.xiaoshu.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Classes implements Serializable {
    @Id
    @Column(name = "c_id")
    private Integer cId;

    @Column(name = "c_name")
    private String cName;

    private static final long serialVersionUID = 1L;

    /**
     * @return c_id
     */
    public Integer getcId() {
        return cId;
    }

    /**
     * @param cId
     */
    public void setcId(Integer cId) {
        this.cId = cId;
    }

    /**
     * @return c_name
     */
    public String getcName() {
        return cName;
    }

    /**
     * @param cName
     */
    public void setcName(String cName) {
        this.cName = cName == null ? null : cName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cId=").append(cId);
        sb.append(", cName=").append(cName);
        sb.append("]");
        return sb.toString();
    }
}