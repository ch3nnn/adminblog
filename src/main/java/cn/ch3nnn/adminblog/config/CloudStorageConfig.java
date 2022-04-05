package cn.ch3nnn.adminblog.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云 云存储配置类
 *
 * @Author ChenTong
 * @Date 2021/10/9 13:14
 */

@Data
@Configuration
public class CloudStorageConfig {
    /**
     * 七牛域名domain
     */
    @Value("${qiniu.domain}")
    private String qiniuDomain;
    /**
     * 七牛ACCESS_KEY
     */
    @Value("${qiniu.accessKey}")
    private String qiniuAccessKey;
    /**
     * 七牛SECRET_KEY
     */
    @Value("${qiniu.secretKey}")
    private String qiniuSecretKey;
    /**
     * 七牛空间名
     */
    @Value("${qiniu.bucketName}")
    private String qiniuBucketName;


}
