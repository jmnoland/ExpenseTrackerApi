package com.jmnoland.expensetrackerapi.helpers;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {

    public static String getClientIdFromHeader(HttpServletRequest request) {
        return request.getHeader("x-client-id");
    }
}
