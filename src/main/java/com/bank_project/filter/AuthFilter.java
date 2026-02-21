package com.bank_project.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        if (requestURI.equals("/login") || requestURI.equals("/register")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            boolean sessionExpired = (session != null && session.getAttribute("user_id") == null);
            if (sessionExpired) {
                httpResponse.sendRedirect("/login?sessionExpired=true");
            } else {
                httpResponse.sendRedirect("/login");
            }
            return;
        }

        chain.doFilter(request, response);
    }

}
