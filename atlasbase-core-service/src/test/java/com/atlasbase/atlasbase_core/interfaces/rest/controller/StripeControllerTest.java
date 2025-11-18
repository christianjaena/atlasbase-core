package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.atlasbase.atlasbase_core.application.services.JwtService;
import com.atlasbase.atlasbase_core.infrastructure.configuration.SecurityConfiguration;
import com.stripe.StripeClient;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.service.CustomerService;
import com.stripe.service.V1Services;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StripeController.class)
@Import(SecurityConfiguration.class)
class StripeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AuthenticationManager manager;

	@MockitoBean
	private JwtService jwtService;

	@MockitoBean
	private UserDetailsService userDetailsService;

	@MockitoBean
	private StripeClient stripeClient;

	// TODO: Add more test cases
	@Test
	void shouldCreateCustomer() throws Exception {
		V1Services v1ServicesMock = mock(V1Services.class);
		Customer customerMock = mock(Customer.class);
		CustomerService customerServiceMock = mock(CustomerService.class);

		when(customerMock.getName()).thenReturn("Christian");
		when(customerMock.getEmail()).thenReturn("jaenachristian@gmail.com");

		when(stripeClient.v1()).thenReturn(v1ServicesMock);
		when(v1ServicesMock.customers()).thenReturn(customerServiceMock);
		when(customerServiceMock.create(any(CustomerCreateParams.class))).thenReturn(customerMock);

		UUID userId = UUID.randomUUID();
		String requestBody = """
				{
				    "userId":"%s"
				}
				""".formatted(userId);

		mockMvc.perform(post("/api/v1/stripe").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("Christian"))
			.andExpect(jsonPath("$.email").value("jaenachristian@gmail.com"));
	}

}