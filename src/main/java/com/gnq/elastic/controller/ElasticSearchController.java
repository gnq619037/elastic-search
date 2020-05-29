package com.gnq.elastic.controller;

import com.alibaba.fastjson.JSONObject;
import com.gnq.elastic.bean.User;
import com.gnq.elastic.service.ElasticSearchService;
import com.gnq.elastic.util.EsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author: guonanqing
 * @desc: es
 * @date: 2020/5/26
 */
@RestController
@RequestMapping("/elastic")
public class ElasticSearchController {

    @Autowired
    private EsUtil esUtil;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @PostMapping("/user/add")
    public String addUser(@RequestBody User user) throws IOException {
        String userJson = JSONObject.toJSONString(user);
        esUtil.createIndex("user",  String.valueOf(user.getId()), "commonUser", userJson);
//        String rsp = esUtil.add("user_index", "user", user);
        return "success";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestBody User user) throws IOException {
        esUtil.delete("user", "commonUser", String.valueOf(user.getId()));
        return "success";
    }

//    @PostMapping("/user/search")
//    public String searchUser(@RequestBody User user) throws IOException {
//        String rsp = esUtil.search("user_index", "user", "");
//        return rsp;
//    }
//
//    @PostMapping("/search")
//    public Object fuzzySearch(){
//        Object object = elasticSearchService.fuzzySearchByName();
//        return object;
//    }
}
