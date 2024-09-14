package com.yaonie.intelligent.assessment.feign.encoder;

import cn.hutool.core.util.TypeUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yaonie.intelligent.assessment.server.exception.BusinessException;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@Slf4j
public class OpenFeigenDecoder implements Decoder {  //实现Decoder接口
    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        try {
            //判断响应是否为null，对应着openfeign请求有没有成功
            if (response.body() == null) {
                throw new DecodeException(response.status(), "没有数据响应", response.request());
            }
            //将响应的数据转成 字符串
            String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
            //再次将响应的数据转换成JSON类型
            JSONObject jsonObject = JSONUtil.parseObj(bodyStr, true);

            //获取json对象的code值，并判断是否为0，如果不为0抛出异常
            int code = (int) jsonObject.get("code");
            if (code != 0) {
                throw new BusinessException(code, jsonObject.get("msg").toString());
            }

            //从json对象中获取data并转成 string类型
            String dataStr = jsonObject.get("data").toString();

            //获取返回的type的类型（是data的类型）的全路径名（引用类型）
            Class<?> aClass = TypeUtil.getClass(type);
            Object result = null;
            //判断返回类型是否是List类型（因为list类型需要手动转化，List类型中有泛型，Object类型不能转）
            if (aClass.isAssignableFrom(List.class)) {
                //获取到List泛型中的类型
                Type typeArgument = TypeUtil.getTypeArgument(type);
                //将Type类型的 typeArgument  转化成Class类型
                Class<?> aClass2 = TypeUtil.getClass(typeArgument);
                //将data字符串转换 成  List泛型中的类型，并组合成List类型
                result = JSONUtil.toList(dataStr, aClass2);
            } else {
                if (aClass.getName().startsWith("java.lang")) {
                    // 是Java自带类型
                    result=jsonObject.get("data");
                } else {
                    // 不是Java自带类型
                    result = JSONUtil.toBean(dataStr, aClass);
                }
            }
            return result;
        } catch (Throwable ex) {
            throw new DecodeException(1111, "openfeign解析错误", response.request());
        }
    }
}
