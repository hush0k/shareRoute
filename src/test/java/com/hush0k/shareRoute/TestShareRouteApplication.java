package com.hush0k.shareRoute;

import org.springframework.boot.SpringApplication;

public class TestShareRouteApplication {

	public static void main(String[] args) {
		SpringApplication.from(ShareRouteApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
