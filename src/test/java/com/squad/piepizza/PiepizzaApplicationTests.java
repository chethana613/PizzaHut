package com.squad.piepizza;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.squad.pizzahut.PiepizzaApplication;

@SpringBootTest
class PiepizzaApplicationTests {

	@Test
	public void applicationTest() {
		PiepizzaApplication.main(new String[] {});
		assertTrue(true);
	}
}
