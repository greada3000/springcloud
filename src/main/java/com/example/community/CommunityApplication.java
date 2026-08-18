package com.example.community;
import org.mybatis.spring.annotation.MapperScan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan(value = "com.example.community", annotationClass = Mapper.class)
public class CommunityApplication {
  public static void main(String[] args) { SpringApplication.run(CommunityApplication.class, args); }
}
