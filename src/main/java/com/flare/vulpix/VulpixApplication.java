package com.flare.vulpix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class VulpixApplication {

	public static void main(String[] args) {
		SpringApplication.run(VulpixApplication.class, args);
	}

}
