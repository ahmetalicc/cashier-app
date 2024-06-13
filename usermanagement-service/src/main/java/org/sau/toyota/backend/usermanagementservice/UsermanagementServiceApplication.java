package org.sau.toyota.backend.usermanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UsermanagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsermanagementServiceApplication.class, args);
    }

}
