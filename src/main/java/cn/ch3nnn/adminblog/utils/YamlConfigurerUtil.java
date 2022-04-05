package cn.ch3nnn.adminblog.utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

/**
 * 配置文件工具类
 *
 * @Author ChenTong
 * @Date 2021/10/22 13:30
 */
public class YamlConfigurerUtil {

    private static Properties ymlProperties;

    /**
     * 初始化加载配置文件application.yml
     */
    public static void init() {
        Resource app = new ClassPathResource("application.yml");
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(app);
        ymlProperties = yamlPropertiesFactoryBean.getObject();
    }


    /**
     * 通过 key 获取配置文件 value 值
     *
     * @param key 字段名称
     * @return
     */
    public static String getStrYmlVal(String key) {
        init();
        return ymlProperties.getProperty(key);
    }

    /**
     * 通过 key 获取配置文件 value 数值
     *
     * @param key 字段名称
     * @return
     */
    public static Integer getIntegerYmlVal(String key) {
        init();
        return Integer.valueOf(ymlProperties.getProperty(key));
    }
}

