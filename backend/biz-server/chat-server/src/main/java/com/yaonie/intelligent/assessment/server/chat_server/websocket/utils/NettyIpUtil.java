package com.yaonie.intelligent.assessment.server.chat_server.websocket.utils;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaders;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-18 14:58
 * @Author 武春利
 * @CreateTime 2024-09-18
 * @ClassName NettyIpUtil
 * @Project backend
 * @Description : NettyIp工具类
 */
public class NettyIpUtil {
    public static String parseForGetIp(ChannelHandlerContext ctx, HttpHeaders headers) {
        String ipAddress = null;
        try {
            ipAddress = headers.get("x-forwarded-for");
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = headers.get("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = headers.get("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = ((InetSocketAddress) ctx.channel().remoteAddress()).getHostString().split(":")[0];
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
}
