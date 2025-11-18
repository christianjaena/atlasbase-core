package com.atlasbase.atlasbase_core.interfaces.rest.controller;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

// TODO: Refactor
@RestController
@RequestMapping("/api/v1/stripe")
public class StripeController {

	private final StripeClient stripeClient;

	public StripeController(StripeClient stripeClient) {
		this.stripeClient = stripeClient;
	}

	@PostMapping
	public ResponseEntity<?> createCustomer(@RequestBody Map<String, UUID> request) throws StripeException {
		var customerParams = CustomerCreateParams.builder()
			.setName("Christian")
			.setEmail("jaenachristian@gmail.com")
			.build();

		Customer customer = stripeClient.v1().customers().create(customerParams);

		return ResponseEntity.ok().body(customer.toJson());
	}

}
