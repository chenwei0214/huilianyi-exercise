package com.cloudhelios.atlantis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * author: chenwei
 * createDate: 18-8-27 上午11:08
 * description:
 */
@EnableTransactionManagement
@MapperScan(value = {"com.cloudhelios.atlantis.mapper"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
