package com.hush0k.shareRoute.auth.internal.util;

import jakarta.servlet.http.HttpServletRequest;

public class IpAddressResolver {

    private static final String[] HEADERS_TO_CHECK = {
            "X-Forwarded-For",
            "X-Real-IP",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP"
    };

    public static String resolve(HttpServletRequest request) {
        for (String header : HEADERS_TO_CHECK) {
            String value = request.getHeader(header);
            if (value != null && !value.isBlank() && !"unknown".equalsIgnoreCase(value)) {
                return value.split(",")[0].trim();
            }
        }

        return request.getRemoteAddr();
    }
}
