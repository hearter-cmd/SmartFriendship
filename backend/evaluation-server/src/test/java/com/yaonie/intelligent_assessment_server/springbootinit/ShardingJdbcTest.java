package com.yaonie.intelligent_assessment_server.springbootinit;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yaonie.intelligent_assessment_server.model.entity.UserAnswer;
import com.yaonie.intelligent_assessment_server.springbootinit.service.UserAnswerService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-29 23:14
 * @Author 武春利
 * @CreateTime 2024-08-29
 * @ClassName com.yaonie.intelligent_assessment_server.springbootinit.ShardingJdbcTest
 * @Project backend
 * @Description : TODO
 */
@SpringBootTest
public class ShardingJdbcTest {

    @Resource
    private UserAnswerService userAnswerService;

    @Test
    void test() {

        UserAnswer userAnswer1 = new UserAnswer();

        userAnswer1.setAppId(1L);
        userAnswer1.setUserId(1L);
        userAnswer1.setChoices("1");
        userAnswerService.save(userAnswer1);

        UserAnswer userAnswer2 = new UserAnswer();
        userAnswer2.setAppId(2L);
        userAnswer2.setUserId(1L);
        userAnswer2.setChoices("2");
        userAnswerService.save(userAnswer2);

        UserAnswer userAnswerOne = userAnswerService.getOne(Wrappers.lambdaQuery(UserAnswer.class).eq(UserAnswer::getAppId, 1L));
        System.out.println(JSONUtil.toJsonStr(userAnswerOne));

        UserAnswer userAnswerTwo = userAnswerService.getOne(Wrappers.lambdaQuery(UserAnswer.class).eq(UserAnswer::getAppId, 2L));
        System.out.println(JSONUtil.toJsonStr(userAnswerTwo));
    }
}

