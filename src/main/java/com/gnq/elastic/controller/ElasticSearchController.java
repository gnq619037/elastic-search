package com.gnq.elastic.controller;

import com.gnq.elastic.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: guonanqing
 * @desc:
 * @date: 2020/5/26
 */
@RestController
@RequestMapping("/elastic")
public class ElasticSearchController {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @PostMapping("/search")
    public Object fuzzySearch(){
        Object object = elasticSearchService.fuzzySearchByName();
        return object;
    }
}
