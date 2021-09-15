package com.example.mongodemo;

import com.example.mongodemo.entity.User;
import com.example.mongodemo.repository.UserRepository;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class MongoDemoApplicationTests1 {

    @Autowired
    private UserRepository userRepository;

    //添加
    @Test
    public void createUser() {
        User user=new User();
        user.setAge(20);
        user.setName("gaich");
        user.setEmail("123@qq.com");
        User user1 = userRepository.save(user);
        System.out.println(user1);

    }

    //查询
    @Test
    public void findAll(){
        List<User> userList = userRepository.findAll();
        System.out.println(userList);
    }

    //id查询
    @Test
    public  void findId(){
        List<User> userList = userRepository.findAll();
        User user = userRepository.findById(userList.get(0).getId()).get();
        System.out.println(user);

    }

    //条件查询
    @Test
    public void findUserList(){
        //name=test, age=20
        User user=new User();
        user.setName("test");
        user.setAge(20);
        Example<User> example=Example.of(user);
        List<User> userList = userRepository.findAll(example);
        System.out.println(userList);
    }

    //模糊查询
    @Test
    public void findLikeUserList(){
        //设置模糊查询的匹配规则
        ExampleMatcher matcher=ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);

        User user=new User();
        user.setName("test");
        user.setAge(20);
        Example<User> example=Example.of(user,matcher);
        List<User> userList = userRepository.findAll(example);
        System.out.println(userList);
    }

    //分页查询
    @Test
    public void findPageUserList(){

        //设置分页参数
        //0代表第一页
        Pageable pageable= PageRequest.of(0,3);
        User user=new User();
        user.setName("test");
        Example<User> example=Example.of(user);
        Page<User> userList = userRepository.findAll(example,pageable);
        System.out.println(userList.getContent());


    }


    //修改
    @Test
    public void updateUser(){

        List<User> userList = userRepository.findAll();
        User user = userRepository.findById(userList.get(0).getId()).get();
        user.setName("aaa");
        User user1 = userRepository.save(user);
        System.out.println(user1);

    }

    //删除
    @Test
    public void deleteUser(){

    }











}
