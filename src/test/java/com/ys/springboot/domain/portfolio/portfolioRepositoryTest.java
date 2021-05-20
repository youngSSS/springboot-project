package com.ys.springboot.domain.portfolio;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class portfolioRepositoryTest {

    @Autowired
    PortfolioRepository portfolioRepository;

    @After
    public void cleanup() { portfolioRepository.deleteAll();}

    @Test
    public void 포트폴리오_등록() {
        // given
        String aboutMe = "about me";
        String profile = "profile";
        String contact = "contact";
        String skill = "skill";
        String project = "project";

        portfolioRepository.save(Portfolio.builder()
                .aboutMe(aboutMe)
                .profile(profile)
                .contact(contact)
                .skill(skill)
                .project(project)
                .build());

        // when
        List<Portfolio> portfolioList = portfolioRepository.findAll();

        // then
        Portfolio portfolio = portfolioList.get(0);
        assertThat(portfolio.getAboutMe()).isEqualTo(aboutMe);
        assertThat(portfolio.getProfile()).isEqualTo(profile);
        assertThat(portfolio.getContact()).isEqualTo(contact);
        assertThat(portfolio.getSkill()).isEqualTo(skill);
        assertThat(portfolio.getProject()).isEqualTo(project);
    }

}
