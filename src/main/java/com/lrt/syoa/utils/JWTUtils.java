package com.lrt.syoa.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTUtils {

    private static Integer expire = 2; // token 过期时间(小时)

    public static String getToken(String userId,String role, String sign) {
        return JWT.create().withClaim("userId",userId) // 将userID保存到 token 里面,作为载荷
                .withClaim("role",role) // 将用户的角色保存到token 中
                .withExpiresAt(DateUtil.offsetHour(new Date(), expire)) // 设置token 过期时间
                .sign(Algorithm.HMAC256(sign)); // 以sign作为密钥
    }

    public static boolean isTokenExpired (DecodedJWT decodedJWT) {
        // 获取 token 的到期时间
        Date expirationTime = decodedJWT.getExpiresAt();
        // 如果 token 到期了就返回true
        return expirationTime.before(new Date());
    }


}

