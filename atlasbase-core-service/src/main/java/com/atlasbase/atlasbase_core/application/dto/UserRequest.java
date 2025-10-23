package com.atlasbase.atlasbase_core.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(@NotBlank String userName, String firstName, String lastName, @Email String email,
		@NotBlank String password) {
}
