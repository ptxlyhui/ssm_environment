package com.lyhui.interceptor;

import com.lyhui.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* 会话拦截器
* */
public class SessionInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = Logger.getLogger(SessionInterceptor.class);
    //检查当前会话是否存在user，如果有就放行，没有就不放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object user = request.getSession().getAttribute("SESSION_USER");
        if (user == null) {

            LOGGER.warn("您不具备权限，请先登陆");
            return false;
        }
        if (user instanceof User) {
            //再去数据库检查其身份对不对，冻结了
            User u  = (User) user;
            u.setPassword(null);
            request.getSession().setAttribute("SESSION_USER",u);
            LOGGER.info(u.getUsername() + "处于登录状态，可以执行操作。");
            return true;
        } else {
            LOGGER.warn("请不要搞事，请先登陆");
            return false;
        }

    }
}
