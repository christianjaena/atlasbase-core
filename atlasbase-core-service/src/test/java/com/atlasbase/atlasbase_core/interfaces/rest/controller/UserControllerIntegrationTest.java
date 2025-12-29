package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.TestFixtures;
import com.atlasbase.atlasbase_core.core.model.User;
import com.atlasbase.atlasbase_core.core.port.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = { "app.jwt.secret=80949c483f94f84d0ada9794852033422" })
class UserControllerIntegrationTest {

	@LocalServerPort
	private Integer port;

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
		.withDatabaseName("postgres")
		.withUsername("postgres")
		.withPassword("postgres");

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private DataSource dataSource;

	@BeforeAll
	static void beforeAll() throws LiquibaseException {
		DataSource dataSource = DataSourceBuilder.create()
			.url(postgres.getJdbcUrl())
			.username(postgres.getUsername())
			.password(postgres.getPassword())
			.build();

		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.yaml");
		liquibase.setDefaultSchema("public");
		liquibase.setDropFirst(true);
		liquibase.afterPropertiesSet();
	}

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost:" + port;
		repository.deleteAll();
	}

	@Test
	void shouldSignInUser() {
		User user = TestFixtures.userMock();
		user.setPassword(passwordEncoder.encode("password"));
		repository.save(user);

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
			.body("data.message", equalTo("User Authenticated"))
			.body("data.token", notNullValue());
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