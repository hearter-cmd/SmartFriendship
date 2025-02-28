package com.yaonie.intelligent.assessment.system.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // 设置响应状态码
        response.setStatus(HttpServletResponse.SC_OK);
        // 设置响应内容类型
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        // 自定义返回信息
        writer.write("{\"message\": \"退出登录成功\"}");
        writer.flush();
        writer.close();
    }
}