package com.ys.springboot.web.dto;

import com.ys.springboot.domain.user.Role;
import com.ys.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private String name;
    private String email;
    private String picture;
    private String token;
    private Role role;

    @Builder
    public UserDto(String name, String email, String picture, String token, Role role) {

        this.name = name;
        this.email = email;
        this.picture = picture;
        this.token = token;
        this.role = role;
    }

    public UserDto(User entity) {
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
        this.token = entity.getToken();
        this.role = entity.getRole();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .token(token)
                .role(role)
                .build();
    }
}
