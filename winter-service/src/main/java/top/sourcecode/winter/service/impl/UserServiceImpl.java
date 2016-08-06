package top.sourcecode.winter.service.impl;

import org.springframework.stereotype.Service;
import top.sourcecode.winter.dao.UserDAO;
import top.sourcecode.winter.entity.User;
import top.sourcecode.winter.service.UserService;

import javax.annotation.Resource;

/**
 * Created by mountain on 7/12/16.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;

    @Override
    public void saveUser(User user) {
        userDAO.save(user);
    }
}
