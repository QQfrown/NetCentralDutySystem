package edu.gpnu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 用户表(User)实体类
 *
 * @author 丘辛意
 */
@TableName(value = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userName;
    private String password;
    private String nickName;
    private String realName;
    private String number;
    private String idNumber;
    private String campus;
    private String college;
    private String major;
    private String avatar;
    private String telephone;
    private String dormitory;
    private String email;
    private Boolean sex;
    private String birthday;
    private Boolean userType;
    private Boolean status;
    private Timestamp createTime;
    private String createBy;
    private String updateBy;
    private Timestamp updateTime;
    private String remark;

    public User() {
    }

    public User(String id, String userName, String password, String nickName, String realName, String number, String idNumber, String campus, String college, String major, String avatar, String telephone, String dormitory, String email, Boolean sex, String birthday, Boolean userType, Boolean status, Timestamp createTime, String createBy, String updateBy, Timestamp updateTime, String remark) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.realName = realName;
        this.number = number;
        this.idNumber = idNumber;
        this.campus = campus;
        this.college = college;
        this.major = major;
        this.avatar = avatar;
        this.telephone = telephone;
        this.dormitory = dormitory;
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
        this.userType = userType;
        this.status = status;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Boolean getUserType() {
        return userType;
    }

    public void setUserType(Boolean userType) {
        this.userType = userType;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", number='" + number + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", campus='" + campus + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", avatar='" + avatar + '\'' +
                ", telephone='" + telephone + '\'' +
                ", dormitory='" + dormitory + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", userType=" + userType +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(nickName, user.nickName) && Objects.equals(realName, user.realName) && Objects.equals(number, user.number) && Objects.equals(idNumber, user.idNumber) && Objects.equals(campus, user.campus) && Objects.equals(college, user.college) && Objects.equals(major, user.major) && Objects.equals(avatar, user.avatar) && Objects.equals(telephone, user.telephone) && Objects.equals(dormitory, user.dormitory) && Objects.equals(email, user.email) && Objects.equals(sex, user.sex) && Objects.equals(birthday, user.birthday) && Objects.equals(userType, user.userType) && Objects.equals(status, user.status) && Objects.equals(createTime, user.createTime) && Objects.equals(createBy, user.createBy) && Objects.equals(updateBy, user.updateBy) && Objects.equals(updateTime, user.updateTime) && Objects.equals(remark, user.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, nickName, realName, number, idNumber, campus, college, major, avatar, telephone, dormitory, email, sex, birthday, userType, status, createTime, createBy, updateBy, updateTime, remark);
    }

}
