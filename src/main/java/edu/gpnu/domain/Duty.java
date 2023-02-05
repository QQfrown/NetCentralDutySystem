package edu.gpnu.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName(value = "duty")
public class Duty implements Serializable {
    private static final long serialVersionUID = -10329785421160012L;

    private String userId;
    private String worksheetId;
    private Integer dutyType;

    public Duty() {
    }

    public Duty(String userId, String worksheetId, Integer dutyType) {
        this.userId = userId;
        this.worksheetId = worksheetId;
        this.dutyType = dutyType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWorksheetId() {
        return worksheetId;
    }

    public void setWorksheetId(String worksheetId) {
        this.worksheetId = worksheetId;
    }

    public Integer getDutyType() {
        return dutyType;
    }

    public void setDutyType(Integer dutyType) {
        this.dutyType = dutyType;
    }

    @Override
    public String toString() {
        return "Duty{" +
                "userId='" + userId + '\'' +
                ", worksheetId='" + worksheetId + '\'' +
                ", dutyType=" + dutyType +
                '}';
    }
}
