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
        User user = new User(username, password);
        try {
            if (UserDA.find(user)) {
                // 登录成功
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                // TODO: 16-12-1  跳转到登录成功界面

            }
        } catch (StatusException e) {
            e.printStackTrace();
            switch (e.getCode()) {
                case StatusCode.USERNAME_MISSING:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "username cannot be empty");

                    break;
                case StatusCode.PASSWORD_MISSING:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "password cannot be empty");
                    break;
                case StatusCode.USER_NOT_FOUND:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "user not found");
            }
        }

    }
}
