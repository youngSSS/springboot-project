package com.ys.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ys.springboot.domain.member.Member;
import com.ys.springboot.domain.member.MemberRepository;
import com.ys.springboot.web.dto.IdDupCheckRequestDto;

import com.ys.springboot.web.dto.SignUpRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// 아래 import들은 @WithMockUser annotation을 사용하기 위한 import들
// @SpringBooTest에서 MockMvc를 사용하기 위함
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.swing.tree.ExpandVetoException;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public  void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        memberRepository.deleteAll();
    }

    @Test
    public void signUpRequest() throws Exception {
        // given
        String name = "김영서";
        String userId = "young4127s";
        String password = "123123";
        String email = "young4127s@naver.com";

        SignUpRequestDto requestDto = SignUpRequestDto.builder()
                .name(name)
                .userId(userId)
                .password(password)
                .email(email)
                .build();

        String url = "http://localhost:" + port + "/api/authentication/sign-up";

        // when
        mvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
            .andExpect(status().isOk());

        // then
        List<Member> all = memberRepository.findAll();

        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getUserId()).isEqualTo(userId);
        assertThat(all.get(0).getPassword()).isEqualTo(password);
        assertThat(all.get(0).getEmail()).isEqualTo(email);
    }

    @Test
    public void idDupCheckRequest() throws Exception {
        // given
        String name = "김영서";
        String userId = "young4127s";
        String password = "123123";
        String email = "young4127s@naver.com";

        SignUpRequestDto postRequestDto = SignUpRequestDto.builder()
                .name(name)
                .userId(userId)
                .password(password)
                .email(email)
                .build();

        String potsUrl = "http://localhost:" + port + "/api/authentication/sign-up";
        String getUrl = "http://localhost:" + port + "/api/authentication/id-dup-check/" + userId;

        // when
        mvc.perform(post(potsUrl)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(postRequestDto)))
                .andExpect(status().isOk());

        // then
        ResponseEntity<String> response = restTemplate.getForEntity(getUrl, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
