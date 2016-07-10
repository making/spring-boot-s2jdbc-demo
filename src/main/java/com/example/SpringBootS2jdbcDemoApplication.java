package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
public class SpringBootS2jdbcDemoApplication {

    @Autowired
    MessageDao messageDao;

    @RequestMapping("/")
    List<Message> list() {
        return messageDao.selectAll();
    }

    @RequestMapping(path = "/", params = "sort")
    List<Message> sort() {
        return messageDao.selectOrderByText();
    }


    @RequestMapping(path = "/", params = "text")
    Message add(@RequestParam String text) {
        Message message = new Message();
        message.text = text;
        messageDao.insert(message);
        return message;
    }

    @Bean
    CommandLineRunner init(MessageDao messageDao) {
        return a -> Stream.of("hello", "hi", "foo", "bar")
                .forEach(text -> {
                    Message m = new Message();
                    m.text = text;
                    messageDao.insert(m);
                });
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootS2jdbcDemoApplication.class, args);
    }
}
