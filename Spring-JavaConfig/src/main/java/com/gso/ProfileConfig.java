package com.gso;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

public class ProfileConfig {
    @Bean
    @Profile("dev")
    public DemoBean devDemoBean() {
        return  new DemoBean("from development profile");
    }
    @Bean
    @Profile("prod")
    public DemoBean prodDemoBean() {
        return  new DemoBean("from production profile");
    }
}
