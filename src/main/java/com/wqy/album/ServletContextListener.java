package com.wqy.album;

import com.wqy.album.dao.DBConfig;
import com.wqy.album.dao.DBManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.sql.Connection;

/**
 * Created by wqy on 16-12-1.
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String dbConfigFile = context.getInitParameter("DbConfigFile");
        try {
            DBManager.initialize(dbConfigFile);
            Connection connection = DBManager.getConnection();
            context.setAttribute("DBConnection", connection);
        } catch (StatusException e) {
            e.printStackTrace();
            // TODO: 16-12-1 数据库异常
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DBManager.terminate();
        } catch (StatusException e) {
            e.printStackTrace();
        }
    }
}
