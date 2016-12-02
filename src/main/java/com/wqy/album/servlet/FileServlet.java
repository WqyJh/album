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

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileServlet extends HttpServlet {
    private String albumPath = null;

    @Override
    public void init() throws ServletException {
        System.out.println("FileServlet: init");
        albumPath = getServletContext().getInitParameter("AlbumStorageDir");
        System.out.println("albumPath: " + albumPath);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FileServlet: doGet");
        System.out.println(req.getRequestURI());
        System.out.println(req.getRequestURL());
        String url = req.getRequestURI();
        String reg = "/file(/\\d*)?";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) {
            String idString = matcher.group(1).substring(1);
            System.out.println(idString);
            int id = Integer.valueOf(idString);
            System.out.println(id);
            Photo photo = AlbumDA.find(id);
//            String username = (String) req.getSession().getAttribute("username");
            String username = "root";
            String fullPath = albumPath + "/" + username + "/" + photo.getFilename();
            System.out.println(fullPath);
            File file = new File(fullPath);
            if (file.exists()) {
                String mime = getServletContext().getMimeType(photo.getFilename());
                resp.setContentType(mime);
                resp.addHeader("Content-Disposition", "attachment; filename=" + photo.getFilename());
                System.out.println(file.length());
                byte[] buffer = new byte[1024];
                int length;
                ServletOutputStream sos = resp.getOutputStream();
                InputStream is = new FileInputStream(file);

                while ((length = is.read(buffer)) > 0) {
                    sos.write(buffer, 0, length);
                }

                sos.flush();
                sos.close();
                is.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isMultipartContent = ServletFileUpload.isMultipartContent(req);
        if (!isMultipartContent) {
            // TODO: 16-12-2 处理空上传
            return;
        }

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
