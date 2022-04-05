package cn.ch3nnn.adminblog.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @Author ChenTong
 * @Date 2021/9/28 14:33
 */
public class SpringUtils {


    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 根据bean class 返回 实例
     *
     * @param
     * @return
     * @Date 2019-08-07 17:36
     **/
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return applicationContext.getBean(clazz);

    }
}
