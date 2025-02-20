package com.library_api.model.user;

public record RegisterDTO(String login, String password, UserRoles role) {

}
