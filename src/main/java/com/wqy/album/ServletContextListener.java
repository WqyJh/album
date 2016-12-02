package com.wqy.album;

import com.wqy.album.dao.AlbumDA;
import com.wqy.album.dao.DBConfig;
import com.wqy.album.dao.DBManager;
import com.wqy.album.dao.UserDA;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by wqy on 16-12-1.
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        System.out.println(context.getContextPath());
        System.out.println(context.getRealPath("/album"));
        System.out.println(context.getRealPath("/"));
        String dbConfigFile = context.getInitParameter("db_config_file");
        try {
            DBManager.initialize(dbConfigFile);
            Connection connection = DBManager.getConnection();
            context.setAttribute("DBConnection", connection);
            UserDA.initialize();
            AlbumDA.initialize();
        } catch (StatusException e) {
            e.printStackTrace();
            // TODO: 16-12-1 数据库异常
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            AlbumDA.terminate();
            UserDA.terminate();
            DBManager.terminate();
        } catch (StatusException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
