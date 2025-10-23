package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.TestFixtures;
import com.atlasbase.atlasbase_core.core.model.User;
import com.atlasbase.atlasbase_core.core.port.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserControllerIntegrationTest {

	@LocalServerPort
	private Integer port;

	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

	@BeforeAll
	static void beforeAll() {
		postgres.start();
	}

	@AfterAll
	static void afterAll() {
		postgres.stop();
	}

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost:" + port;
		repository.deleteAll();
	}

	@Test
	void shouldSignInUser() {
		User user = TestFixtures.userMock();
		user.setPassword(passwordEncoder.encode("password"));

		List<User> users = List.of(user);
		repository.saveAll(users);

		String jsonBody = """
				{
				    "userName": "test-user",
				    "password": "password"
				}
				""";

		given().contentType(ContentType.JSON)
			.body(jsonBody)
			.when()
			.post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-in")
			.then()
			.statusCode(200)
			.body(equalTo("Authenticated"));
	}

	@Test
	void shouldSignUpUser() {
		String jsonBody = """
				{
				    "userName": "test-user",
				    "password": "password",
				    "firstName": "John",
				    "lastName": "Doe",
				    "email": "johndoe@gmail.com"
				}
				""";

		given().contentType(ContentType.JSON)
			.body(jsonBody)
			.when()
			.post(TestFixtures.USER_CONTROLLER_BASE_PATH + "/sign-up")
			.then()
			.statusCode(201);
	}

}