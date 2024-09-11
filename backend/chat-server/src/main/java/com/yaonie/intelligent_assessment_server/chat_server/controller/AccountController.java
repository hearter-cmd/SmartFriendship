package com.yaonie.intelligent_assessment_server.chat_server.controller;


import com.wf.captcha.ArithmeticCaptcha;
import com.yaonie.intelligent_assessment_server.chat_server.entity.vo.ResponseVO;
import com.yaonie.intelligent_assessment_server.chat_server.utils.RedisUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-11 17:16
 * @Author 武春利
 * @CreateTime 2024-09-11
 * @ClassName AccountController
 * @Project backend
 * @Description : TODO
 */
@RestController("accountController")
@RequestMapping("/account")
@Validated
@Slf4j
public class AccountController extends ABaseController {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 生成验证码
     * @return 验证码图片对象
     */
    @RequestMapping("/checkCode")
    public ResponseVO checkCode() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(100, 42);

        String captchaCodeKey = UUID.randomUUID().toString().replace("-", "");
        String captchaCode = captcha.text();
        String base64 = captcha.toBase64();
        redisUtil.setEx("asasasa", captchaCode, 15000, TimeUnit.SECONDS );
        redisUtil.setEx("asasasass", captchaCode, 15000, TimeUnit.SECONDS );
        log.info("验证码是->{}", captchaCode);
        log.info("验证码是->" + captchaCodeKey);

        HashMap<String, String> result = new HashMap<>();
        result.put("captchaCodeKey", captchaCodeKey);
        result.put("captchaCode", captchaCode);
        return getSuccessResponseVO(base64);
    }
}
