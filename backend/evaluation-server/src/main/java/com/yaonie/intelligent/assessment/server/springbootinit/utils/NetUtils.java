package com.yaonie.intelligent.assessment.server.springbootinit.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.area.GaoDeArea;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 网络工具类
 */
@Slf4j
public class NetUtils {
    /**
     * 获取IP归属地的线程池
     */
    private static ExecutorService executorService =
            new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(500),
                    new NamedThreadFactory("refresh-ipDetail"));

    /**
     * 获取ip地址
     */

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }

    // 网关中获取Ip地址
    public static String getGatewayIpAddress(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        }
        return ip;
    }

    /**
     * 解析ip归属地工具, 防止限流, 最多尝试3次, 并且等待2秒
     *
     * @param ip ip地址
     * @return IpDetail
     */
    public static GaoDeArea getIpDetail(String ip) {
        for (int i = 0; i < 3; i++) {
            GaoDeArea ipDetail = getIpAreaByGaoDe(ip);
            if (Objects.nonNull(ipDetail)) {
                return ipDetail;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static final String URL = "https://restapi.amap.com/v3/ip?ip=%s&output=json&key=%s";

    @Value("${area.gaoDe.key}")
    private static String key;

    public static GaoDeArea getIpAreaByGaoDe(String ip) {
        try {
            String url = String.format(URL, ip, key);
            String res = HttpUtil.get(url);
            GaoDeArea area = JSONUtil.toBean(res, GaoDeArea.class);
            return area;
        } catch (Exception e) {
            log.info("evaluation-server-解析ip地址失败, ip: {}", ip);
            return null;
        }
    }

}
