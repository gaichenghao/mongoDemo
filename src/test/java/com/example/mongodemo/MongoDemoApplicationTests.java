package com.example.mongodemo;

import com.example.mongodemo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class MongoDemoApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    //添加
    @Test
    public void createUser() {
        User user = new User();
        user.setAge(20);
        user.setName("test");
        user.setEmail("4932200@qq.com");
        User user1 = mongoTemplate.insert(user);
        System.out.println(user1);
    }

    //查询
    @Test
    public void findAll(){
        List<User> userList = mongoTemplate.findAll(User.class);
        System.out.println(userList);
    }

    //id查询
    @Test
    public  void findId(){
        List<User> userList = mongoTemplate.findAll(User.class);
        User user = mongoTemplate.findById(userList.get(0).getId(), User.class);
        System.out.println(user);
    }

    //条件查询
    @Test
    public void findUserList(){
        //name=test and age=20
        Query queue=new Query(Criteria.where("name").is("test").and("age").is(20));
        List<User> userList = mongoTemplate.find(queue, User.class);
        System.out.println(userList);
    }

    //模糊查询
    @Test
    public void findLikeUserList(){
        //name like test and age=20
        String name="est";
        String regex=String.format("%s%s%s","^.*",name,".*$");
        Pattern pattern=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Query queue=new Query(Criteria.where("name").regex(pattern));
        List<User> userList = mongoTemplate.find(queue, User.class);
        System.out.println(userList);
    }

    //分页查询
    @Test
    public void findPageUserList(){
        int pageNo=1;
        int pageSize=3;
        String name="est";
        //分页查询
        String regex=String.format("%s%s%s","^.*",name,".*$");
        Pattern pattern=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Query queue=new Query(Criteria.where("name").regex(pattern));
        //分页查询
        //查询记录数
        long count = mongoTemplate.count(queue, User.class);
        List<User> userList = mongoTemplate.find(
                queue.skip((pageNo - 1) * pageSize).limit(pageSize), User.class);
        System.out.println(count);
        System.out.println(userList);

    }













}
