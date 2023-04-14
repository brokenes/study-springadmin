package com.github.admin.serveice.server.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.admin.common.domain.User;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.UserRequest;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.dao.UserDao;
import com.github.admin.serveice.server.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private  UserDao userDao;

    @Override
    public Result<User> findByUserName(String userName) {
        LOGGER.info("查询用户信息userName:{}",userName);
        User user = userDao.findByUserName(userName);
        if(user == null){
            LOGGER.error("当前用户名称userName:{}查询用户信息不存在",userName);
            return Result.fail("404","当前用户不存在");
        }
        return Result.ok(user);
    }

    @Override
    public Result<DataPage<User>> getPageList(UserRequest userRequest) {
        Integer pageNo = userRequest.getPageNo() == null ? 1 : userRequest.getPageNo();
        Integer pageSize = userRequest.getPageSize() == null ? 1 : userRequest.getPageSize();
        Map<String,Object> map = BeanUtil.beanToMap(userRequest);
        DataPage<User> dataPage = new DataPage<User>(pageNo,pageSize);
        int startIndex = dataPage.getStartIndex();
        int endIndex = dataPage.getEndIndex();
        map.put("startIndex",startIndex);
        map.put("endIndex",endIndex);
        LOGGER.info("用户分页查询当前第:{}页,每页数据显示:{}条数据,mysql查询startIndex:{},endIndex:{}", pageNo,pageSize,startIndex,endIndex);
        long totalCount = userDao.getPageCount(map);
        List<User> list = userDao.getPageList(map);
        LOGGER.info("用户查询数据totalCount:{},查询请求参数:{}",totalCount,userRequest);
        dataPage.setTotalCount(totalCount);
        dataPage.setDataList(list);
        return Result.ok(dataPage);

    }
}
