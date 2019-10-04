package utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BaseHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @SuppressWarnings("all")
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    @SuppressWarnings("all")
    public static <T> T getBean(String beanName, Class<T> type) {
        return (T) applicationContext.getBean(beanName, type);
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        BaseHolder.applicationContext = applicationContext;
    }

}
