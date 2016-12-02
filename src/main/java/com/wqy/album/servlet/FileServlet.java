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

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileServlet extends HttpServlet {
    private String albumPath = null;
    private Pattern idPattern = null;
    private Pattern filenamePattern = null;
    private String tmpDir = null;
    private File tmpRepo = null;
    private int sizeThreshold = 262144;
    private long maxSize = 8388608;

    @Override
    public void init() throws ServletException {
        ServletConfig config = getServletConfig();
        Enumeration<String> names = config.getInitParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            switch (name) {
                case "album_storage_path":
                    albumPath = config.getInitParameter("album_storage_path");
                    File albumDir = new File(albumPath);
                    if (!albumDir.exists()) {
                        albumDir.mkdir();
                    }
                    break;
                case "file_id_regex":
                    String idReg = config.getInitParameter("file_id_regex");
                    idPattern = Pattern.compile(idReg);
                    break;
                case "file_name_regex":
                    String filenameReg = config.getInitParameter("file_name_regex");
                    filenamePattern = Pattern.compile(filenameReg);
                case "upload_threshold":
                    sizeThreshold = Integer.valueOf(config.getInitParameter("upload_threshold"));
                    break;
                case "upload_tmp_dir":
                    tmpDir = config.getInitParameter("upload_tmp_dir");
                    tmpRepo = new File(tmpDir);
                    if (!tmpRepo.exists()) {
                        tmpRepo.mkdir();
                    }
                    break;
                case "upload_max_size":
                    maxSize = Long.valueOf(config.getInitParameter("upload_max_size"));
                    break;
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Photo photo = null;
        try {
            int id = parsePhotoId(req);
            photo = AlbumDA.find(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            String photoFileName = parsePhotoFilename(req);
            photo = AlbumDA.find(photoFileName);
        }

        if (photo != null) {
            String photoFilename = photo.getFilename();
            String username = (String) req.getSession().getAttribute("username");
            String fullPath = albumPath + "/" + username + "/" + photoFilename;

            File file = new File(fullPath);
            if (file.exists()) {
                String mime = getServletContext().getMimeType(photoFilename);
                resp.setContentType(mime);
                resp.addHeader("Content-Disposition", "attachment; filename=" + photoFilename);

                ServletOutputStream sos = resp.getOutputStream();
                InputStream is = new FileInputStream(file);
                writeStream(is, sos);
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

        String username = (String) req.getSession().getAttribute("username");
        User user = UserDA.find(username);
        File userDir = getUserDir(username);

        DiskFileItemFactory factory = new DiskFileItemFactory(sizeThreshold, tmpRepo);
        factory.setFileCleaningTracker(FileCleanerCleanup.getFileCleaningTracker(this.getServletContext()));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(maxSize);


        List<FileItem> items = null;
        try {
            items = upload.parseRequest(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
            return;
        }

        items.stream().filter(item -> item.getFieldName().equals("fileField")).forEach(item -> {
            String filename = getFileName(item);
            String fullPath = userDir.getAbsolutePath() + "/" + filename;
            File file = new File(fullPath);

            try {
                if (file.createNewFile()) {
                    item.write(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Photo photo = new Photo(filename, user);
            AlbumDA.create(photo);
        });
        getServletContext().getRequestDispatcher("/album.jsp").forward(req, resp);
    }

    private File getUserDir(String username) {
        String userDir = albumPath + "/" + username;
        File fileDir = new File(userDir);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        return fileDir;
    }

    private void writeStream(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        int length;

        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }

        os.flush();
        os.close();
        is.close();
    }

    private String parsePhotoFilename(HttpServletRequest req) {
        String uri = req.getRequestURI();
        Matcher matcher = filenamePattern.matcher(uri);
        if (matcher.matches()) {
            return matcher.group();
        }
        return null;
    }

    private int parsePhotoId(HttpServletRequest req) throws NumberFormatException {
        String uri = req.getRequestURI();
        Matcher matcher = idPattern.matcher(uri);
        if (matcher.matches()) {
            String idString = matcher.group(1);
            return Integer.valueOf(idString);
        }
        return -1;
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
        String filename = Common.makeString(16) + getFileExt(itemName);
        return filename;
    }
}
