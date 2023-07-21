package com.example.myregistrar;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
@EnableJpaRepositories(
        basePackages = "com.example.myregistrar.repositories",
        enableDefaultTransactions = false
)
@EnableTransactionManagement
@ComponentScan
public class ApplicationConfig {

}
