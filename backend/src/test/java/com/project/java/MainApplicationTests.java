package com.project.java;

import com.project.my.MainApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
//@ActiveProfiles("dev")
@SpringBootTest(classes = MainApplication.class)
class MainApplicationTests {

	@Test
	void contextLoads() {
		log.debug("잉?");
	}

}
