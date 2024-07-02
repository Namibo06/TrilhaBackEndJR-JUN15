package com.codigoCerto.tarefas.configs;

import org.modelmapper.ModelMapper;

@org.springframework.context.annotation.Configuration
public class Configuration {
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
