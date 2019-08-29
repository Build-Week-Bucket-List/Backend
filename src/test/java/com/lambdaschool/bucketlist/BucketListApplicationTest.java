package com.lambdaschool.bucketlist;

import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableWebMvc
// @EnableJpaAuditing
@SpringBootApplication
//@EnableSwagger2
public class BucketListApplicationTest
{

    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(BucketListApplicationTest.class, args);

        DispatcherServlet dispatcherServlet = (DispatcherServlet) ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }

}
