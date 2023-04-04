package com.github.admin.serveice.server.impl;

import com.github.admin.common.domain.Role;
import com.github.admin.common.domain.UserRole;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.dao.RoleDao;
import com.github.admin.serveice.dao.UserRoleDao;
import com.github.admin.serveice.server.RoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Resource
    private RoleDao roleDao;

    @Resource
    private UserRoleDao userRoleDao;

    /***
     * SELECT
     * 	role0_.id AS col_0_0_
     * FROM
     * 	sys_role role0_
     * 	LEFT OUTER JOIN sys_user_role users1_ ON role0_.id = users1_.role_id
     * 	LEFT OUTER JOIN sys_user user2_ ON users1_.user_id = user2_.id
     * 	AND ( user2_.STATUS != 3 )
     * WHERE
     * 	( role0_.STATUS != 3 )
     * 	AND user2_.id =?
     * 	AND role0_.STATUS =?
     * 	LIMIT ?
     * @param userId
     * @return
     */

    @Override
    public Result<Boolean> existsUserOk(Long userId) {
        List<UserRole> list =  userRoleDao.findByUserId(userId);
        if(CollectionUtils.isEmpty(list)){
            LOGGER.error("当前userId:{}查询用户角色不存在",userId);
            return Result.fail("401","当前用户对应的角色不存在");
        }
        for(UserRole userRole:list){
            Long roleId = userRole.getRoleId();
            Role role = roleDao.findByRoleId(roleId);
            LOGGER.info("当前roleId:{} 查询角色返回结果role:{}",roleId,role);
            if(role != null){
                Boolean isExists = true;
                return Result.ok(isExists);
            }
        }
        return Result.fail("401","当前用户对应的角色不存在");
    }
}
