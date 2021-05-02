package com.ys.springboot.web;

import com.ys.springboot.domain.member.Member;
import com.ys.springboot.service.authentication.AuthenticationService;
import com.ys.springboot.web.dto.IdDupCheckRequestDto;
import com.ys.springboot.web.dto.SignUpRequestDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // Sign Up
    @PostMapping("/api/authentication/sign-up")
    public Long signUp(@RequestBody SignUpRequestDto requestDto) {
        return authenticationService.signUp(requestDto);
    }

    // Duplicate Check
    @GetMapping("/api/authentication/id-dup-check/{userId}")
    public boolean idDupCheck(@PathVariable String userId) {
        Member member = authenticationService.findByUserId(userId);

        if (member == null) return false;
        else return true;
    }

}
