package com.fastcampus.boardserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching // redis 캐시 사용을 위한 어노테이션
@SpringBootApplication
public class BoardserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardserverApplication.class, args);
	}

}
