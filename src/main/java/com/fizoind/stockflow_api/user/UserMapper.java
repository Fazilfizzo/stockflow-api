package com.fizoind.stockflow_api.user;

public class UserMapper {

    public static UserCreateDto toDto(User user) {
        return new UserCreateDto(user.getUsername(), user.getPassword(), user.getEmail(), user.getRole());
    }

    public static User toEntity(UserCreateDto userCreateDto) {
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setEmail(userCreateDto.getEmail());
        user.setRole(userCreateDto.getRole());

        return user;
    }
}
