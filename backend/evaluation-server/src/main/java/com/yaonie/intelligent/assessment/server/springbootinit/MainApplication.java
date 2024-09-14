package com.yaonie.intelligent.assessment.server.springbootinit;

import com.yaonie.intelligent.assessment.server.springbootinit.properties.ZhiPuAi;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 主类（项目启动入口）
 *
 * @author 77160
 */
// todo 如需开启 Redis，须移除 exclude 中的内容
//    (exclude = {RedisAutoConfiguration.class})
@SpringBootApplication(scanBasePackages = {
        "com.yaonie.intelligent.assessment.server",
})
@MapperScan("com.yaonie.intelligent.assessment.server.springbootinit.mapper")
@EnableScheduling
// 开启AOP
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableRedisHttpSession
@EnableConfigurationProperties({
        ZhiPuAi.class
})
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
