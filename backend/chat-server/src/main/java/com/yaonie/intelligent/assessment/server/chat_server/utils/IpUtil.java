package com.yaonie.intelligent.assessment.server.chat_server.utils;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.area.GaoDeArea;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-18 14:44
 * @Author 武春利
 * @CreateTime 2024-09-18
 * @ClassName IpUtil
 * @Project backend
 * @Description : TODO
 */
@Slf4j
public class IpUtil implements DisposableBean {
    private static final String URL = "https://restapi.amap.com/v3/ip?ip=%s&output=json&key=%s";
    /**
     * '
     * 异步执行获取归属地的操作, 防止阻塞
     */
    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(
            1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(500),
            new NamedThreadFactory("Get-Area-ByIp-")
    );
    @Value("${area.gaoDe.key}")
    private static String key;

    public static GaoDeArea getIpAreaByGaoDe(String ip) {
        Future<GaoDeArea> submit = EXECUTOR.submit(() -> {
            GaoDeArea area;
            for (int i = 0; i < 3; i++) {
                String url = String.format(URL, ip, key);
                String res = HttpUtil.get(url);
                area = JSONUtil.toBean(res, GaoDeArea.class);
                if ("OK".equals(area.getInfo())) {
                    return area;
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        });
        try {
            return submit.get();
        } catch (InterruptedException | ExecutionException e) {
            throw null;
        }
    }

    @Override
    public void destroy() throws Exception {
        EXECUTOR.shutdown();
        if (EXECUTOR.awaitTermination(60, TimeUnit.SECONDS)) {
            if (log.isErrorEnabled()) {
                log.error("ExecutorService shutdown timeout, force shutdown");
                EXECUTOR.shutdownNow();
            }
        }
    }
}
