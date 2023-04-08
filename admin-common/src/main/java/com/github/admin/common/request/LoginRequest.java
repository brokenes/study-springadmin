package com.github.admin.common.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
//@ApiModel("用户登录参数")
public class LoginRequest implements Serializable {

    @NotBlank(message = "账户不能为空")
//    @ApiModelProperty("用户/账户名称")
    private String userName;

    @NotBlank(message = "密码不能为空")
//    @ApiModelProperty("用户/账户账户密码")
    private String password;

//    @ApiModelProperty("验证码")
    private String captcha;

//    @ApiModelProperty("记住我")
    private String rememberMe;
}
