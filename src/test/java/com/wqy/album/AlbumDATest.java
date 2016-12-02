package com.wqy.album;

import com.wqy.album.dao.AlbumDA;
import com.wqy.album.dao.DBManager;
import com.wqy.album.dao.UserDA;
import com.wqy.album.model.Photo;
import com.wqy.album.model.User;
import com.wqy.album.util.Common;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wqy on 16-12-1.
 */
public class AlbumDATest {
    @BeforeClass
    public static void initialize() throws StatusException, SQLException, IOException, ClassNotFoundException {
        DBManager.initialize("db.properties");
        UserDA.initialize();
        AlbumDA.initialize();
    }

    /**
     * 不使用 terminate() 方法
     * 因为 DBManager 和 DAO 类不能正常处理并发访问
     * 多个测试并行的时候，先结束的那个测试会释放资源
     * 导致访问异常
     */
    public static void terminate() throws StatusException, SQLException {
        AlbumDA.terminate();
        UserDA.terminate();
        DBManager.terminate();
    }

    @Test
    public void testCreate() {
        Photo photo = new Photo(Common.makeString(20), 1);
        assertEquals(StatusCode.SUCCESS, AlbumDA.create(photo));
    }

    @Test
    public void testFindByUser() {
        int userId = 1;
        User user = UserDA.find(userId);
        List<Photo> photos = AlbumDA.find(user);
        assertNotNull(photos);
        photos.stream().forEach(photo -> {
            assertNotNull(photo);
            assertEquals(userId, photo.getUserId());
        });
    }

    @Test
    public void testFindById() {
        Photo photo = AlbumDA.find(3);
        assertNotNull(photo);
        assertEquals(3, photo.getId());
    }

    @Test
    public void testFindLimitOffset() {
        User user = UserDA.find(1);
        List<List<Photo>> photoLists = new ArrayList<>();
        List<Photo> photos = null;
        int limit = 5;
        int offset = 0;
        while ((photos = AlbumDA.find(user, limit, offset)) != null && photos.size() > 0) {
            photoLists.add(photos);
            offset += 5;
        }

        List<Photo> ps = new LinkedList<>();
        photoLists.stream().forEachOrdered(list -> {
            list.stream().forEachOrdered(photo -> {
                ps.add(photo);
            });
        });
        List<Photo> allPhotos = AlbumDA.find(user);
        assertEquals(allPhotos.size(), ps.size());

        Comparator c = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Photo p1 = (Photo) o1;
                Photo p2 = (Photo) o2;
                return p1.getId() - p2.getId();
            }
        };
        allPhotos.sort(c);
        ps.sort(c);

        for (int i = 0; i < allPhotos.size(); i++) {
            assertEquals(0, c.compare(allPhotos.get(i), ps.get(i)));
        }
    }

    @Test
    public void testDelete() {
        int userId = 2;
        Photo photo = new Photo(Common.makeString(20), userId);
        assertEquals(StatusCode.SUCCESS, AlbumDA.create(photo));

        Photo p = AlbumDA.find(photo.getFilename());
        assertEquals(userId, p.getUserId());

        assertEquals(StatusCode.SUCCESS, AlbumDA.delete(p.getId()));

        assertNull(AlbumDA.find(p.getId()));
    }
}
