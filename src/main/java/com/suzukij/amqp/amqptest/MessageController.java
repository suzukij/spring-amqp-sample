package com.suzukij.amqp.amqptest;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageController {

    @Autowired
    private AmqpTemplate amqp;

    @GetMapping("/")
    public Object send() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("test1", "ABC123");
        parameters.put("test2", "ABCDEABCDE1234567890");
        parameters.put("test3", "AAA");

        amqp.convertAndSend("amqp-test", parameters, m -> {
            m.getMessageProperties().setContentType("application/json");
            m.getMessageProperties().getHeaders().put("Message-Type", "test");
            return m;
        });
        return parameters;
    }

}
