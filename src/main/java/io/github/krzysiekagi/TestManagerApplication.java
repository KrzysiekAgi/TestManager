package io.github.krzysiekagi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages={"io.github.krzysiekagi"})
public class TestManagerApplication {

	//TODO add logging
	public static void main(String[] args) {
		SpringApplication.run(TestManagerApplication.class, args);
	}

}
