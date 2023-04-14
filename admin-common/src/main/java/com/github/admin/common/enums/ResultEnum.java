package com.github.admin.common.enums;

import com.github.admin.common.interfaces.ResultInterface;
import lombok.Getter;

/**
 * 后台返回结果集枚举
 * @author 小懒虫
 * @date 2018/8/14
 */
@Getter
public enum ResultEnum implements ResultInterface {

    /**
     * 通用状态
     */
    SUCCESS("200", "admin.success","成功"),
    ERROR("400", "admin.fail","错误"),

    /**
     * 账户问题
     */
    USER_EXIST("401", "admin.user.not.exit","该用户名已经存在"),
    USER_PWD_NULL("402", "admin.password.empty","密码不能为空"),
    USER_INEQUALITY("403", "admin.two.passwords.not.match","两次密码不一致"),
    USER_OLD_PWD_ERROR("404", "admin.original.password.incorrect","原来密码不正确"),
    USER_NAME_PWD_NULL("405", "admin.user.not.exit","用户名和密码不能为空"),
    USER_CAPTCHA_ERROR("406", "admin.user.not.exit","验证码错误"),

    /**
     * 角色问题
     */
    ROLE_EXIST("401", "admin.user.not.exit","该角色标识已经存在，不允许重复！"),



    /**
     * 权限问题
     */
    NO_PERMISSIONS("401", "admin.user.not.exit","权限不足！"),
    NO_ADMIN_AUTH("500", "admin.user.not.exit","不允许操作超级管理员"),
    NO_ADMIN_STATUS("501", "admin.user.not.exit","不能修改超级管理员状态"),
    NO_ADMINROLE_AUTH("500", "admin.user.not.exit","不允许操作管理员角色"),
    NO_ADMINROLE_STATUS("501", "admin.user.not.exit","不能修改管理员角色状态"),
    SAVE_MENU_ERROR("501", "save.menu.error","添加菜单失败"),
    UPDATE_MENU_ERROR("501", "update.menu.error","更新菜单失败"),
    UPDATE_MENU_STATUS_ERROR("501", "update.menu.status.error","更新菜单状态失败"),

    UPDATE_ADMIN_STATUS_ERROR("501", "update.user.status.error","更新用户状态失败"),
    SYS_ERROR("500","sys.error","系统异常,请稍后再试");

    ;

    private String code;

    private String lanKey;
    private String message;

    ResultEnum(String code, String lanKey,String message) {
        this.code = code;
        this.lanKey = lanKey;
        this.message = message;
    }
}
