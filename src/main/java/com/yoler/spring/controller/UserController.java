package com.yoler.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangyu on 2017/6/28.
 */
@Controller
public class UserController {
    private  final Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 用户登陆
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login() {
        logger.debug("login开始");
        return new ModelAndView("login");
    }

    @RequestMapping(value = "loginSuccess", method = RequestMethod.POST)
    public ModelAndView loginSuccess(String username, String password) {
        if (this.checkParams(new String[]{username, password})) {
            ModelAndView mav = new ModelAndView("loginSuccess");
            mav.addObject("username", username);
            mav.addObject("password", password);
            return mav;
        }
        return new ModelAndView("login");
    }

    /**
     * 另一种
     *
     * @return
     */
    @RequestMapping(value = "anotherLogin", method = RequestMethod.GET)
    public String anotherLogin() {
        return "login";
    }

    /***
     * 验证参数是否为空
     * @param params
     * @return
     */
    private boolean checkParams(String[] params) {
        for (String param : params) {
            if (param == "" || param == null || param.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
