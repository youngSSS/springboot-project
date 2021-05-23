package com.ys.springboot.domain.social;

import com.ys.springboot.web.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class NaverOauth implements SocialOauth {

    @Override
    public String getOauthRedirectURL() {
        return "";
    }

    @Override
    public String requestAccessToken(String code) {
        return "";
    }

    @Override
    public UserDto requestUserInfo(String accessToken) {
        return null;
    }

}
