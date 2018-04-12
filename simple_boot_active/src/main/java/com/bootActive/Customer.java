package com.bootActive;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Customer {
    @JmsListener(destination = "sample.queue")
    public void receiveQueue(String text) {
        System.out.println(text);
    }
}
