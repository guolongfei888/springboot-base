package com.panshi.elasticsearch.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Auther: guo
 * @Date: 0:15 2021/4/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "item_index",shards = 1, replicas = 0)
public class Items {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title; //标题

    @Field(type = FieldType.Keyword)
    private String category;// 分类

    @Field(type = FieldType.Keyword)
    private String brand; // 品牌

    @Field(type = FieldType.Double)
    private Double price; // 价格

    @Field(index = false, type = FieldType.Keyword)
    private String images; // 图片地址


    // 指定存储格式
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    // 数据格式转换，并加上8小时进行存储
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
//    @Field(type = FieldType.Date_Range)
    private LocalDateTime createTime;

}
