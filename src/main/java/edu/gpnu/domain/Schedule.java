package edu.gpnu.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName(value = "schedule")
public class Schedule implements Serializable {
    private static final long serialVersionUID = -40329785421160112L;

    private String userId;
    private String worksheetId;

    public Schedule() {
    }

    public Schedule(String userId, String worksheetId) {
        this.userId = userId;
        this.worksheetId = worksheetId;
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

    @Override
    public String toString() {
        return "Schedule{" +
                "userId='" + userId + '\'' +
                ", worksheetId='" + worksheetId + '\'' +
                '}';
    }
}
