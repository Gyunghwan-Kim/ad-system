package com.buzzvil.adserver.campaign.service;

import com.buzzvil.adserver.campaign.repository.CampaignRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class WeightCampaignServiceImplTest {

    @InjectMocks
    private PctrCampaignServiceImpl pctrCampaignServiceImpl;

    @Mock
    private CampaignRepository campaignRepository;

    @Test
    @DisplayName("weight 기반의 정책")
    void getCampaign() {
    }
}