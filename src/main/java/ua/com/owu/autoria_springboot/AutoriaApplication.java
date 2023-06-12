package ua.com.owu.autoria_springboot;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@SpringBootApplication
public class AutoriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoriaApplication.class, args);
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
