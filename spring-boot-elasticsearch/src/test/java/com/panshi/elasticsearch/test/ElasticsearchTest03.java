package com.panshi.elasticsearch.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.panshi.elasticsearch.pojo.User;
import net.sf.json.JSONObject;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: guo
 * @Date: 11:43 2021/4/10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest03 {
//    @Resource(name = "highLevelClient")
    @Resource
    RestHighLevelClient client;

    // JavaClient
    // 添加文档
    //测试添加文档  put/User_index/_doc/1
    @Test
    public void testCreateDoc() throws IOException {
        // 创建对象
        User user = new User("阿巴", 12, "a");
        // 创建请求
        // 创建索引
        IndexRequest request = new IndexRequest("user_index");
        // 指定id, 如果不指定, 则自动生成
        request.id("1");
        // 请求超时
        request.timeout(TimeValue.timeValueSeconds(1));
//        request.timeout("1s");
        // 数据放入请求
//        request.source(JSON.toJSONString(user), XContentType.JSON);
        // 向ES插入数据 必须转换为JSON格式
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.
//        request.source(user);
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writerWithView(user.getClass()).writeValueAsString(user);
        request.source(userJson,XContentType.JSON);
        // 客户端发送请求
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
    }


    // JavaCliet
    @Test// 测试文档是否存在 get /index/doc/1
    public void testExist() throws IOException {
        // get 就是 get 请求，那 update 就是 update 请求喽
        GetRequest request = new GetRequest("user_index", "1");
        // 不获取返回的 _source 的上下文
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }


    // JavaClient
    @Test// 获得文档的信息 get /index/doc/1
    public void testGetDoc() throws IOException {
        GetRequest request = new GetRequest("user_index", "1");
        GetResponse documentFields = client.get(request, RequestOptions.DEFAULT);
        // 打印文档的内容
        System.out.println(documentFields.getSourceAsString());
        // 返回内容和请求一样
        System.out.println(documentFields.toString());
    }



    // JavaClient
    @Test // 更新文档
    public void testUpdateDoc() throws IOException {
        // 你看 update 请求~
        UpdateRequest updateRequest = new UpdateRequest("user_index", "1");
        updateRequest.timeout("1s");
        // 修改的字段,没有该字段就添加
//        updateRequest.doc(XContentType.JSON,"sex","as");
        User user = new User("阿巴阿巴222", 12, "b");

        // 修改单一属性
//        updateRequest.doc(XContentType.JSON,"name","阿巴阿巴");
        // 修改对象
        updateRequest.doc(JSONObject.fromObject(user),XContentType.JSON);
        client.update(updateRequest, RequestOptions.DEFAULT);
    }


    // JavaClient
    @Test
    public void testDeleteDoc() throws IOException {
        // delete 请求~
        DeleteRequest deleteRequest = new DeleteRequest("user_index", "1");
        deleteRequest.timeout("1s");
        DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }



    // 一次搞一个太短了，说出去没面子，你得搞几个才行
    // JavaClient
    @Test // 批量数据导入
    public void testBulkRequest() throws Exception {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("阿巴阿巴111", 10, "wo"));
        users.add(new User("阿巴阿巴222", 29, "wom"));
        users.add(new User("阿巴阿巴333", 39, "woma"));
        users.add(new User("阿巴阿巴444", 49, "woman"));
        for (int i = 0; i < users.size(); i++) {
            bulkRequest.add(
                    new IndexRequest("user_index").id((i + 1) + "")
                            .source(JSONObject.fromObject(users.get(i)), XContentType.JSON)
            );
        }
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());
    }

    @Test // 批量删除
    public void testDeleteBulkRequest() throws Exception {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        for (int i = 1; i < 5; i++) {
            bulkRequest.add(
                    new DeleteRequest("user_index").id((i ) + "")
            );
        }
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());
    }
    //----------------------------------------------------------



    // JavaClient
    // 有点迷 O，说好的 rest 请求
    // 到搜索就是 Search 请求了
    // 年轻人 不讲武德，耗子尾汁~
    // SearchRequest 搜索请求
    // SearchSourceBuilder 条件构造
    @Test // 测试搜索  搜索请求条件构造
    public void testSearch() throws Exception {
//        SearchRequest searchRequest = new SearchRequest("user_index");
//        // 构建搜索条件
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        // 快速构建查询条件
//        // QueryBuilders.termQuery 精确匹配
//        // QueryBuilders.matchAllQuery 匹配所有
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "阿巴阿巴");
//        searchSourceBuilder.query(matchQueryBuilder);
//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHit[] hits = search.getHits().getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getSourceAsMap());
//        }


//        // 2 条件查询 QueryBuilders.termQuery 精确匹配
//        SearchRequest searchRequest = new SearchRequest("user_index");
//        // 构建搜索条件
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("age", 10);
//        searchSourceBuilder.query(termQueryBuilder);
//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHit[] hits = search.getHits().getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getSourceAsMap());
//        }


