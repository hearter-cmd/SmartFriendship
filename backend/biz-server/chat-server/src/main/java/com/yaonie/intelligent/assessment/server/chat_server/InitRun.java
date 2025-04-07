package com.yaonie.intelligent.assessment.server.chat_server;


import io.lettuce.core.RedisException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-13 2:10
 * @Author 武春利
 * @CreateTime 2024-09-13
 * @ClassName InitRun
 * @Project backend
 * @Description :
 */
@Component
@Slf4j
public class InitRun implements ApplicationRunner {
    @Resource
    private DataSource dataSource;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            dataSource.getConnection();
            redisTemplate.opsForValue().get("test");
            log.info("服务启动成功, 数据库连接正常, Redis连接正常s");
        } catch (SQLException ex) {
            log.error("数据库错误, 请检查数据库配置");
        } catch (RedisException ex) {
            log.error("Redis错误, 请检查Redis配置");
        }


    }
}
