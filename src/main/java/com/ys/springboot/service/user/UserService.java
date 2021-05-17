package com.ys.springboot.service.user;

import com.google.gson.JsonObject;
import com.ys.springboot.domain.posts.Posts;
import com.ys.springboot.domain.user.User;
import com.ys.springboot.domain.user.UserRepository;
import com.ys.springboot.web.dto.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long save(UserDto userDto) {
        return userRepository.save(userDto.toEntity()).getId();
    }

    public UserDto findById (Long id) {
        User entity = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다. id=" + id));

        return new UserDto(entity);
    }
}
