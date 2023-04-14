package com.github.admin.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class User implements Serializable {

    /**id,默认自动增长**/
    private Long id;
    /**用户名称或账号**/
    private String userName;
    /**昵称**/
    private String nickName;
    /**密码***/
    private String password;

    private String confirm;

    /**加密盐**/
    private String salt;
    /**部门id**/
    private Long deptId;
    /**图片路径**/
    private String picture;
    /**性别**/
    private int sex;
    /**邮箱**/
    private String email;
    /**电话号码**/
    private String phone;
    /**备注**/
    private String remark;
    /**创建时间***/
    private Date createDate;
    /**更新时间**/
    private Date updateDate;
    /**状态**/
    private int status;

    private Dept dept;

    private Set<Role> roles = new HashSet<>(0);



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", deptId=" + deptId +
                ", picture='" + picture + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", remark='" + remark + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", status=" + status +
                '}';
    }
}
