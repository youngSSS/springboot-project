package com.ys.springboot.web;

import com.ys.springboot.config.auth.helper.SocialLoginType;
import com.ys.springboot.service.oauth.OauthService;
import com.ys.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class OauthController {

    private final OauthService oauthService;

    /**
     * 사용자로부터 SNS 로그인 요청을 Social Login Type 을 받아 처리
     * @param socialLoginType (GOOGLE, NAVER, KAKAO)
     */
    @GetMapping("/oauth/{socialLoginType}")
    public void getSocialLoginType(@PathVariable SocialLoginType socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);

        oauthService.redirectLoginRequest(socialLoginType);
    }

    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     * @param socialLoginType (GOOGLE, NAVER, KAKAO)
     * @param code API Server 로부터 넘어노는 code
     */
    @GetMapping("/oauth/{socialLoginType}/callback")
    public void loginCallback(@PathVariable SocialLoginType socialLoginType, @RequestParam("code") String code) {
        oauthService.callbackHandler(socialLoginType, code);
    }

}
