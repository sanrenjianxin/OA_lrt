package com.lrt.syoa;

import com.lrt.syoa.entity.User;
import com.lrt.syoa.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableScheduling
@SpringBootApplication
public class SyoaApplication {


    public static void main(String[] args) {
        SpringApplication.run(SyoaApplication.class, args);
    }


}
