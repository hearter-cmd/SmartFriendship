package com.yaonie.intelligent.assessment.server.springbootinit.scoring;


import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.App;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.UserAnswer;
import com.yaonie.intelligent.assessment.server.springbootinit.scoring.impl.ScoringStrategyConfig;
import com.yaonie.intelligent.assessment.server.springbootinit.utils.ZhiPuUtils;
import jakarta.annotation.Resource;
import org.apache.poi.util.StringUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-19 0:48
 * @Author 武春利
 * @CreateTime 2024-08-19
 * @ClassName ScoringStrategyContext
 * @Project backend
 * @Description : 评分策略执行器
 */
@Service
public class ScoringStrategyExecutor implements ScoringStrategy{

    @Resource
    private List<ScoringStrategy> scoringStrategyList;

    @Resource
    private ZhiPuUtils zhiPuUtils;

    private static final String BASE_CAFFEINE_KEY = "AI_TEST_SCORING_KEY";

    private Cache<String, String> aiResultMap = Caffeine.newBuilder()
            .maximumSize(1024)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    @Resource
    private RedissonClient redissonClient;

    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        String jsonStr = JSONUtil.toJsonStr(choices);
        // 生成Caffeine的唯一Key
        String cacheKey = getCacheKey(app.getId(), jsonStr);
        // 通过Key比对是否有相同应用相同结果的答案
        String userAnswerJsonStr = aiResultMap.getIfPresent(cacheKey);
        // 如果存在即, 字符串不为空则直接返回UserAnswer
        if (StringUtil.isNotBlank(userAnswerJsonStr)) {
            return JSONUtil.toBean(userAnswerJsonStr, UserAnswer.class);
        }

        // 获取对应结果的锁
        RLock lock = redissonClient.getLock(BASE_CAFFEINE_KEY + cacheKey);
        try {
            // 尝试获取锁, 必须设置生存时间, 否则很容易死锁
            // isLock代表是否加锁成功
            // 第一个参数 : 在时间范围内进行重试, 超时了就不会重试了
            // 第二个参数 : 锁的存在时长
            // 第三个参数 : 时间的单位
            boolean isLock = lock.tryLock(10, 30, TimeUnit.SECONDS);
            if (!isLock) {
                return null;
            }

            Integer appType = app.getAppType();
            Integer scoringStrategy = app.getScoringStrategy();
            UserAnswer userAnswer = new UserAnswer();
            for (ScoringStrategy customStrategy : scoringStrategyList) {
                if (customStrategy.getClass().isAnnotationPresent(ScoringStrategyConfig.class)) {
                    ScoringStrategyConfig annotation = customStrategy.getClass().getAnnotation(ScoringStrategyConfig.class);
                    if (annotation.appType() == appType && annotation.scoringType() == scoringStrategy) {
                        userAnswer = customStrategy.doScore(choices, app);
                        String photo = zhiPuUtils.doDrawPhoto(userAnswer.getResultDesc());
                        userAnswer.setResultPicture(photo);
                        aiResultMap.put(cacheKey, JSONUtil.toJsonStr(userAnswer));
                        return userAnswer;
                    }
                }
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "未找到对应的评分策略");
        } finally {
            // 只有在被锁的情况下才会释放锁
            if (lock.isLocked()) {
                /**
                 * 比如 A 设置了锁, 然后它过期了,
                 * B 又设置了一个锁,
                 * 现在 A 取尝试释放锁, A 可能会将 B 的锁释放掉
                 */
                // 确保这个锁, 只能本人进行释放
                if(lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }

    }

    private String getCacheKey(Long appId, String hashChoices) {
        return DigestUtil.md5Hex(appId + ":" + hashChoices);
    }
}
