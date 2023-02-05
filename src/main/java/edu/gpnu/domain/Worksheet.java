package edu.gpnu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName(value = "worksheet")
public class Worksheet implements Serializable {

    private static final long serialVersionUID = -40329785421160012L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private Integer weekNum;
    private Integer dayWeek;
    private Integer dayNum;
    private Timestamp createTime;
    private String createBy;
    private String updateBy;
    private Timestamp updateTime;
    private String remark;

    public Worksheet() {
    }

    public Worksheet(String id, Integer weekNum, Integer dayWeek, Integer dayNum, Timestamp createTime, String createBy, String updateBy, Timestamp updateTime, String remark) {
        this.id = id;
        this.weekNum = weekNum;
        this.dayWeek = dayWeek;
        this.dayNum = dayNum;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    public Integer getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(Integer dayWeek) {
        this.dayWeek = dayWeek;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Worksheet{" +
                "id='" + id + '\'' +
                ", weekNum=" + weekNum +
                ", dayWeek=" + dayWeek +
                ", dayNum=" + dayNum +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}
