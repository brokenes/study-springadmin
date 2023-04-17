package com.github.admin.serveice.server.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.github.admin.common.domain.AdminLogger;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.AdminLoggerRequest;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.dao.AdminLoggerDao;
import com.github.admin.serveice.server.AdminLoggerService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AdminLoggerServiceImpl implements AdminLoggerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminLoggerServiceImpl.class);


    @Resource
    private AdminLoggerDao adminLoggerDao;

    @Override
    public Result<DataPage<AdminLogger>> page(AdminLoggerRequest request) {
        if(StringUtils.isBlank(request.getAsc()) && StringUtils.isBlank(request.getOrderByColumn())){
            request.setAsc("desc");
            request.setOrderByColumn("create_date");
        }
        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();
        Map<String,Object> map = BeanUtil.beanToMap(request);
        DataPage<AdminLogger> dataPage = new DataPage<AdminLogger>(pageNo,pageSize);
        int startIndex = dataPage.getStartIndex();
        int endIndex = dataPage.getPageSize();
        map.put("start",startIndex);
        map.put("offset",endIndex);
        LOGGER.info("日志分页查询当前第:{}页,每页数据显示:{}条数据,mysql查询startIndex:{},endIndex:{}", pageNo,pageSize,startIndex,endIndex);
        long totalCount = adminLoggerDao.pageLogListCount(map);
        List<AdminLogger> list = adminLoggerDao.pageLogList(map);
        LOGGER.info("日志查询数据totalCount:{},查询请求参数:{}",totalCount, JSON.toJSONString(request));
        dataPage.setTotalCount(totalCount);
        dataPage.setDataList(list);
        return Result.ok(dataPage);
    }

    @Override
    public Result<Integer> insertSelective(AdminLogger adminLogger) {
        Integer status = adminLoggerDao.insertSelective(adminLogger);
        if(status != 1){
            LOGGER.error("日志插入失败");
            return Result.fail("500","日志插入失败");
        }
        return Result.ok(status);
    }


    @Override
    public Result<Integer> clearLogger() {
        Integer status = adminLoggerDao.clearLogger();
        if(status == 0){
            LOGGER.error("清空日志失败,status:{}",status);
            return Result.fail("500","清空日志失败!");
        }
        return Result.ok(status);
    }

    @Override
    public Result<Integer> deleteByPrimaryKey(Long id) {
        if(id == null){
            LOGGER.error("请求参数id为空!");
            return Result.fail("405","请求参数为空!");
        }
        Integer status = adminLoggerDao.deleteByPrimaryKey(id);
        if(status != 1){
            LOGGER.error("删除日志失败,id:{}",id);
            return Result.fail("500","删除日志失败!");
        }
        return Result.ok(status);
    }

    @Override
    public Result<AdminLogger> selectByPrimaryKey(Long id) {
        if(id == null){
            LOGGER.error("请求参数id为空!");
            return Result.fail("405","请求参数为空!");
        }
        AdminLogger adminLogger = adminLoggerDao.selectByPrimaryKey(id);
        if(adminLogger == null){
            LOGGER.error("查询日志失败,id:{}",id);
            return Result.fail("500","查询数据不存在!");
        }
        return Result.ok(adminLogger);
    }
}
