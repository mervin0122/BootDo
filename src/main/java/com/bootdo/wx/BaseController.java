package com.bootdo.wx;


import com.bootdo.common.utils.CookieUtil;
import com.bootdo.common.utils.RequestUtil;
import com.bootdo.common.utils.SessionUtil;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : zscat
 * @version : 1.0
 * @created on  : 2016/11/13  上午10:00
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    //page
    public static final String PAGE_REGISTER = "/user/signup"; //注册推广员
    public static final String PAGE_USERINFO = "/user/info"; //推广员信息
    public static final String PAGE_INCOMES = "/income/below-income"; //收入明细
    public static final String PAGE_BELOW_TGUSERS = "/below-user"; //下线推广员
    public static final String PAGE_ERROR = "/error"; //错误页面

    @Resource
    SessionUtil sessionUtil;

    @Resource
    UserService promotionUserService;

    /**
     * 重定向
     *
     * @param viewName
     * @return
     */
    public String redirect(String viewName) {
        return "redirect:" + viewName;
    }


    /**
     * 从cookie取 token
     *
     * @return String
     */
    public String getToken() {
        String leId = null;
        Cookie cookie = CookieUtil.getCookie(Constant.SESSION_USER_KEY);
        if (cookie != null) {
            leId = cookie.getValue();
        }
        return leId;

    }


    /**
     * 取request
     *
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return RequestUtil.getRequest();
    }

    /**
     * 判断一个请求是不是ajax请求
     *
     * @return Boolean
     */
    public Boolean isAjaxRequest() {
        return RequestUtil.isAjaxRequest();
    }

    /**
     * 获取用户浏览器信息
     *
     * @return String
     */
    public String getUserAgent() {
        return RequestUtil.getUserAgent();
    }

    /**
     * 获取客户端ip
     *
     * @return String ip
     */
    public String getClientIP() {
        return RequestUtil.getClientIP();
    }

    /**
     * 判断一个字符串,是不是ip字符串
     *
     * @param ipStr String
     * @return boolean
     */
    public boolean isIpString(String ipStr) {
        return RequestUtil.isIpString(ipStr);
    }

    public UserDO getCurrentUser() {
        return sessionUtil.getCurrentUser();
    }

//    public UserDO getCurrentUser(HttpServletResponse response) {
//        UserDO myPromotion = sessionUtil.getCurrentUser(); //获取当前推广员
//        if (myPromotion != null && myPromotion.getUserId() == null && org.apache.commons.lang.StringUtils.isNotEmpty(myPromotion.getWxOpenId())) { //session过期
//            UserDO query = new UserDO();
//            query.setWxOpenId(myPromotion.getWxOpenId());
//            UserDO oldPromotion = promotionUserService.selectOne(query); //如果为空,新用户
//            if (oldPromotion != null && oldPromotion.getUserId() != null) {
//                setSession(response, myPromotion);
//                return oldPromotion;
//            }
//        }
//        return myPromotion;
//    }

    public void setSession(HttpServletResponse response, UserDO user) {
        sessionUtil.setCurrentUser(response, user);
    }




}
