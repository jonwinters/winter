package top.sourcecode.winter.dao;

import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import top.sourcecode.winter.BaseTest;
import top.sourcecode.winter.entity.User;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Iterator;

/**
 * Created by mountain on 7/24/16.
 */
public class UserDAOTest extends BaseTest {

    @Resource
    private UserDAO userDAO;

    @Test
    public void testUserDAO() throws ParseException {
        Pageable pageable = new PageRequest(0,2);
        Iterator<User> iterator = userDAO.findAll(pageable).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getUsername());
        }
    }
}
