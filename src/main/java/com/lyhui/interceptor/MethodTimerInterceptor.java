package com.lyhui.interceptor;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* 方法耗时统计的拦截器
* */
public class MethodTimerInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = Logger.getLogger(MethodTimerInterceptor.class);

    //前置功能， 开始到结束，统计两个点的时间
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、定义开始时间
        long start  = System.currentTimeMillis();

        //2、将该时间存到请求域当中
        request.setAttribute("start", start);

        //记录请求日志
        LOGGER.info(request.getRequestURI() + "，请求到达");

        //返回true，才会找到下一个拦截器，如果没有下一个拦截器，则去Controller
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        //1、取出start
        long start = (long) request.getAttribute("start");

        //2、得到end
        long end = System.currentTimeMillis();

        //3、记录耗时
        long spendTime = end - start;
        if (spendTime > 1000) {
            LOGGER.warn("方法耗时严重，请及时处理，耗时：" + spendTime + "毫秒");
        } else {
            LOGGER.info("方法耗时：" + spendTime + "毫秒，速度正常");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
