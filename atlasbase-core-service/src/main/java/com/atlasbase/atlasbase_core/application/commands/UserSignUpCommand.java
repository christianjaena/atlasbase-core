package com.atlasbase.atlasbase_core.application.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public final class UserSignUpCommand extends BaseCommand {

	private String userName;

	private String password;

	private String email;

	private String firstName;

	private String lastName;

}
