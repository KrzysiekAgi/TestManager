package io.github.krzysiekagi;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.krzysiekagi.controller.TestCaseController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestManagerApplicationTests {

	@Autowired
	private TestCaseController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
