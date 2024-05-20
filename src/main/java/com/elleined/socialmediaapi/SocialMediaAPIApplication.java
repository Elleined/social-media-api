package com.elleined.socialmediaapi;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class SocialMediaAPIApplication {
	public static void main(String[] args) {
		SpringApplication.run(SocialMediaAPIApplication.class, args);
	}
}
