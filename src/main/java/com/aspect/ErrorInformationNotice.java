package com.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.service.ViewService;

/**
 * 对异常信息进行通知处理的类
 */
@Component
@Aspect
@SuppressWarnings("all")
public class ErrorInformationNotice {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorInformationNotice.class);

    @Autowired
    private ViewService viewService;

    /**
     * 对供外界访问的service层方法进行代理增强
     *
     * @param proceedingJoinPoint 连接点对象
     *
     * @return 有异常->true 无异常->false
     */
    @Around("execution(public * com.service.impl.*ServiceImpl.*(..))")
    public Object ErrorInformationProcess(ProceedingJoinPoint proceedingJoinPoint) {
        Object returnValue = null;
        try {
            //获取执行方法的参数
            final Object[] args = proceedingJoinPoint.getArgs();
            //执行被增强的方法
            returnValue = proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            //捕捉异常，记录异常，显示异常信息给用户
            LOGGER.error("", throwable);
            viewService.showErrorInformation(throwable.getMessage());
            return true;
        }
        return returnValue;
    }
}
