package com.buzzvil.adserver.campaign.service;

import com.buzzvil.adserver.campaign.repository.CampaignRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class RandomCampaignServiceImplTest {

    @InjectMocks
    private PctrCampaignServiceImpl pctrCampaignServiceImpl;

    @Mock
    private CampaignRepository campaignRepository;

    @Test
    @DisplayName("랜덤으로 정렬하는 정책")
    void getCampaign() {

    }
}