package com.github.admin.serveice.server;

import com.github.admin.common.domain.Menu;
import com.github.admin.common.util.Result;
import java.util.List;


public interface MenuService {

    Result<List<Menu>> findAll();

}
