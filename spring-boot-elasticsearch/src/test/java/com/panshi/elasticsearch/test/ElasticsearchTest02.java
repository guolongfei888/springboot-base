package com.panshi.elasticsearch.test;

import com.panshi.elasticsearch.pojo.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @Auther: guo
 * @Date: 11:43 2021/4/10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest02 {
//    @Resource(name = "highLevelClient")
    @Resource
    RestHighLevelClient client;
    @Autowired
    private ElasticsearchRestTemplate restTemplate;



}
