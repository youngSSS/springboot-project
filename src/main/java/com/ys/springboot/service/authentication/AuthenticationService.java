package com.ys.springboot.service.authentication;

import com.ys.springboot.domain.member.Member;
import com.ys.springboot.domain.member.MemberRepository;
import com.ys.springboot.web.dto.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final MemberRepository memberRepository;

    // Sign Up
    @Transactional
    public Long signUp(SignUpRequestDto requestDto) {
        return memberRepository.save(requestDto.toEntity()).getId();
    }

    // Check User ID for duplicates
    @Transactional
    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    // Check Email for duplicates
    @Transactional
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // 로그인
//    @Override
//    public UserDetails loadUserByUserId(String userId) throws Exception {
//        List<Authentication>
//    }

}
