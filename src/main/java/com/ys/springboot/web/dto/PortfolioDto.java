package com.ys.springboot.web.dto;

import com.ys.springboot.domain.portfolio.Portfolio;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PortfolioDto {
    private String aboutMe;
    private String profile;
    private String contact;
    private String skill;
    private String project;

    @Builder
    public PortfolioDto(String aboutMe, String profile, String contact, String skill, String project) {
        this.aboutMe = aboutMe;
        this.profile = profile;
        this.contact = contact;
        this.skill = skill;
        this.project = project;
    }

    public PortfolioDto(Portfolio entity) {
        this.aboutMe = entity.getAboutMe();
        this.profile = entity.getProfile();
        this.contact = entity.getContact();
        this.skill = entity.getSkill();
        this.project = entity.getProject();
    }

    public Portfolio toEntity() {
        return Portfolio.builder()
                .aboutMe(aboutMe)
                .profile(profile)
                .contact(contact)
                .skill(skill)
                .project(project)
                .build();
    }


}
