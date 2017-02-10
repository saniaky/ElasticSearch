package com.saniaky.service.impl;

import com.alibaba.fastjson.JSON;
import com.saniaky.entity.User;
import com.saniaky.model.ElasticSearchConst;
import com.saniaky.repository.UsersRepository;
import com.saniaky.service.UsersService;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author saniaky
 * @since 2/10/17
 */
@Service
public class UsersServiceImpl implements UsersService {

    private static Logger LOG = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private Client client;

    @Override
    public List<User> getUsers(Pageable pageable) {
        return usersRepository.findAll(pageable).getContent();
    }

    @Override
    public User get(long id) {
        return usersRepository.findOne(id);
    }

    @Override
    public User create(User user) {
        User savedUser = usersRepository.save(user);

        // Index userType
        String userJSON = JSON.toJSONString(savedUser);
        IndexResponse response = client
                .prepareIndex(ElasticSearchConst.userIndex, ElasticSearchConst.userType)
                .setSource(userJSON)
                .get();

        if (!response.isCreated()) {
            LOG.error("Cannot create index");
        }

        return savedUser;
    }

    @Override
    public void delete(long userId) {
        // Remove from index
        client.prepareDelete(ElasticSearchConst.userIndex, ElasticSearchConst.userType, String.valueOf(userId)).get();

        // Delete from repository
        usersRepository.delete(userId);
    }

}
