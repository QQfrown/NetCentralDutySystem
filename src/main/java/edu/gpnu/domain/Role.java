package edu.gpnu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 角色表(Role)实体类
 *
 * @author 丘辛意
 */
@TableName(value = "role")
public class Role implements Serializable {
    private static final long serialVersionUID = -40356785421168312L;
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String description;
    private String permissions;
    private Boolean status;
    private Boolean delFlag;
    private Timestamp createTime;
    private String createBy;
    private String updateBy;
    private Timestamp updateTime;
    private String remark;

    public Role() {
    }

    public Role(String id, String description, String permissions, Boolean status, Boolean delFlag, Timestamp createTime, String createBy, String updateBy, Timestamp updateTime, String remark) {
        this.id = id;
        this.description = description;
        this.permissions = permissions;
        this.status = status;
        this.delFlag = delFlag;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
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

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", permissions='" + permissions + '\'' +
                ", status=" + status +
                ", delFlag=" + delFlag +
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
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(description, role.description) && Objects.equals(permissions, role.permissions) && Objects.equals(status, role.status) && Objects.equals(delFlag, role.delFlag) && Objects.equals(createTime, role.createTime) && Objects.equals(createBy, role.createBy) && Objects.equals(updateBy, role.updateBy) && Objects.equals(updateTime, role.updateTime) && Objects.equals(remark, role.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, permissions, status, delFlag, createTime, createBy, updateBy, updateTime, remark);
    }
}
