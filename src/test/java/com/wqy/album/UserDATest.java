package com.wqy.album;

import com.wqy.album.dao.DBManager;
import com.wqy.album.dao.UserDA;
import com.wqy.album.model.User;
import com.wqy.album.util.Common;
import org.junit.*;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDATest {
    @BeforeClass
    public static void initialize() throws StatusException, SQLException, IOException, ClassNotFoundException {
        DBManager.initialize("db.properties");
        UserDA.initialize();
    }

    /**
     * 不使用 terminate() 方法
     * 因为 DBManager 和 DAO 类不能正常处理并发访问
     * 多个测试并行的时候，先结束的那个测试会释放资源
     * 导致访问异常
     */
    public static void terminate() throws StatusException, SQLException {
        UserDA.terminate();
        DBManager.terminate();
    }

    @Test
    public void testCheckUserValidate() throws StatusException {
        String username = Common.makeString(10);
        String password = Common.makeString(10);
        User user = new User(username, password);
        assertEquals(StatusCode.SUCCESS, UserDA.checkUser(user));
    }

    @Test
    public void testCheckUserInvalidate() {
        assertEquals(StatusCode.USERNAME_MISSING, UserDA.checkUser(new User(null, "fdsfa")));
        assertEquals(StatusCode.USERNAME_MISSING, UserDA.checkUser(new User("", "fdsfa")));
        assertEquals(StatusCode.PASSWORD_MISSING, UserDA.checkUser(new User("fsfs", null)));
        assertEquals(StatusCode.PASSWORD_MISSING, UserDA.checkUser(new User("fsfs", "")));
    }

    @Test
    public void testCreateAndFindUser() throws StatusException {
        String username = Common.makeString(10);
        String password = Common.makeString(10);
        User user = new User(username, password);
        UserDA.create(user);
        assertEquals(StatusCode.SUCCESS, UserDA.find(user));

        User u = UserDA.find(username);
        assertNotNull(u);
        assertEquals(username, u.getUsername());
        assertEquals(password, u.getPassword());
        assertNotEquals(u.getId(), user.getId());
    }

    @Test
    public void testCreateUser() throws StatusException {
        String username = Common.makeString(10);
        String password = Common.makeString(10);
        User user = new User(username, password);
        assertEquals(StatusCode.SUCCESS, UserDA.create(user));
    }

    @Test
    public void testUpdateUser() throws StatusException {
        String username = Common.makeString(10);
        String password = Common.makeString(10);
        User user = new User(username, password);
        UserDA.create(user);

        String newPassword = Common.makeString(10);
        User newUser = new User(username, newPassword);
        assertEquals(StatusCode.SUCCESS, UserDA.update(newUser));

        User u = UserDA.find(username);
        assertNotEquals(password, u.getPassword());
        assertEquals(newPassword, u.getPassword());
    }

    @Test
    public void testDeleteUser() throws StatusException {
        String username = Common.makeString(10);
        String password = Common.makeString(10);
        User user = new User(username, password);
        UserDA.create(user);

        assertEquals(StatusCode.SUCCESS, UserDA.delete(user));
        assertEquals(StatusCode.USER_NOT_FOUND, UserDA.find(user));
    }
}
