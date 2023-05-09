package com.buzzvil.adserver.campaign.repository.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ad_campaign")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {

    @Id
    private Long id;

    private String name;

    private String imageUrl;

    private String landingUrl;

    private Long weight;

    private String targetCountry;

    private String targetGender;

    private Long reward;

}
