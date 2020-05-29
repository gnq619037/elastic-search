package com.gnq.elastic;/**
 * @author guonanqing
 * @date 2020/5/27 9:40
 * @version 1.0
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author: guonanqing
 * @desc:
 * @date: 2020/5/27
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ESApplication {
    public static void main(String[] args) {
        SpringApplication.run(ESApplication.class, args);
    }
}
