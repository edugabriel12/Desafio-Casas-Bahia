package br.com.desafiocasasbahia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DesafioCasasBahiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafioCasasBahiaApplication.class, args);
    }

}
