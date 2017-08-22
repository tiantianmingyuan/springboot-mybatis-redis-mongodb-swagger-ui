package com.zkzn.payment.server.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zkzn.payment.server.entity.Demo;
import com.zkzn.payment.server.service.DemoService;
import com.zkzn.payment.server.util.redis.RedisCommonCmd;

import springfox.documentation.schema.plugins.SchemaPluginsManager;


@Controller
@RequestMapping(value = "/demo")
public class DemoController {
 
    
    @Autowired
    private DemoService demoService;
 
    @Autowired
    private RedisCommonCmd redisCommonCmd;
    
    /**
     * 可以直接使用@ResponseBody响应JSON
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getcount", method = RequestMethod.POST)
    public ModelMap getCount(HttpServletRequest request,
            HttpServletResponse response) {
        ModelMap map = new ModelMap();
        map.addAttribute("count", 158);
 
        // 后台获取的国际化信息
        map.addAttribute("xstest", "测试");
        return map;
    }
 
    /**
     * 可以直接使用@ResponseBody响应JSON
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/jsonTest1", method = RequestMethod.POST)
    public ModelMap jsonTest(HttpServletRequest request,
            HttpServletResponse response) {
        ModelMap map = new ModelMap();
        map.addAttribute("hello", "你好");
        map.addAttribute("veryGood", "很好");
 
        return map;
    }
 
    /**
     * 可以直接使用@ResponseBody响应JSON
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/jsonTest3", method = RequestMethod.POST)
    public List<String>jsonTest3(HttpServletRequest request,
            HttpServletResponse response) {
        List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add("你好");
        return list;
    }
 
    /**
     * JSON请求一个对象<br/>
     * （Ajax Post Data：{"name":"名称","content":"内容"}）
     *
     * @param version
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/jsonTest2", method = RequestMethod.POST)
    public ModelMap jsonTest2(@RequestBody Demo demo) {
        ModelMap map = new ModelMap();
        map.addAttribute("result", "ok");
        return map;
    }
 
    @ResponseBody
    @RequestMapping(value = "/jsonTest6", method = RequestMethod.POST)
    public ModelMap jsonTest6(@RequestParam("name") String demoName, @RequestParam String content) {
        ModelMap map = new ModelMap();
        map.addAttribute("name",demoName + "AAA");
        map.addAttribute("content",content + "BBB");
        map.addAttribute("date",new java.util.Date());
        return map;
    }
 
  
    @ResponseBody
    @RequestMapping(value = "/jsonTest5", method = RequestMethod.POST)
    public ModelMap jsonTest5(@RequestBody JSONObject jsonObject) throws Exception {
        String name = jsonObject.getString("name");
        ModelMap map = new ModelMap();
        map.addAttribute("demoName",name);
        return map;
    }
 
   
    @ResponseBody
    @RequestMapping(value = "/jsonTest4", method = RequestMethod.POST)
    public ResponseEntity<String> jsonTest4(HttpEntity<Demo> demo,
            HttpServletRequest request, HttpSession session) {
        //获取Headers方法
        HttpHeaders headers = demo.getHeaders();
 
        // 获取内容
        String demoContent = demo.getBody().getContent();
 
        // 这里直接new一个对象（HttpHeaders headers = new HttpHeaders();）
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("MyHeaderName", "SHANHY");
 
        ResponseEntity<String>responseResult = new ResponseEntity<String>(
                demoContent, responseHeaders, HttpStatus.OK);
        return responseResult;
    }
 
    
    @ResponseBody
    @RequestMapping(value = "/insertDemo", method = RequestMethod.GET)
    public void insertDemo(){
        Demo demo = new Demo();
        demo.setContent("content");
        demo.setName("测试");
        demoService.insertDemo(demo);
    };
    
    /**
     * 
     * @Title: 		分页查询 
     * @Description: TODO     
     * @throws
     */
    @ResponseBody
    @RequestMapping(value = "listDemoByPage", method = RequestMethod.GET)
    public List<Demo> listDemoByPage(){
        /*
         * 第一个参数：第几页;
         * 第二个参数：每页获取的条数.
         */
        PageHelper.startPage(1, 2);
        System.out.println("热加载开始了");
        return demoService.list();
    };
    
    /**
     * 测试redis缓存
     */
    @RequestMapping(value = "testRedis", method = RequestMethod.GET)
    @ResponseBody
    public String testRedis(){
        
        redisCommonCmd.set(0, "testRedis", "testRedis");
        return "OK";
    }
}
