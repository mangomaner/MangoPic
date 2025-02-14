package com.mango.mangopic.config;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @desc
 *
 * @author gaojun
 * @email 15037584397@163.com
 */
@Configuration
@ConfigurationProperties(prefix = "aliyun")
@Data
public class OssClientConfig {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String urlPrefix;
    @Bean
    public OSS ossClient() {
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

}
