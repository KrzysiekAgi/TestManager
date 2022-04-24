package io.github.krzysiekagi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"io.github.krzysiekagi"})
public class TestManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestManagerApplication.class, args);
	}

}
