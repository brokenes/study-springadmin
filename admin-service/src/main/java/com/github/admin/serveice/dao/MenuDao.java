package com.github.admin.serveice.dao;

import com.github.admin.common.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MenuDao {

     List<Menu> findAll();

     Menu findById(Long menuId);

     List<Menu> findByCondition(Map<String,Object> map);

     List<Menu> findListByPidAndNotId(@Param("pid")Long pid,@Param("id")Long id);

     Integer getSortMax(Long pid);

     List<Menu> findMenuByPid(Long pid);

     Integer save(Menu menu);

     Integer update(Menu menu);

     Integer deleteById(Long id);

}
