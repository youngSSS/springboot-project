package com.ys.springboot.domain.portfolio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ys.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Portfolio extends BaseTimeEntity {

    @Id
    private Long id = (long) 1;

    @Column
    private String aboutMe;

    @Column
    private String profile;

    @Column
    private String contact;

    @Column
    private String skill;

    @Column
    private String project;

    @Builder
    public Portfolio(String aboutMe, String profile, String contact, String skill, String project) {
        this.aboutMe = aboutMe;
        this.profile = profile;
        this.contact = contact;
        this.skill = skill;
        this.project = project;
    }

    public void update(String aboutMe, String profile, String contact, String skill, String project) {
        this.aboutMe = aboutMe;
        this.profile = profile;
        this.contact = contact;
        this.skill = skill;
        this.project = project;
    }

}
