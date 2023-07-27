package com.example.myregistrar.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableJpaRepositories(
        basePackages = "com.example.myregistrar.repositories",
        enableDefaultTransactions = false
)
@EnableTransactionManagement
@EnableKafka
@ComponentScan(basePackages = "com.example.myregistrar")
public class ApplicationConfig {
}
