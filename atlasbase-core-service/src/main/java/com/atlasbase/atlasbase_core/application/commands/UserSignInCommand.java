package com.atlasbase.atlasbase_core.application.commands;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
public final class UserSignInCommand extends BaseCommand {

	private String userName;

	private String password;

}
