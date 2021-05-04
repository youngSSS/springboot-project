package com.ys.springboot.config.auth;

import com.ys.springboot.service.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// @EnableWebSecurity
// Spring Security 설정들을 활성화한다

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                 //h2-console 화면을 사용하기 위해 해당 옵션들을 disable 한다
                .csrf().disable().headers().frameOptions().disable();
//                .and()
//                    // authorizeRequests로 URL별 권한 관리를 설정한다
//                    // authorizeRequests가 선언되어야만 antMatchers 옵션을 사용할 수 있다
//                    // antMatchers는 권한 관리 대상을 지정하는 옵션이다
//                    // 현재 첫 번째 antMatchers에 지정된 URL들은 전체 열람 권한을 주었고 두 번째는 USER 권한을 가진 사용자만 접근 가능하도록 설정하였다
//                    // anyRequest는 설정된 URL 이외의 것들을 총칭한다 (authenticated를 통해 로그인한 사용자만 접근 가능하도록 설정하였다)
//                    .authorizeRequests()
//                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
////                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
//                    .antMatchers("/api/v1/**").permitAll()
//                    .anyRequest().authenticated()
//                .and()
//                    // Logout 기능 설정의 진입점
//                    .logout()
//                        // Logout 성공 시 / 주소로 이동
//                        .logoutSuccessUrl("/")
//                .and()
//                    // OAuth 2 Login 기능 설정의 진입점
//                    .oauth2Login()
//                        // OAuth 2 Login 성공 이후 사용자 정보를 가져올 때의 설정 담당
//                        .userInfoEndpoint()
//                            // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
//                            // 리소스 서버 (소셜 서비스들)에서 사용자 정보를 가젼온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다
//                            .userService(customOAuth2UserService);
    }

    private AuthenticationService authenticationService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(authenticationService).passwordEncoder(passwordEncoder());
//    }

}
