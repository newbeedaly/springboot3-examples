package cn.newbeedaly.springnative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringNativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNativeApplication.class, args);
    }

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

}
