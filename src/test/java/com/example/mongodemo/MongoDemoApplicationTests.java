package com.example.mongodemo;

import com.example.mongodemo.entity.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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


    //修改
    @Test
    public void updateUser(){
        //根据id查询
        List<User> userList = mongoTemplate.findAll(User.class);
        User user = mongoTemplate.findById(userList.get(0).getId(), User.class);

        //设置修改值
        user.setName("test_1");
        user.setAge(50);
        user.setEmail("0000@qq.com");

        //调用方法实现修改
        Query query=new Query(Criteria.where("_id").is(user.getId()));
        Update update=new Update();
        update.set("name",user.getName());
        update.set("age",user.getAge());
        update.set("email",user.getEmail());
        UpdateResult upsert = mongoTemplate.upsert(query, update, User.class);
        long count = upsert.getModifiedCount();
        System.out.println(count);

    }

    //删除
    @Test
    public void deleteUser(){
        //根据id查询
        List<User> userList = mongoTemplate.findAll(User.class);
        User user = mongoTemplate.findById(userList.get(0).getId(), User.class);
        //调用方法实现修改
        Query query=new Query(Criteria.where("_id").is(user.getId()));
        DeleteResult remove=mongoTemplate.remove(query,User.class);
        long deletedCount = remove.getDeletedCount();
        System.out.println(deletedCount);

    }











}
