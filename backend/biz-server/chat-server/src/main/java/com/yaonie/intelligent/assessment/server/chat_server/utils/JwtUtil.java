package com.yaonie.intelligent.assessment.server.chat_server.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-17 1:20
 * @Author 武春利
 * @CreateTime 2024-09-17
 * @ClassName JwtUtil
 * @Project backend
 * @Description : Jwt工具类
 */
@Slf4j
public class JwtUtil {
    @Value("${jwt.slat}")
    private static String slat = "39a4e2262b09439fb23e69f142514b76";
    private static final String UID_CLAIM = "uid";
    private static final String CREATE_TIME = "createTime";

    /**
     * JWT生成Token.<br/>
     * <p>
     * JWT构成: header, payload, signature
     */
    public static String createToken(Long uid) {
        // build token
        String token = JWT.create()
                // 只存一个uid信息，其他的自己去redis查
                .withClaim(UID_CLAIM, uid)
                .withClaim(CREATE_TIME, new Date())
                // signature
                .sign(Algorithm.HMAC256(slat));
        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     */
    public static Map<String, Claim> verifyToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(slat)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (Exception e) {
            log.error("decode error,token:{}", token, e);
        }
        return null;
    }


    /**
     * 根据Token获取uid
     *
     * @param token
     * @return uid
     */
    public static Long getUidOrNull(String token) {
        return Optional.ofNullable(verifyToken(token))
                .map(map -> map.get(UID_CLAIM))
                .map(Claim::asLong)
                .orElse(null);
    }
}
