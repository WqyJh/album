package com.wqy.album.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.wqy.album.dao.AlbumDA;
import com.wqy.album.dao.UserDA;
import com.wqy.album.model.Photo;
import com.wqy.album.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqy on 16-12-2.
 */
public class AlbumServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            return;
        }
        User user = UserDA.find(username);
        int limit = Integer.parseInt(req.getParameter("limit"));
        int offset = Integer.parseInt(req.getParameter("offset"));
        List<Photo> photoList = AlbumDA.find(user, limit, offset);
        System.out.println(req.getQueryString());
        System.out.println("limit: " + limit + "  offset: " + offset);
        if (photoList != null) {
            JsonArray urls = new JsonArray();
            photoList.forEach(photo -> {
                String url = "/file/" + photo.getId();
                urls.add(url);
            });
            System.out.println(urls.toString());
            resp.getWriter().write(urls.toString());
        }
    }
}
