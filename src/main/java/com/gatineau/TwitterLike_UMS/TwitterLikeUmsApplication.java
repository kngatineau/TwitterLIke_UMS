package com.gatineau.TwitterLike_UMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TwitterLikeUmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterLikeUmsApplication.class, args);
	}

}
