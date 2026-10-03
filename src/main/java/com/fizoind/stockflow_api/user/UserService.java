package com.fizoind.stockflow_api.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserCreateDto createUser(UserCreateDto userCreateDto) {
        User user = new User();

        Optional<User> savedUser = userRepository.findByUsername(userCreateDto.getUsername());

        if (!savedUser.isPresent()) {
            user = UserMapper.toEntity(userCreateDto);
        }

        userRepository.save(user);

        return UserMapper.toDto(user);
    }
}
