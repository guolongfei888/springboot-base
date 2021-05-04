package com.panshi.elasticsearch.repository;

import com.panshi.elasticsearch.pojo.Items;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: guo
 * @Date: 10:19 2021/4/10
 */
@Repository
public interface ItemRepository extends ElasticsearchRepository<Items,Long> {

    List<Items> findByTitle(String title);
}
