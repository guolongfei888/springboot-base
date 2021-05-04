package com.panshi.elasticsearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Auther: guo
 * @Date: 13:15 2021/4/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Keyword)
    private Integer age;
    @Field(type = FieldType.Keyword)
    private String firstChar;


}
