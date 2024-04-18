package com.admin.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;

import java.util.*;

public class JWTUtils {
    public static String get_sub(String token) {
        return get_claims(token).get("sub").asString();
    }

    private static Map<String, Claim> get_claims(String token) {
        return JWT.decode(token).getClaims();
    }
}
