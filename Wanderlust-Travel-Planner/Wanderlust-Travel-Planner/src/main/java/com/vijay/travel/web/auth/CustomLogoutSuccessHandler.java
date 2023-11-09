package com.vijay.travel.web.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.vijay.travel.service.JwtTokenService;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final JwtTokenService jwtTokenService;

    @Autowired
    public CustomLogoutSuccessHandler(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        Assert.notNull(request, "Request cannot be null");

        Object attribute = request.getAttribute("TOKEN");

        if (attribute instanceof Map.Entry entry
                && entry.getKey() instanceof String && entry.getValue() instanceof String token) {

            jwtTokenService.blackListJwtToken(token);
        }
    }
}
