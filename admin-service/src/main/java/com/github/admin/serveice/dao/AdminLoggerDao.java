package com.github.admin.serveice.dao;

import com.github.admin.common.domain.AdminLogger;

import java.util.List;
import java.util.Map;

public interface AdminLoggerDao {


     Long pageLogListCount(Map<String,Object> map);


     List<AdminLogger> pageLogList(Map<String,Object> map);


     Integer insertSelective(AdminLogger adminLogger);

}
