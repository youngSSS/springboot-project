package com.ys.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// DTO (Data Transfer Object)
// 계층간 데이터 교환을 위한 JAVA bins
// (계층이란 Controlle, View, Business Layer, Persistent Layer를 말하며,
// 각 계층간 데이터 교환을 위한 객체를 DTO 또는 VO라고 부른다)

// @Getter는 선언한 모든 필드의 get 메소드를 생성해 준다
// @RequiredArgsConstructor는 선언된 모든 final 필드가 포함된 생성자를 생성해 준다 (final만 생성)

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {

    private final String name;
    private final int amount;

}
