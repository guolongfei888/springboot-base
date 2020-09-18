package com.panshi.springboot.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *将配置文件中配置的每一个属性的值，映射到这个组件中
 * '@ConfigurationProperties'：告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定；
 *        prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *
 *   只有这个组件是容器中的组件，才能容器提供的@ConfigurationProperties功能；
 *    '@ConfigurationProperties(prefix = "person")'默认从全局配置文件中获取值；
 */
@PropertySource(value = "classpath:person.properties")
@Component
//@ImportResource(value = {"classpath:beans.xml"})
@ConfigurationProperties(prefix = "person")
//@Validated
public class Person {
    /**
     * <bean class="Person">
     *      <property name="lastName" value="字面量/${key}从环境变量、配置文件中获取值/#{SpEL}"></property>
     * <bean/>
     */

//    @Value("#{11*3}")
    private Integer id;
//    @Value("#{'张三'}")
    //@Email(message = "邮箱格式有误") // @Email注解报红 是因为新版本需要validation启动器
    private String lastName;
//    @Value("${person.age}")
    private Integer age;
    //@Value("true")
    private Boolean boss;
    private Date birth;
    private List<String> lists;
    private Map maps;
    private Dog dog;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isBoss() {
        return boss;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public List<String> getLists() {
        return lists;
    }

    public void setLists(List<String> lists) {
        this.lists = lists;
    }

    public Map getMaps() {
        return maps;
    }

    public void setMaps(Map maps) {
        this.maps = maps;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", boss=" + boss +
                ", birth=" + birth +
                ", lists=" + lists +
                ", maps=" + maps +
                ", dog=" + dog +
                '}';
    }
}
