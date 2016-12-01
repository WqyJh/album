package com.wqy.album.filter;

import com.wqy.album.StatusException;
import com.wqy.album.dao.UserDA;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by wqy on 16-12-1.
 */
public class AuthenFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            UserDA.initialize();
        } catch (StatusException e) {
            // 数据库连接异常
            e.printStackTrace();
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        if (username != null) {
            // 已登录，继续访问
            chain.doFilter(request, response);
        } else {
            // 未登录，重定向到登录页面
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect("/login");
        }
    }

    public void destroy() {
        try {
            UserDA.terminate();
        } catch (StatusException e) {
            // 数据库关闭异常
            e.printStackTrace();
        }
    }
}
