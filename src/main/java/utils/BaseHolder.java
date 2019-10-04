package utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取bean对象的类
 */
@Component
@SuppressWarnings("all")
public class BaseHolder implements ApplicationContextAware {
    /**
     * spring核心容器
     */
    private static ApplicationContext applicationContext;

    /**
     * 获取核心容器中的bean对象
     *
     * @param beanName spring中bean的id
     * @param <T>      泛型
     *
     * @return bean对象
     */
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 获取核心容器中的bean对象（无需强制类型转换）
     *
     * @param beanName spring中bean的id
     * @param type     bean的class对象
     * @param <T>      泛型
     *
     * @return bean对象
     */
    public static <T> T getBean(String beanName, Class<T> type) {
        return (T) applicationContext.getBean(beanName, type);
    }

    /**
     * 设置核心容器
     *
     * @param applicationContext 核心容器
     *
     * @throws BeansException bean异常
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        BaseHolder.applicationContext = applicationContext;
    }

}
