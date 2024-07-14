package com.elleined.socialmediaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class SocialMediaAPIApplication {
	public static void main(String[] args) {
		SpringApplication.run(SocialMediaAPIApplication.class, args);
	}
}
