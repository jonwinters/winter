package top.sourcecode.winter.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.sourcecode.winter.entity.User;

import java.util.Date;
import java.util.List;

/**
 * Created by mountain on 7/9/16.
 */
public interface UserDAO extends JpaRepository<User, Long> {

    List<User> findByIdLessThanEqual(Long id);

    List<User> findByCreateTimeGreaterThan(Date date);

    @Query("select u from User u where u.username like %?1%")
    List<User> findByUsernameContains(String username);

    Page<User> findAll(Pageable pageable);
}