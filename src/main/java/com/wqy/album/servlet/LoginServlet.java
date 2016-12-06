package com.wqy.album.servlet;

import com.wqy.album.StatusCode;
import com.wqy.album.StatusException;
import com.wqy.album.dao.UserDA;
import com.wqy.album.model.User;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by wqy on 16-12-1.
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("login: " + username + ":" + password);
        User user = new User(username, password);
        int code = UserDA.find(user);
        if (code == StatusCode.SUCCESS) {
            // 登录成功
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            resp.getWriter().write("<h1>Login success</h1>");
            // TODO: 16-12-6 Find a better way to redirect or dispatch the request
//            getServletContext().getRequestDispatcher("/album.jsp").forward(req, resp);
            resp.sendRedirect("/album.jsp");
        } else {
            resp.sendRedirect("/login?status=" + StatusCode.LOGIN_FAILED + "&cause=" + code);
        }
    }
}
