package ynu.edu;

import org.springframework.boot.SpringApplication;

public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
        System.out.println("=====================================");
        System.out.println("能耗监测平台后端启动成功！");
        System.out.println("接口文档地址：http://localhost:8080/api/doc.html");
        System.out.println("=====================================");
    }
}
