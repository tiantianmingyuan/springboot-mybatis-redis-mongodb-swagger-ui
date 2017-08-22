package com.zkzn.payment.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkzn.payment.server.dao.DemoDao;
import com.zkzn.payment.server.entity.Demo;



@Service
public class DemoService {

    @Autowired
    private DemoDao demoMapper;
    
    @Transactional
    public void insertDemo (Demo demo){
        demoMapper.save(demo);
    }
    
    
    public List<Demo> list() {
        List<Demo> list = demoMapper.findAll();
        return list;
    }
}
