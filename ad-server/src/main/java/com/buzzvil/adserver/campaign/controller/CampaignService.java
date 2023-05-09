package com.buzzvil.adserver.campaign.controller;

import com.buzzvil.adserver.campaign.repository.entity.Campaign;
import com.buzzvil.adserver.campaign.service.CampaignDto;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public interface CampaignService {

    default List<CampaignDto> extracted(List<Campaign> campaigns) {
        return campaigns.stream().map(ca -> CampaignDto.builder().id(ca.getId()).name(ca.getName()).imageUrl(ca.getImageUrl()).landingUrl(ca.getLandingUrl()).reward(ca.getReward()).weight(ca.getWeight()).build()).collect(Collectors.toList());
    }

    List<CampaignDto> getCampaign(BigInteger userId, String gender, String country);
}
