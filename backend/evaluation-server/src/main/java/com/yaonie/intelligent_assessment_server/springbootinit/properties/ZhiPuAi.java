package com.yaonie.intelligent_assessment_server.springbootinit.properties;


import com.zhipu.oapi.ClientV4;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-22 15:22
 * @Author 武春利
 * @CreateTime 2024-08-22
 * @ClassName ZhiPuAi
 * @Project backend
 * @Description : 智普AI 配置
 */
@Component
@ConditionalOnProperty(prefix = "ais.zhi-pu", name = "apiKey")
@ConfigurationProperties(prefix = "ais.zhi-pu")
@Data
public class ZhiPuAi {

    private String apiKey;
    private Float temperature;

    @Bean
    public ClientV4 clientV4() {
        ClientV4 clientV4 = new ClientV4.Builder(apiKey)
                .networkConfig(300, 100, 100, 100, TimeUnit.SECONDS)
                .connectionPool(new okhttp3.ConnectionPool(5, 5, TimeUnit.MINUTES))
                .build();
        return clientV4;
    }
}
