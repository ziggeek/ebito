package com.ebito.cloud.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@Component
@Data
//@ConfigurationProperties(prefix = "minio")
@ConstructorBinding
public class MinioProperties {
    @Value("${spring.minio.bucket}")
    private String bucket;
    @Value("${spring.minio.url}")
    private String url;
    @Value("${spring.minio.accessKey}")
    private String accessKey;
    @Value("${spring.minio.secretKey}")
    private String secretKey;

}
