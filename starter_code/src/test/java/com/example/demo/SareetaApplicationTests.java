package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SareetaApplicationTests {

	@LocalServerPort
	private long port;

	@Test
	public void contextLoads() {
	}

	@Autowired
	private TestRestTemplate template;

	@Test
	public void hasNoAccessIfNotAuthenticated() throws Exception {
		ResponseEntity<String> result = template.getForEntity("http://localhost:" + port + "/api/user/username", String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
	}
}
