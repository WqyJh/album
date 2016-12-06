package com.wqy.album.servlet;

import com.wqy.album.StatusCode;
import com.wqy.album.dao.UserDA;
import com.wqy.album.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wqy on 16-12-2.
 */
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("register: " + username + ":" + password);
        User user = new User(username, password);
        int code = UserDA.create(user);
        if (code == StatusCode.SUCCESS) {
            getServletContext().getRequestDispatcher("/login_action").forward(req, resp);
        } else {
            // TODO: 16-12-6 Add an error page or response
            resp.getWriter().write("<h1>Register failed:</h1>" + code);
        }
    }
}
