package com.yaonie.intelligent.assessment.server.chat_server.websocket.utils;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-18 15:21
 * @Author 武春利
 * @CreateTime 2024-09-18
 * @ClassName ChannelAttrUtil
 * @Project backend
 * @Description : TODO
 */
public class NettyUtil {
    @AllArgsConstructor
    public static enum TypeEnum {
        SESSION_ID("SESSION"),
        IP("IP");
        private final String key;
    }

    public static void setChannelAttr(ChannelHandlerContext ctx, TypeEnum key, String value) {
        ctx.channel().attr(AttributeKey.valueOf(key.key)).set(value);
    }

    public static void setChannelAttr(Channel channel, TypeEnum key, String value) {
        channel.attr(AttributeKey.valueOf(key.key)).set(value);
    }

    public static String getChannelAttr(ChannelHandlerContext ctx, TypeEnum key) {
        return ctx.channel().attr(AttributeKey.valueOf(key.key)).get().toString();
    }

    public static String getChannelAttr(Channel channel, TypeEnum key) {
        return channel.attr(AttributeKey.valueOf(key.key)).get().toString();
    }
}
