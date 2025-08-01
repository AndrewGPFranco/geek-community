package com.agp.ead;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.flyway.enabled=false")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class EadApplicationTests {

	@Test
	void contextLoads() {
	}

}
