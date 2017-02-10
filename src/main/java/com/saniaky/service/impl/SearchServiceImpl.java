package com.saniaky.service.impl;

import com.alibaba.fastjson.JSON;
import com.saniaky.config.ElasticSearchConfig;
import com.saniaky.entity.Phone;
import com.saniaky.entity.User;
import com.saniaky.model.ElasticSearchConst;
import com.saniaky.service.SearchService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author saniaky
 * @since 2/10/17
 */
@Service
public class SearchServiceImpl implements SearchService {

    private static Logger LOG = LoggerFactory.getLogger(ElasticSearchConfig.class);

    @Autowired
    private Client client;

    @Override
    public List<Phone> searchPhones(String username, boolean onlyOfficePhones) {
        List<Phone> result = new ArrayList<>(0);

        SearchResponse response = client.prepareSearch(ElasticSearchConst.userIndex)
                .setTypes(ElasticSearchConst.userType)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("username", "saniaky"))
                .setFrom(0).setSize(1).setExplain(true)
                .execute()
                .actionGet();

        SearchHit[] hits = response.getHits().getHits();

        // User is found
        if (hits.length > 0) {
            String userJSON = hits[0].getSourceAsString();
            User user = JSON.parseObject(userJSON, User.class);

            if (onlyOfficePhones) {
                result = user.getPhone().stream()
                        .filter(phone -> phone.getDescription().contains("Office"))
                        .collect(Collectors.toList());
            } else {
                result = user.getPhone();
            }
        }

        return result;
    }

}
