package com.panshi.elasticsearch.test;

import com.panshi.elasticsearch.pojo.Items;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Auther: guo
 * @Date: 11:43 2021/4/10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
//@Slf4j
public class ElasticsearchTest01 {
//    @Resource(name = "highLevelClient")
    @Resource
    RestHighLevelClient client;

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    /**
     * JavaClient
     * @throws IOException
     */
    // 创建索引
    @Test
    public void testCreateIndex() throws IOException {
        // 1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("jd_goods");
        // 2.客户端执行请求
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        // 响应内容
        boolean acknowledged = response.isAcknowledged();
        System.out.println(response.index()+"  "+acknowledged);
    }

    // 获取索引
    @Test
    public void  testExist() throws IOException {
        GetIndexRequest request = new GetIndexRequest("jd_goods");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);

    }


    // 删除索引
    @Test
    public void  testDelete() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("jd_goods");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }


    /**
     * ElasticsearchRestTemplate
     */
    @Test
    public void testCreateIndex2() {
        boolean isCreate = restTemplate.indexOps(IndexCoordinates.of("template")).create();
        System.out.println(isCreate);

    }

    // 查询索引
    @Test
    public void testExist2() {
        boolean exists = restTemplate.indexOps(IndexCoordinates.of("template")).exists();
        System.out.println(exists);
    }

    @Test//3 删除索引
    public void testDelete2() {
        boolean delete = restTemplate.indexOps(IndexCoordinates.of("template")).delete();
        System.out.println(delete);
    }
}
