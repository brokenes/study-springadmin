package com.github.admin.api.controller;

import com.github.admin.common.config.ProjectProperties;
import com.github.admin.common.enums.ResultEnum;
import com.github.admin.common.exception.ResultException;
import com.github.admin.common.util.CaptchaUtil;
import com.github.admin.common.util.SpringContextUtil;
import com.github.admin.common.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @GetMapping(value = {"/login","/"})
    public String login(Model model){
        ProjectProperties properties = SpringContextUtil.getBean(ProjectProperties.class);
        boolean isCaptcha = properties.isCaptchaOpen();
        LOGGER.info("当前验证码是否开启isCaptcha:{}",isCaptcha);
        model.addAttribute("isCaptcha", properties.isCaptchaOpen());
        return "/login";
    }

    /**
     * 验证码图片
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置响应头信息，通知浏览器不要缓存
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "-1");
        response.setContentType("image/jpeg");
        // 获取验证码
        String code = CaptchaUtil.getRandomCode();
        LOGGER.info("当前用户登录随机验证码code:{}",code);
        // 将验证码输入到session中，用来验证
        request.getSession().setAttribute("captcha", code);
        // 输出到web页面
        ImageIO.write(CaptchaUtil.genCaptcha(code), "jpg", response.getOutputStream());
    }


    @PostMapping("/login")
    public ResultVo login(String userName,String password,String captcha, String rememberMe){
        ProjectProperties properties = SpringContextUtil.getBean(ProjectProperties.class);
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
            LOGGER.error("当前用户或密码为空,用户名称-userName:{},密码-password:{}",password);
            throw new ResultException(ResultEnum.USER_NAME_PWD_NULL);
        }
        boolean isCaptcha = properties.isCaptchaOpen();
        LOGGER.info("当前系统配置是否需要验证码登录,isCaptche:{},用户输入验证码为,captcha:{}",isCaptcha,captcha);
        if(isCaptcha){
            if(StringUtils.isBlank(captcha)){
                LOGGER.error("当前用户没有输入验证码");
                throw new ResultException(ResultEnum.USER_CAPTCHA_ERROR);
            }
        }

        return null;

    }
}
