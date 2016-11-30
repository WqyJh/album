package com.wqy.album;

import com.wqy.album.dao.UserDA;
import com.wqy.album.model.User;
import com.wqy.album.util.Common;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDATest {
    @Before
    public void initialize() throws StatusException {
        UserDA.initialize();
    }

    @After
    public void terminate() throws StatusException {
        UserDA.terminate();
    }

    @Test
    public void testCheckUserValidate() throws StatusException {
        String username = Common.makeString(10);
        String password = Common.makeString(10);
        User user = new User(username, password);
        UserDA.checkUser(user);
    }

    @Test
    public void testCheckUserInvalidate() {
        try {
            UserDA.checkUser(new User(null, "fdsfa"));
        } catch (StatusException e) {
            assertEquals(StatusCode.USERNAME_MISSING, e.getCode());
        }

        try {
            UserDA.checkUser(new User("", "fdsfa"));
        } catch (StatusException e) {
            assertEquals(StatusCode.USERNAME_MISSING, e.getCode());
        }

        try {
            UserDA.checkUser(new User("fsfs", null));
        } catch (StatusException e) {
            assertEquals(StatusCode.PASSWORD_MISSING, e.getCode());
        }

        try {
            UserDA.checkUser(new User("fsfs", ""));
        } catch (StatusException e) {
            assertEquals(StatusCode.PASSWORD_MISSING, e.getCode());
        }
    }

    @Test
    public void testCreateAndFindUser() throws StatusException {
        String username = Common.makeString(10);
        String password = Common.makeString(10);
        User user = new User(username, password);
        UserDA.create(user);
        assertTrue(UserDA.find(user));
    }

    @Test
    public void testCreateUser() throws StatusException {
        String username = Common.makeString(10);
        String password = Common.makeString(10);
        User user = new User(username, password);
        UserDA.create(user);
    }

    @Test
    public void testUpdateUser() throws StatusException {
        String username = Common.makeString(10);
        String password = Common.makeString(10);
        User user = new User(username, password);
        UserDA.create(user);

        String newPassword = Common.makeString(10);
        assertNotEquals(password, newPassword);
        User newUser = new User(username, newPassword);
        UserDA.update(newUser);

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

        UserDA.delete(user);

        try {
            assertFalse(UserDA.find(user));
        } catch (StatusException e) {
            assertEquals(StatusCode.USER_NOT_FOUND, e.getCode());
        }
    }
}
