package com.ys.springboot.web;

import com.ys.springboot.domain.member.Member;
import com.ys.springboot.service.authentication.AuthenticationService;
import com.ys.springboot.web.dto.IdDupCheckRequestDto;
import com.ys.springboot.web.dto.SignUpRequestDto;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // Sign Up
    @PostMapping("/api/authentication/sign-up")
    public Long signUp(@RequestBody SignUpRequestDto requestDto) {
        return authenticationService.signUp(requestDto);
    }

    // Check User ID for duplicates
    @GetMapping("/api/authentication/id-dup-check/{userId}")
    public String idDupCheck(@PathVariable String userId) {
        Member member = authenticationService.findByUserId(userId);
        String dupFlag;

        if (member == null) dupFlag = "false";
        else dupFlag = "true";

        JsonObject obj = new JsonObject();
        JsonObject data = new JsonObject();

        data.addProperty("dupFlag", dupFlag);

        obj.addProperty("code", 200);
        obj.add("data", data);

        return obj.toString();
    }

    // Check Email for duplicates
    @ResponseBody
    @GetMapping("/api/authentication/email-dup-check/{email}")
    public String emailDupCheck(@PathVariable String email) {
        Member member = authenticationService.findByEmail(email);
        String dupFlag;

        if (member == null) dupFlag = "false";
        else dupFlag = "true";

        JsonObject obj = new JsonObject();
        JsonObject data = new JsonObject();

        data.addProperty("dupFlag", dupFlag);

        obj.addProperty("code", 200);
        obj.add("data", data);

        return obj.toString();
    }

}
