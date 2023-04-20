package com.github.admin.serveice.server;

import com.github.admin.common.domain.Menu;
import com.github.admin.common.request.MenuRequest;
import com.github.admin.common.util.Result;
import java.util.List;
import java.util.Set;


public interface MenuService {

    Result<List<Menu>> findAll();

    Result<List<Menu>> findAll(MenuRequest menuRequest);

    Result<List<Menu>> findListByPidAndId(Long pid, Long id);

    Result<Integer> getSortMax(Long pid);

    Result<Menu> findMenuByPid(Long pid);

    Result<Integer> saveMenu(Menu menu);

    Result<Menu> findMenuById(Long id);

    Result<Integer> deleteMenuById(Long id);

    Result<Integer> updateMenuStatus(List<Menu> list);

    Result<Integer> updateMenu(Menu menu);

    Result<Set<Menu>> getMenusByRoleId(Long roleId);


    Result<List<Menu>> getListBySortOk();
}
