package com.wqy.album.dao;

import java.io.*;
import java.util.Properties;

/**
 * Created by wqy on 16-12-1.
 */
public class DBConfig extends Properties {
    public String driver;
    public String url;
    public String username;
    public String password;

    public DBConfig(String driver, String url, String username,
                    String password) {
        super();
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DBConfig getDBConfig(String configFile) {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(configFile));
            DBConfig dbConfig = new DBConfig(
                    p.getProperty("driver"),
                    p.getProperty("url"),
                    p.getProperty("username"),
                    p.getProperty("password")
            );
            return dbConfig;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
