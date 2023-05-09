package com.buzzvil.adserver.campaign.service;

import com.buzzvil.adserver.campaign.controller.CampaignService;
import com.buzzvil.adserver.campaign.repository.CampaignRepository;
import com.buzzvil.adserver.campaign.repository.entity.Campaign;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service(value = "randomCampaignServiceImpl")
@AllArgsConstructor
@Order(0)
public class RandomCampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;

    @Override
    public List<CampaignDto> getCampaign(BigInteger userId, String gender, String country) {
        final List<Campaign> campaigns = this.campaignRepository.findRandomTop3ByTargetGenderAndTargetCountryOrderByWeightDesc(gender, country);
        return extracted(campaigns);
    }
}
