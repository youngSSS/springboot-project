package com.ys.springboot.web.dto;

import com.ys.springboot.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
    private String name;
    private String userId;
    private String password;
    private String email;

    @Builder
    public SignUpRequestDto(String name, String userId, String password, String email) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .userId(userId)
                .password(password)
                .email(email)
                .build();
    }
}
