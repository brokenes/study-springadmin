package com.github.admin.serveice.server;

import com.github.admin.common.domain.AdminLogger;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.AdminLoggerRequest;
import com.github.admin.common.util.Result;

public interface AdminLoggerService {


    Result<DataPage<AdminLogger>> page(AdminLoggerRequest request);

    Result<Integer> insertSelective(AdminLogger adminLogger);
}
