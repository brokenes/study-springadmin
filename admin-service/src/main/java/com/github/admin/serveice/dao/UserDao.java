package com.github.admin.serveice.dao;

import com.github.admin.common.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

//@Mapper
public interface UserDao {

    /***
     * 当一个参数的时候，@param注解可以省略，参数大于2个或者
     * 以上的时候，需添加@param注解
     * @param userName
     * @return
     */
    User findByUserName(@Param("userName") String userName);


    User findUserById(Long id);

    long getPageCount(Map<String, Object> map);

    List<User> getPageList(Map<String, Object> map);

    Integer updateUser(User user);

    Integer saveUser(User user);

    Integer findUserCountByUserName(String userName);

}
