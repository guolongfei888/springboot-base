package com.panshi.elasticsearch.test;

import com.panshi.elasticsearch.pojo.Items;
import com.panshi.elasticsearch.pojo.User;
import com.panshi.elasticsearch.repository.ItemRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: guo
 * @Date: 21:58 2021/4/10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest04 {
    @Resource
    private ItemRepository itemRepository;
    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    // -------------------------------------------------------------
    // ElasticSearchTemplate
    /**
     * 测试 高亮搜索，总得整点下饭操作叭
     */
//    @Test
//    public void testSearchHighLight() {
//        //第二步，总查询条件
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "手机");
//        /*=============================================================*/
//        // 第三步 高亮设置
//        // new 一个 highlightBuilder
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.field("title");
//        highlightBuilder.preTags("<span style='color:red'>");
//        highlightBuilder.postTags("</span>");
//        /*=============================================================*/
//        // 第四步 分页设置
//        // new 一个 Pageable 的实现类
//        // PageRequest(int page, int size, Sort sort)
//        // 需要三个参数~
//        Pageable pageable = PageRequest.of(1, 5);
//        // 【 还可以排序哦，都点开看看 】
//        //Pageable pageable1 =PageRequest.of(1, 5, Sort.Direction.ASC);
//
//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                .withHighlightBuilder(highlightBuilder)
//                .withPageable(pageable)
//                .withQuery(matchQueryBuilder).build();
//
//        // 第一步 template选方法
//        SearchHits<AbstractDocument.Content> template = restTemplate.search(nativeSearchQuery, AbstractDocument.Content.class , IndexCoordinates.of("goods"));
//
//        List<SearchHit<AbstractDocument.Content>> searchHits = template.getSearchHits();
//        for (SearchHit<AbstractDocument.Content> searchHit : searchHits) {
//            System.out.println(searchHit);
//        }
//
//        // 高亮字段返回前端 肯定是要替换一下的
//        // 玛德 自己搞 我不搞了
//    }


    // 创建index

    // ElasticSearchTemplate

    /**
     * 测试添加文档  put/User_index/_doc/1
     */
    @Test
    public void testInsertDoc() {
        // 创建对象
        User user = new User("阿巴", 12, "a");
        restTemplate.save(user, IndexCoordinates.of("template"));
    }

    @Test
    public void testCreateDoc2() {
        // 创建对象
        User user = new User("阿巴", 12, "a");
        // 老夫 tm 就想指定 id 发现 save 内部执行 index 方法
        // 第二步 构建需要的参数 indexQuery
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setObject(user);
        indexQuery.setId("1");
        // 第一步选择方法 index(IndexQuery query, IndexCoordinates index) 需要两个参数：IndexQuery 索引库
        restTemplate.index(indexQuery, IndexCoordinates.of("template"));
    }

    // ElasticSearchTemplate
    @Test
    public void testGetDoc2() {
        User user = restTemplate.get("1", User.class, IndexCoordinates.of("template"));
        System.out.println(user);
    }

    // ElasticSearchTemplate

    /**
     * 测试文档是否存在 get /index/doc/1
     */
    @Test
    public void testExist2() {
        boolean template = restTemplate.exists("1", IndexCoordinates.of("template"));
        System.out.println(template);
    }


    // ElasticSearchTemplate

    /**
     * 批量数据导入
     */
    @Test
    public void testBulkRequest2() {
        // 第二步 构建条件
        List<IndexQuery> indexQueryList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = new User("你好啊" + i, i, "a "+i);
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setObject(user);
            indexQuery.setId(""+i);
            indexQueryList.add(indexQuery);
        }
        // 第一步 选择方法
        // List<String> bulkIndex(List<IndexQuery> queries, BulkOptions bulkOptions, IndexCoordinates index)
        // 三个参数而已，弄！
        // 返回 id list
        List<String> list = restTemplate.bulkIndex(indexQueryList, BulkOptions.defaultOptions(), IndexCoordinates.of("template"));
        System.out.println(list);
    }


    // ElasticSearchTemplate

    /**
     * 更新文档
     */
    @Test
    public void testUpdateDoc2() {
        // 第三步：创建 Document 对象添加需要更改的字段
        Document document = Document.create();

        document.append("name", "张三");

        // 第二步：创建 UpdateQuery 对象，由于参数太多，很 nb 的是源码提供了 builder 方法参数为文档id
        //UpdateQuery没有注释，看着脑壳疼，如有错误请及时指正
        UpdateQuery updateQuery = UpdateQuery.builder("1").withTimeout("1s")
                .withDocument(document).build();
        // 第一步：选择方法 update(UpdateQuery query, IndexCoordinates index)
        // 需要两个参数 第一个参数 更新条件，第二个参数 索引库
        UpdateResponse template = restTemplate.update(updateQuery, IndexCoordinates.of("template"));
        System.out.println(template.getResult());
    }

    // ElasticSearchTemplate
    @Test
    public void testDeleteDoc2() {
        // 删除具体文档
        String template = restTemplate.delete("knlKFXkBKMOU9gHpQtuN", IndexCoordinates.of("template"));
        System.out.println(template);
    }


    // 挨？ 老夫现在就想删多个匹配的文档，怎么滴？
    @Test
    public void testDeleteDoc3() {
        // 第二步 构建所需要的条件
        // 埃？ 这里为什么这样写？一开始我也不知道，点开看 Query 接口的源码
        // 她提供了个静态方法
        //static Query findAll() {
        //		return new StringQuery(QueryBuilders.matchAllQuery().toString());
        //}
        // 源码这样写的~
        //        QueryBuilders.termQuery()
        //        QueryBuilders.boolQuery()
        //        QueryBuilders.fuzzyQuery()
        StringQuery age = new StringQuery(QueryBuilders.matchQuery("age", "186").toString());
        // 第一步：选择方法
        // 删除匹配某条件的 delete(Query query, Class<?> clazz, IndexCoordinates index)
        // 从这以后包括搜索参数中开始出现  Class<?> clazz 这个参数，是不是一脸懵逼？
        // 这个参数就是 查询的实体，我查的 User 应该写上 User.class
        // 我一想，我都删了也不要什么返回类型，我就写 null 怎么滴，老铁没毛病~
        // Query 参数就是实例化他的儿子
        restTemplate.delete(age, null, IndexCoordinates.of("template"));
        //===========================================
        // 方法二  Query 有多个实例类
        // 第二步 构建所需要的条件
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("age", "19");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(matchQueryBuilder).build();

        // 第一步
        // 删除匹配某条件的 delete(Query query, Class<?> clazz, IndexCoordinates index)
        restTemplate.delete(nativeSearchQuery, null, IndexCoordinates.of("template"));
    }


    // -----------------------------------------------------------------
    // ElasticSearchTemplate
    @Test
    public void testSearch2() {
        // 第二步构建参数
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("age", "6");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(matchQueryBuilder).build();

        // 第一步 选方法
        //<T> SearchHits<T> search(Query query, Class<T> clazz, IndexCoordinates index);
        SearchHits<User> template = restTemplate.search(nativeSearchQuery, User.class, IndexCoordinates.of("template"));
        List<SearchHit<User>> searchHits = template.getSearchHits();
        for (SearchHit<User> searchHit : searchHits) {
            System.out.println(searchHit);
        }
    }

    // 分页查询

}
