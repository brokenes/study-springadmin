package com.github.admin.sensitive;

import com.alibaba.fastjson.JSON;
import com.github.admin.common.domain.User;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.util.Result;
import com.github.framework.sensitive.core.api.SensitiveUtils;

import java.util.ArrayList;
import java.util.List;

public class SensitiveTest {

    public static void main(String[] args) {
        List<User> list = new ArrayList<User>();
        User user = new User();
        user.setConfirm("12121");
        user.setEmail("aaaa@sina.com");
        user.setPhone("15888888888");
        list.add(user);
        DataPage<User> dataPage = new DataPage<>();
        dataPage.setDataList(list);
        Result<List<User>> result = Result.ok(dataPage);

        System.out.println("******************" + JSON.toJSONString(result));

        System.out.println("111################"+JSON.toJSONString(SensitiveUtils.desCopy(result)));
        System.out.println("222################"+JSON.toJSONString(SensitiveUtils.desJson(dataPage)));

    }
}
