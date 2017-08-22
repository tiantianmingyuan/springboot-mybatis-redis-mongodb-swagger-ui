package com.zkzn.payment.server.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @SuppressWarnings("unchecked")
    @Bean
    public Docket testApi(){
       Docket docket = new Docket(DocumentationType.SWAGGER_2)
              .groupName("test")
              .genericModelSubstitutes(DeferredResult.class)
              .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zkzn.payment.server.web"))
                .paths(Predicates.or(PathSelectors.regex(".*/api/.*")))//过滤的接口
                .paths(PathSelectors.any())
                .build()
               .apiInfo(testApiInfo());
              ;
       return docket;
    }
   
    @SuppressWarnings("unchecked")
    @Bean
    public Docket demoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("demo")
               .genericModelSubstitutes(DeferredResult.class)
//             .genericModelSubstitutes(ResponseEntity.class)
               .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zkzn.payment.server.web"))
                .paths(Predicates.or(PathSelectors.regex(".*/demo/.*")))//过滤的接口
                .paths(PathSelectors.any())
                .build()
               .apiInfo(demoApiInfo());
    }
   
     private ApiInfo testApiInfo() {
            ApiInfo apiInfo = new ApiInfo("Test相关接口",//大标题
                    "Test相关接口，主要用于测试.",//小标题
                    "1.0",//版本
                    "mingyuan.du@sao.so",
                    "杜明远",//作者
                    "连接地址",//链接显示文字
                    "www.baidu.com"//网站链接
            );
 
            return apiInfo;
        }
 
    private ApiInfo demoApiInfo() {
        ApiInfo apiInfo = new ApiInfo("Demo相关接口",//大标题
                "Demo相关接口 ，主要用于测试",//小标题
                "1.0",//版本
                "mingyuan.du@sao.so",
                "明汐",//作者
                "连接地址",//链接显示文字
                "www.baidu.com"//网站链接
            );
            return apiInfo;
        }
    
    /**
     * @return
     */
    private List<Parameter> getGlobalOperationParameters() {
        List<Parameter> list = new ArrayList<Parameter>();
        Parameter auth = new ParameterBuilder().name("Authorization").description("Authorization")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("Bearer ").required(true).build();
        list.add(auth);
        return list;
    }
}
