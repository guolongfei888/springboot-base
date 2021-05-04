package com.panshi.elasticsearch.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.panshi.elasticsearch.pojo.Items;
import com.panshi.elasticsearch.pojo.User;
import com.panshi.elasticsearch.repository.ItemRepository;
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
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: guo
 * @Date: 11:43 2021/4/10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest05 {
    @Resource
    ItemRepository itemRepository;
    @Autowired
    private ElasticsearchRestTemplate restTemplate;

//    @Test
//    public void createIMapping() {
//        restTemplate.putMapping(Items.class);
//    }
    // 新增
    @Test
    public void testSave() {
        Items items = new Items(4L,"vivo","手机","X7",3999.0,"http://www.baidu.com", LocalDateTime.now());
        itemRepository.save(items);
    }

    @Test
    public void test1() {
        Date date = new Date();
        date.setTime(1619238169936L);
        System.out.println(date);
    }

    // 修改
    @Test
    public void update() {
        Items items = new Items(1L,"华为手机","手机","华为",2999.0,"http://www.baidu.com", LocalDateTime.now());
        itemRepository.save(items);
    }

    // 根据id查询
    @Test
    public void findById() {
        Items items = itemRepository.findById(1L).get();
        System.out.println(items);
    }
    
    // 查询所有
    @Test
    public void findAll() {
        List<Items> items = itemRepository.findByTitle("手机");
        items.forEach(System.out::println);
//        Iterable<Items> all = itemRepository.findAll();
//        for (Items items : all) {
//            System.out.println(items);
//        }
    }

    // 删除
    @Test
    public void delete(){
        Items items = new Items();
        items.setId(1L);
        itemRepository.delete(items);
    }

    // 批量新增
    @Test
    public void saveAll() {
        List<Items> itemsList  = new ArrayList<>();
        for (long i = 0; i<10;i++) {
            itemsList.add(new Items(i,"华为手机 "+i,"手机 "+i,"华为 "+i,2999.0+i,"http://www.baidu.com", LocalDateTime.now()));
        }
        itemRepository.saveAll(itemsList);
    }

    // 分页查询
    @Test
    public void findByPageable() {
        // 设置排序(排序方式, 正序还是倒序)
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int currentPage =0; // 当前页,第一页
        int pageSize = 5; // 每页显示条数
        // 设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sort);
        // 分页查询
        Page<Items> page = itemRepository.findAll(pageRequest);
        for (Items items : page) {
            System.out.println(items);
        }
    }


}