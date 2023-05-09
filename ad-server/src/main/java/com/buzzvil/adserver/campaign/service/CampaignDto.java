package com.buzzvil.adserver.campaign.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDto {
    private Long id;

    private String name;

    private String imageUrl;

    private String landingUrl;

    private Long weight;

    private Long reward;
}
