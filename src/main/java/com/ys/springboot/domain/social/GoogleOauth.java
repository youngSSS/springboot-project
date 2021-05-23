package com.ys.springboot.domain.social;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ys.springboot.domain.user.Role;
import com.ys.springboot.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleOauth implements SocialOauth {

    @Value("${sns.google.url}")
    private String GOOGLE_SNS_BASE_URL;
    @Value("${sns.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${sns.google.callback.url}")
    private String GOOGLE_SNS_CALLBACK_URL;
    @Value("${sns.google.client.secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;
    private final String GOOGLE_SNS_TOKEN_BASE_URL = "https://oauth2.googleapis.com/token";

    private final JsonParser jsonParser = new JsonParser();

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();

        params.put("scope", "profile email");
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return GOOGLE_SNS_BASE_URL + "?" + parameterString;
    }

    @Override
    public String requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = null;

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // 여기서 jsonResponse가 null이 될 수 있기 때문에 null을 jsonResponse의 기본값으로 사용
            jsonResponse = responseEntity.getBody();
        }

        return jsonResponse == null ? null : new JsonParser().parse(jsonResponse).getAsJsonObject().get("access_token").getAsString();
    }

    @Override
    public UserDto requestUserInfo(String accessToken) {
        String name, email, picture;
        UserDto userDto = null;

        if (accessToken == null) return null;

        try {
            // Access Token을 사용하여 사용자 정보 요청
            String requestURL = "https://www.googleapis.com/userinfo/v2/me?access_token=" + accessToken;
            HttpURLConnection connection = (HttpURLConnection) new URL(requestURL).openConnection();

            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            int responseCode = connection.getResponseCode();

            // CASE: Access Token을 사용하여 정상적으로 사용자 정보를 받아 온 경우
            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String result = "", line = "";

                while ((line = bufferedReader.readLine()) != null) result += line;

                JsonElement jsonElement = jsonParser.parse(result);

                name = jsonElement.getAsJsonObject().get("name").getAsString();
                email = jsonElement.getAsJsonObject().get("email").getAsString();
                picture = jsonElement.getAsJsonObject().get("picture").getAsString();

                userDto = new UserDto(name, email, picture, accessToken, null);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return userDto;
    }

}
