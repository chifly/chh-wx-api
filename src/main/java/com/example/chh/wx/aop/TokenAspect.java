package com.example.chh.wx.aop;

import com.example.chh.wx.common.util.R;
import com.example.chh.wx.config.shiro.ThreadLocalToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * token的切面类，拦截所有web方法
 * @author apple
 */
@Aspect
@Component
public class TokenAspect {
    @Autowired
    private ThreadLocalToken threadLocalToken;

    @Pointcut("execution(public * com.example.chh.wx.controller.*.*(..)))")
    public void aspect() {

    }
    @Around("aspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        R r = (R) point.proceed();
        String token = threadLocalToken.getToken();
        if (token != null) {
            r.put("token", token);
            threadLocalToken.clear();
        }
        return r;
    }
}
