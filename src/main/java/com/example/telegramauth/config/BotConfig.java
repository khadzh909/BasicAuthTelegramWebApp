package com.example.telegramauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@PropertySource("application.properties")
public class BotConfig {
//    @Value("${bot.name}")
    private String name = "BasicAuthAppBot";
//    @Value("${bot.token}")
    private String token = "8116386030:AAFOgGprw8cMcEzNkKJYKRo3vYobf1pgZFM";

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
