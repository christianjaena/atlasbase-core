package com.atlasbase.atlasbase_core.application.dto;

public record UserRequest(String userName, String firstName, String lastName, String email, String password) {
}
