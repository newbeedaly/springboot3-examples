package cn.newbeedaly.dockercompose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@SpringBootApplication
public class DockerComposeApplication {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DockerComposeApplication.class, args);
    }

    @RequestMapping("/")
    public String hello() {
        return Optional.ofNullable(redisTemplate.opsForValue().get("hello")).orElse("hello world!");
    }
}