//        // 3 分页查询
//        SearchRequest searchRequest = new SearchRequest("user_index");
//        // 构建搜索条件
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
//        searchSourceBuilder.query(matchAllQueryBuilder);
//        // (当前页码-1)*每页条数
//        searchSourceBuilder.from(0);
//        searchSourceBuilder.size(2);
//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHit[] hits = search.getHits().getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getSourceAsMap());
//        }

//        // 4  查询排序
//        SearchRequest searchRequest = new SearchRequest("user_index");
//        // 构建搜索条件
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
//        searchSourceBuilder.query(matchAllQueryBuilder);
//        // 排序
//        searchSourceBuilder.sort("age", SortOrder.DESC);
//        // (当前页码-1)*每页条数
//        searchSourceBuilder.from(0);
//        searchSourceBuilder.size(2);
//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHit[] hits = search.getHits().getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getSourceAsMap());
//        }

//        // 5  过滤字段
//        SearchRequest searchRequest = new SearchRequest("user_index");
//        // 构建搜索条件
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
//        searchSourceBuilder.query(matchAllQueryBuilder);
//        // 过滤字段
//        String[] excludes = {"age"};
//        String[] includes = {};
//        searchSourceBuilder.fetchSource(includes,excludes);
//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHit[] hits = search.getHits().getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getSourceAsMap());
//        }

//        // 6  组合查询
//        SearchRequest searchRequest = new SearchRequest("user_index");
//        // 构建搜索条件
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//
////        boolQueryBuilder.must(QueryBuilders.matchQuery("age",29));
////        boolQueryBuilder.must(QueryBuilders.matchQuery("name","阿巴阿巴222"));
//
//        boolQueryBuilder.should(QueryBuilders.matchQuery("age",29));
//        boolQueryBuilder.should(QueryBuilders.matchQuery("age",39));
//
//        searchSourceBuilder.query(boolQueryBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHit[] hits = search.getHits().getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getSourceAsMap());
//        }



//        // 7  范围查询
//        SearchRequest searchRequest = new SearchRequest("user_index");
//        // 构建搜索条件
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
//
//        rangeQueryBuilder.gte(20);
//        rangeQueryBuilder.lte(40);
//        searchSourceBuilder.query(rangeQueryBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHit[] hits = search.getHits().getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getSourceAsMap());
//        }


//        // 8  模糊查询
//        SearchRequest searchRequest = new SearchRequest("user_index");
//        // 构建搜索条件
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        FuzzyQueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("firstChar", "wom").fuzziness(Fuzziness.ONE);
//
//        searchSourceBuilder.query(queryBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHit[] hits = search.getHits().getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getSourceAsMap());
//        }


//        // 9  高亮查询
//        SearchRequest searchRequest = new SearchRequest("user_index");
//        // 构建搜索条件
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("firstChar", "wom");
//
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.preTags("<font color='red'>");
//        highlightBuilder.postTags("</font>");
//        highlightBuilder.field("name");
//        searchSourceBuilder.highlighter(highlightBuilder);
//        searchSourceBuilder.query(termQueryBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHit[] hits = search.getHits().getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getSourceAsMap());
//        }

//        // 10  聚合查询
//        SearchRequest searchRequest = new SearchRequest("user_index");
//        // 构建搜索条件
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        MaxAggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");
//
//        searchSourceBuilder.aggregation(aggregationBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHit[] hits = search.getHits().getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getSourceAsMap());
//        }


        // 11  分组查询
        SearchRequest searchRequest = new SearchRequest("user_index");
        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");

        searchSourceBuilder.aggregation(aggregationBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsMap());
        }

    }



    // JavaClient
    @Test
    public void testSearchHighLight() throws IOException {
        int pageNum = 1;
        int pageSize = 5;
        // 搜索请求
        SearchRequest searchRequest = new SearchRequest("goods");
        // 条件构造
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "手机");

        searchSourceBuilder.query(matchQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        // 分页
        searchSourceBuilder.from((pageNum-1)*pageSize);
        searchSourceBuilder.size(pageSize);

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        // 多字段相同只高亮第一个
        highlightBuilder.requireFieldMatch(false);
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        List<Map<String,Object>> list = new ArrayList<>();

        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            // 高亮字段在 highlight 字段中，拿出来替换到类中
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            if(title!=null){
                Text[] fragments = title.fragments();
                StringBuilder n_title = new StringBuilder();
                for (Text fragment : fragments) {
                    n_title.append(fragment);
                }
                sourceAsMap.put("title", n_title);
            }
            list.add(sourceAsMap);

            for (Map<String, Object> map : list) {
                System.out.println(map);
            }
        }
    }

}