package com.yaonie.intelligent.assessment.server.springbootinit;

import com.yaonie.intelligent.assessment.server.springbootinit.properties.ZhiPuAi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 主类（项目启动入口）
 *
 * @author 77160
 */
// todo 如需开启 Redis，须移除 exclude 中的内容
//    (exclude = {RedisAutoConfiguration.class})
@SpringBootApplication(scanBasePackages = {
        "com.yaonie.intelligent.assessment.server",
        "com.yaonie.intelligent.assessment.ai",
        "com.yaonie.intelligent.assessment.system"
})
@EnableScheduling
// 开启AOP
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableConfigurationProperties({
        ZhiPuAi.class,
})
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
