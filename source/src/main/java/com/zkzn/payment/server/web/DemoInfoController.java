package com.zkzn.payment.server.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.WriteResult;
import com.zkzn.payment.server.entity.DemoInfo;


@RestController
public class DemoInfoController {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    //找出mongodb全部
    @RequestMapping("find2")
    public List<DemoInfo> find2(){
       return mongoTemplate.findAll(DemoInfo.class);
    }
    
    //单个指定库插入mongodb
    @RequestMapping("insert")
    public String insert(){
        DemoInfo demoInfo = new DemoInfo();
        demoInfo.setAge(12);
        demoInfo.setName("机械纪元");
        List<String> list = new ArrayList<>();
        list.add("mongodb");
        list.add("java");
        list.add("spring");
        demoInfo.setList(list);
        mongoTemplate.insert(demoInfo, "demoInfo");
        return "OK";
    }
    //批量使用mongdb进行插入
    @RequestMapping("insertBatch")
    public String insertBatch(){
        List<DemoInfo> list = new ArrayList<>();
        DemoInfo demoInfo = new DemoInfo();
        demoInfo.setAge(12);
        demoInfo.setName("张三");
        DemoInfo demoInfo2 = new DemoInfo();
        demoInfo2.setAge(13);
        demoInfo2.setName("张三2");
        list.add(demoInfo);
        list.add(demoInfo2);
        mongoTemplate.insert(list, "demoInfo");
        return "OK";
    }
    
    
    //条件查询mongodb
    @RequestMapping("queryByCondition")
    public List<DemoInfo>  queryByCondition(){
       Query query =Query.query(Criteria.where("name").is("张三2"));
       List<DemoInfo> find = mongoTemplate.find(query, DemoInfo.class);
       return find;
    }
    
    //条件修改mongodb
    @RequestMapping("upsertByCondition")
    public WriteResult  upsertByCondition(){
      //特殊更新，更新name为张三2的数据，如果没有name为张三2的数据则以此条件创建一条新的数据
      //当没有符合条件的文档，就以这个条件和更新文档为基础创建一个新的文档，如果找到匹配的文档就正常的更新
       Query query =Query.query(Criteria.where("name").is("张三2"));
       Update update = Update.update("name", "李四").set("age", 22);
       WriteResult upsert = mongoTemplate.upsert(query, update, DemoInfo.class);
       return upsert;
    }
    
    
    
    //更新条件不变，更新字段改成了一个我们集合中不存在的，用set方法如果更新的key不存在则创建一个新的key
    @RequestMapping("updateMulti")
    public WriteResult  updateMulti(){
       Query query =Query.query(Criteria.where("name").is("李四"));
       Update update = Update.update("name", "李四updateMulti").set("money", 100);
       WriteResult updateMulti = mongoTemplate.updateMulti(query, update, DemoInfo.class);
       return updateMulti;
    }
    
    
    //update的inc方法用于做累加操作，将money在之前的基础上加上100
    @RequestMapping("updateMultiForInc")
    public WriteResult  updateMultiForInc(){
       Query query =Query.query(Criteria.where("name").is("李四updateMulti"));
       Update update = Update.update("name", "李四updateMulti").inc("money", 100);
       WriteResult updateMulti = mongoTemplate.updateMulti(query, update, DemoInfo.class);
       return updateMulti;
    }
    
    //update的rename方法用于修改key的名称
    @RequestMapping("updateMultiForReName")
    public WriteResult  updateMultiForReName(){
       Query query =Query.query(Criteria.where("name").is("李四updateMulti"));
       Update update = Update.update("name", "李四updateMulti").rename("money", "money改");
       WriteResult updateMulti = mongoTemplate.updateMulti(query, update, DemoInfo.class);
       return updateMulti;
    }
    
    //update的unset方法用于删除key
    @RequestMapping("updateMultiForUnset")
    public WriteResult  updateMultiForUnset(){
       Query query =Query.query(Criteria.where("name").is("李四updateMulti"));
       Update update = Update.update("name", "李四updateMulti").unset("money改");
       WriteResult updateMulti = mongoTemplate.updateMulti(query, update, DemoInfo.class);
       return updateMulti;
    }
    
    
    //update的pull方法用于删除list数组中的java
    @RequestMapping("updateMultiForPull")
    public WriteResult  updateMultiForPull(){
       Query query =Query.query(Criteria.where("name").is("机械纪元"));
       Update update = Update.update("name", "机械纪元").pull("list", "java");
       WriteResult updateMulti = mongoTemplate.updateMulti(query, update, DemoInfo.class);
       return updateMulti;
    }
    
    //传入json实体条件查询mongodb
    @RequestMapping(value="queryByModel",method=RequestMethod.POST)
    public List<DemoInfo>  queryByModel(@RequestBody DemoInfo demoInfo){
       Query query =Query.query(Criteria.where("name").is(demoInfo.getName()));
       List<DemoInfo> find = mongoTemplate.find(query, DemoInfo.class);
       return find;
    }
    
    
    
    
}
