package com.github.admin.serveice.dao;

import com.github.admin.common.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MenuDao {

     List<Menu> findAll();

     Menu findById(Long menuId);

     List<Menu> findByCondition(Map<String,String> map);

     List<Menu> findListByPidAndId(@Param("pid")Long pid,@Param("id")Long id);
}
