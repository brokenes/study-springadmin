package com.github.admin.api.controller;

import com.github.admin.client.RoleServiceClient;
import com.github.admin.common.config.ProjectProperties;
import com.github.admin.common.domain.User;
import com.github.admin.common.enums.ResultEnum;
import com.github.admin.common.exception.ResultException;
import com.github.admin.common.util.CaptchaUtil;
import com.github.admin.common.util.Result;
import com.github.admin.common.util.ResultVoUtil;
import com.github.admin.common.util.SpringContextUtil;
import com.github.admin.common.vo.ResultVo;
import com.github.admin.util.URL;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @Resource
    private RoleServiceClient roleServiceClient;

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
    @ResponseBody
    public ResultVo login(String userName,String password,String captcha, String rememberMe){
        ProjectProperties properties = SpringContextUtil.getBean(ProjectProperties.class);
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
            LOGGER.error("当前用户或密码为空,用户名称-userName:{},密码-password:{}",password);
            throw new ResultException(ResultEnum.USER_NAME_PWD_NULL);
        }
        boolean isCaptcha = properties.isCaptchaOpen();
        LOGGER.info("当前系统配置是否需要验证码登录,isCaptche:{},用户输入验证码为,captcha:{}",isCaptcha,captcha);
        if(isCaptcha){
            Session session = SecurityUtils.getSubject().getSession();
            String sessionCaptcha = (String) session.getAttribute("captcha");
            if (StringUtils.isBlank(captcha) || StringUtils.isBlank(sessionCaptcha)
                    || !captcha.toUpperCase().equals(sessionCaptcha.toUpperCase())) {
                throw new ResultException(ResultEnum.USER_CAPTCHA_ERROR);
            }
            session.removeAttribute("captcha");
        }
        // 1.获取Subject主体对象
        Subject subject = SecurityUtils.getSubject();
        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        // 3.执行登录，进入自定义Realm类中
        try {
            // 判断是否自动登录
            if (rememberMe != null) {
                token.setRememberMe(true);
            } else {
                token.setRememberMe(false);
            }
            subject.login(token);

            // 判断是否拥有后台角色
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Result<Boolean> result = roleServiceClient.existsUserOk(user.getId());
           if (result.isSuccess() && result.getData()) {
//            if(false){
                return ResultVoUtil.success("登录成功!", new URL("/main"));
            } else {
                SecurityUtils.getSubject().logout();
                return ResultVoUtil.error("您不是后台管理员！");
            }
        }catch(LockedAccountException e) {
            return ResultVoUtil.error("该账号已被冻结!");
        }catch(AuthenticationException e) {
            return ResultVoUtil.error("用户名或密码错误!");
        }catch(Exception e){
            LOGGER.error("系统异常:",e);
            return ResultVoUtil.error("系统异常,请稍后再试!");
        }
    }
}
