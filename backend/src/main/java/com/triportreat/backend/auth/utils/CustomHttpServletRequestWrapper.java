package com.triportreat.backend.auth.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final String accessToken;

    public CustomHttpServletRequestWrapper(HttpServletRequest request, String accessToken) {
        super(request);
        this.accessToken = accessToken;
    }

    @Override
    public String getHeader(String name) {
        if ("Authorization".equalsIgnoreCase(name)) {
            return "Bearer " + accessToken;
        }
        return super.getHeader(name);
    }
}
