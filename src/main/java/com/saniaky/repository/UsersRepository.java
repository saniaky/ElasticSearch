package com.saniaky.repository;

import com.saniaky.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author saniaky
 * @since 2/10/17
 */
public interface UsersRepository extends PagingAndSortingRepository<User, Long> {
}
