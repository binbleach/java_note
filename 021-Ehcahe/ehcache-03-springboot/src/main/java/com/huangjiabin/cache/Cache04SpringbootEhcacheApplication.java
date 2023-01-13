package com.huangjiabin.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Cache04SpringbootEhcacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cache04SpringbootEhcacheApplication.class, args);
	}

}
