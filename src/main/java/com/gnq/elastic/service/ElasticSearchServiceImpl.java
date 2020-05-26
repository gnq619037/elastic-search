package com.gnq.elastic.service;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: guonanqing
 * @desc:
 * @date: 2020/5/26
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    @Autowired
    private RestHighLevelClient highLevelClient;

    @Override
    public Object fuzzySearchByName() {
        return null;
    }
}
