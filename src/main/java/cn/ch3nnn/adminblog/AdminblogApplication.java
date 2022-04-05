package cn.ch3nnn.adminblog;

import cn.ch3nnn.adminblog.utils.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chentong
 */
@EnableCaching // 启用缓存功能
@EnableScheduling // 开启定时任务功能
@EnableTransactionManagement // 开启事务
@SpringBootApplication
public class AdminblogApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext applicationContext = SpringApplication.run(AdminblogApplication.class, args);
        SpringUtils.setApplicationContext(applicationContext);

    }

}
