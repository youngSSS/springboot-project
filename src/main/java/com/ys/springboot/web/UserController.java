package com.ys.springboot.web;

import com.ys.springboot.service.user.UserService;
import com.ys.springboot.web.dto.UserDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/api/login")
    public UserDto save(@RequestBody UserDto userDto) {
        System.out.println("------------- " + userDto);

        return userService.findById(userService.save(userDto));
    }
}
