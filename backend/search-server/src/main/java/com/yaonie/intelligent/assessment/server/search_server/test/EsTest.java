package com.yaonie.intelligent.assessment.server.search_server.test;


import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.App;
import com.yaonie.intelligent.assessment.server.search_server.esdao.AppEsDao;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-14 22:05
 * @Author 武春利
 * @CreateTime 2024-09-14
 * @ClassName EsTest
 * @Project backend
 * @Description : 测试ES
 */
@SpringBootTest
public class EsTest {
    @Resource
    private AppEsDao appEsDao;

    @Test
    void testAdd(){
        App app = new App();
        app.setId(3L);
        app.setAppName("测试一下");
        app.setAppDesc("测试一下, 打广告");
        app.setAppIcon("没有");
        app.setAppType(0);
        app.setScoringStrategy(0);
        app.setReviewStatus(0);
        app.setReviewMessage("没有");
        app.setReviewerId(1L);
        app.setReviewTime(new Date());
        app.setUserId(1L);
        app.setCreateTime(new Date());
        app.setUpdateTime(new Date());
        app.setIsDelete(0);
        appEsDao.save(app);
    }

    @Test
    public void test() {
        System.out.println(appEsDao.findByUserId(1L));
    }
}
