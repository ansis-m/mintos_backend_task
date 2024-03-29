package com.example.mintos.backend.config;

import com.example.mintos.backend.entities.Account;
import com.example.mintos.backend.entities.Client;
import com.example.mintos.backend.entities.Transaction;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors){
        config.exposeIdsFor(Client.class);
        config.exposeIdsFor(Account.class);
        config.exposeIdsFor(Transaction.class);
    }
}
