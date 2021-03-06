package com.ys.springboot.web;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ys.springboot.domain.portfolio.Portfolio;
import com.ys.springboot.domain.portfolio.PortfolioRepository;
import com.ys.springboot.web.dto.PortfolioDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PortfolioApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        portfolioRepository.deleteAll();
    }

    @Test
    public void Portfolio_????????????() throws Exception {
        // given
        String url = "http://localhost:" + port + "/api/portfolio";

        String aboutMe = "about me";
        String profile = "profile";
        String contact = "contact";
        String skill = "skill";
        String project = "project";

        PortfolioDto portfolioDto = PortfolioDto.builder()
                .aboutMe(aboutMe)
                .profile(profile)
                .contact(contact)
                .skill(skill)
                .project(project)
                .build();

        // when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(portfolioDto)))
                .andExpect(status().isOk());

        List<Portfolio> portfolioList = portfolioRepository.findAll();

        // then
        Portfolio portfolio = portfolioList.get(0);
        assertThat(portfolio.getAboutMe()).isEqualTo(aboutMe);
        assertThat(portfolio.getProfile()).isEqualTo(profile);
        assertThat(portfolio.getContact()).isEqualTo(contact);
        assertThat(portfolio.getSkill()).isEqualTo(skill);
        assertThat(portfolio.getProject()).isEqualTo(project);
    }

    @Test
    public void Portfolio_????????????() throws Exception {
        // given
        Portfolio savedPortfolio = portfolioRepository.save(Portfolio.builder()
                .aboutMe("about Me")
                .profile("profile")
                .contact("contact")
                .skill("skill")
                .project("project")
                .build());

        Long updateId = savedPortfolio.getId();
        String expectedAboutMe = "Modified About Me";
        String expectedProfile = "Modified Profile";
        String expectedContact = "Modified Contact";
        String expectedSkill = "Modified Skill";
        String expectedProject = "Modified Project";

        PortfolioDto requestDto = PortfolioDto.builder()
                .aboutMe(expectedAboutMe)
                .profile(expectedProfile)
                .contact(expectedContact)
                .skill(expectedSkill)
                .project(expectedProject)
                .build();

        String url = "http://localhost:" + port + "/api/portfolio/" + updateId;

        HttpEntity<PortfolioDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Portfolio> all = portfolioRepository.findAll();
        assertThat(all.get(0).getAboutMe()).isEqualTo(expectedAboutMe);
        assertThat(all.get(0).getProfile()).isEqualTo(expectedProfile);
        assertThat(all.get(0).getContact()).isEqualTo(expectedContact);
        assertThat(all.get(0).getSkill()).isEqualTo(expectedSkill);
        assertThat(all.get(0).getProject()).isEqualTo(expectedProject);

    }


}
