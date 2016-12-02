package com.wqy.album.servlet;

import com.wqy.album.dao.AlbumDA;
import com.wqy.album.dao.UserDA;
import com.wqy.album.model.Photo;
import com.wqy.album.model.User;
import com.wqy.album.util.Common;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class FileUploadServlet extends HttpServlet {
    private String albumPath = null;

    @Override
    public void init() throws ServletException {
        albumPath = getServletContext().getInitParameter("AlbumStorageDir");
        System.out.println("albumPath: " + albumPath);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        boolean isMultipartContent = ServletFileUpload.isMultipartContent(req);
//        if (!isMultipartContent) {
//            return;
//        }

//        String username = (String) req.getSession().getAttribute("username");
        String username = "root";
        User user = UserDA.find(username);
        String userDir = albumPath + "/" + username;
        File fileDir = new File(userDir);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(262144);
        factory.setFileCleaningTracker(FileCleanerCleanup.getFileCleaningTracker(this.getServletContext()));
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            List<FileItem> items = upload.parseRequest(req);
            System.out.println("size: " + items.size());
            items.stream().filter(item -> item.getFieldName().equals("fileField")).forEach(o -> {
                FileItem item = o;
                String filename = getFileName(item);
                System.out.println(filename);
                String filePath = fileDir + "/" + filename;
                System.out.println(filePath);
                File file = new File(filePath);
                try {
                    file.createNewFile();
                    item.write(file);
                    Photo photo = new Photo(filename, user);
                    AlbumDA.create(photo);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            });
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    private String getFileExt(String filename) {
        int index = filename.indexOf('.');
        if (index < 0) {
            return "";
        }
        String ext = filename.substring(filename.indexOf("."));
        return ext;
    }

    private String getFileName(FileItem item) {
        String itemName = item.getName();
        System.out.println(itemName);
        String filename = Common.makeString(16) + getFileExt(itemName);
        return filename;
    }
}
