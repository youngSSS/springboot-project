package com.ys.springboot.web.dto;

import com.ys.springboot.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IdDupCheckRequestDto {
    private String userId;

    @Builder
    public IdDupCheckRequestDto(String userId) {
        this.userId = userId;
    }

    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .build();
    }

}
