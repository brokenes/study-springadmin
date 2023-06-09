package com.github.admin.api.shiro.real;

import com.github.admin.client.RoleServiceClient;
import com.github.admin.client.UserServiceCient;
import com.github.admin.common.constants.AdminConst;
import com.github.admin.common.domain.Role;
import com.github.admin.common.domain.User;
import com.github.admin.common.enums.ResultEnum;
import com.github.admin.common.enums.StatusEnum;
import com.github.admin.common.exception.ResultException;
import com.github.admin.common.util.Result;
import com.github.admin.common.util.ShiroUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Set;


public class AuthRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRealm.class);
    @Autowired
    private UserServiceCient userServiceCient;

    @Autowired
    private RoleServiceClient roleServiceClient;

    /**
     * 授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取用户Principal对象
        User user = (User) principal.getPrimaryPrincipal();

        if (user == null) {
            throw new UnknownAccountException();
        }else if(user.getStatus() == StatusEnum.FREEZED.getCode()) {
            throw new LockedAccountException();
        }
        Long userId = user.getId();
        // 管理员拥有所有权限
        if (userId.equals(AdminConst.ADMIN_ID)) {
            info.addRole(AdminConst.ADMIN_ROLE_NAME);
            info.addStringPermission("*:*:*");
            return info;
        }

        // 赋予角色和资源授权
//       Set<Role> roles = ShiroUtil.getSubjectRoles();
        Result<Set<Role>> result = roleServiceClient.getUserOkRoleList(userId);
        if(result.isSuccess()){
            Set<Role> roles = result.getData();
                    roles.forEach(role -> {
                        info.addRole(role.getName());
                        role.getMenus().forEach(menu -> {
                            String perms = menu.getPerms();
                            if (menu.getStatus() == StatusEnum.OK.getCode()
                                    && StringUtils.isNotEmpty(perms) && !perms.contains("*")) {
                                info.addStringPermission(perms);
                            }
                        });
                    });
        } else {
            throw new UnknownAccountException();
        }

        return info;
    }

    /**
     * 认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 获取数据库中的用户名密码
        Result<User> result = userServiceCient.findByUserName(token.getUsername());
        if (result.isSuccess()) {
            User user = result.getData();
            // 判断用户名是否存在
            if (user == null) {
                throw new UnknownAccountException();
            }else if(user.getStatus() == StatusEnum.FREEZED.getCode()) {
                throw new LockedAccountException();
            }
            // 对盐进行加密处理
            ByteSource salt = ByteSource.Util.bytes(user.getSalt());
            /* 传入密码自动判断是否正确
             * 参数1：传入对象给Principal
             * 参数2：正确的用户密码
             * 参数3：加盐处理
             * 参数4：固定写法
             */
            return new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
        } else {
            throw new ResultException(ResultEnum.SYS_ERROR);
        }
    }

    /**
     * 自定义密码验证匹配器
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        setCredentialsMatcher(new SimpleCredentialsMatcher() {
            @Override
            public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
                UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
                SimpleAuthenticationInfo info = (SimpleAuthenticationInfo) authenticationInfo;
                // 获取明文密码及密码盐
                String password = String.valueOf(token.getPassword());
                String salt = CodecSupport.toString(info.getCredentialsSalt().getBytes());

                return equals(ShiroUtil.encrypt(password, salt), info.getCredentials());
            }
        });
    }
}
