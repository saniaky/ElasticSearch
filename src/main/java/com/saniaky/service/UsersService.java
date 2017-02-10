package com.saniaky.service;

import com.saniaky.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author saniaky
 * @since 2/10/17
 */
public interface UsersService {

    List<User> getUsers(Pageable pageable);

    User get(long id);

    User create(User user);

    void delete(long userId);
}
