package com.lrt.syoa.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JWTUtils {

    public static String getToken(String userId, String sign) {
        return JWT.create().withAudience(userId) // 将userID保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 两小时后 token 过期
                .sign(Algorithm.HMAC256(sign)); // 以sign作为密钥
    }

}

