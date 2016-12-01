package com.wqy.album.servlet;

import com.wqy.album.dao.AlbumDA;
import com.wqy.album.dao.UserDA;
import com.wqy.album.model.Photo;
import com.wqy.album.model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

public class FileUploadServlet extends HttpServlet {
    private String albumPath = null;

    @Override
    public void init() throws ServletException {
        albumPath = getServletContext().getRealPath("/album");
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

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(262144);
        factory.setFileCleaningTracker(FileCleanerCleanup.getFileCleaningTracker(this.getServletContext()));
        ServletFileUpload upload = new ServletFileUpload(factory);

        ServletInputStream is = req.getInputStream();
        File file = new File(albumPath + "124324");
        OutputStream os = new FileOutputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            os.write(line.getBytes());
        }
        os.close();
        br.close();

//        try {
//            List items = upload.parseRequest(req);
//            System.out.println("size: " + items.size());
//            items.stream().forEach(o -> {
//                FileItem item = (FileItem) o;
//                String filename = getFileName(item);
//                String filePath = String.join(albumPath, "/", username, "/", filename);
//                System.out.println(filePath);
//                File file = new File(filePath);
//                try {
//                    item.write(file);
//                    Photo photo = new Photo(filename, user);
//                    AlbumDA.create(photo);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return;
//                }
//            });
//        } catch (FileUploadException e) {
//            e.printStackTrace();
//        }
    }

    private String getFileExt(String filename) {
        String[] subs = filename.split(".");
        String ext = subs[subs.length - 1];
        return ext;
    }

    private String getFileName(FileItem item) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        Date date = new Date();
        String itemName = item.getName();
        String s = String.format("%s%d", itemName, date.getTime());
        String md5 = String.valueOf(md.digest(s.getBytes()));
        String filename = String.join(md5, ".", getFileExt(itemName));
        return filename;
    }
}
