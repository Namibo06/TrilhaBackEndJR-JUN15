package com.codigoCerto.tarefas.configs;

import org.hibernate.cfg.Environment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Autowired Environment env;

    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public DataSource dataSource(){
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Environment.getProperties().getProperty("driverClassName"));
        dataSource.setUrl(Environment.getProperties().getProperty("url"));
        dataSource.setUsername(Environment.getProperties().getProperty("username"));
        dataSource.setPassword(Environment.getProperties().getProperty("password"));

        return dataSource;
    }
}
