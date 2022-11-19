package com.annagev.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController {
    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody CreateUserDto dto) {

        final User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        final User savedUser = userRepository.save(user);

        final UserDto responseDto = new UserDto();
        responseDto.setName(savedUser.getName());
        responseDto.setSurname(savedUser.getSurname());
        responseDto.setEmail(savedUser.getEmail());
        responseDto.setPhone(savedUser.getPhone());

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        List<User> users = this.userRepository.findAll();
        if (users != null) {
            return ResponseEntity.ok(users.stream().map(u ->
                            new UserDto(u.getName(), u.getSurname(), u.getEmail(), u.getPhone()))
                    .collect(Collectors.toList()));
        }
        return ResponseEntity.ok(new ArrayList<>());

    }
}
