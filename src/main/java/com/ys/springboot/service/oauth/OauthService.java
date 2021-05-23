package com.ys.springboot.service.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ys.springboot.config.auth.helper.SocialLoginType;
import com.ys.springboot.domain.social.SocialOauth;
import com.ys.springboot.domain.user.UserRepository;
import com.ys.springboot.service.user.UserService;
import com.ys.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OauthService {

    private final List<SocialOauth> socialOauthList;
    private final HttpServletResponse response;
    private final UserService userService;
    private final UserRepository userRepository;

    public void redirectLoginRequest(SocialLoginType socialLoginType) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();

        try {
            response.sendRedirect(redirectURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * code 활용하여 access token 발급 및 사용자 정보 요청
     * @param code API Server에서 받은 access token 발급을 위한 code
     */
    public void callbackHandler(SocialLoginType socialLoginType, String code) {
        // Social Login 객체 생성
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);

        // Access Token 요청
        String accessTokenResponse = socialOauth.requestAccessToken(code);

        // 사용자 정보 요청
        UserDto userDto = socialOauth.requestUserInfo(accessTokenResponse);



        // 사용자 정보 저장
        Long userId = userService.findByEmail(userDto.getEmail());

        if (userId != null) {
            userService.updateToken(userId, userDto.getToken());
        }
        else {
            userService.save(userDto);
        }

        try {
            response.sendRedirect("http://localhost:8080");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SocialOauth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }

}
