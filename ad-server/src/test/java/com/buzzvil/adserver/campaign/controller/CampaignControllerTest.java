package com.buzzvil.adserver.campaign.controller;

import com.buzzvil.adserver.campaign.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CampaignController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private List<CampaignService> campaignService;

    @MockBean
    private RandomCampaignServiceImpl randomCampaignServiceImpl;
    @MockBean
    private WeightCampaignServiceImpl weightCampaignServiceImpl;

    @MockBean
    private PctrCampaignServiceImpl pctrCampaignServiceImpl;

    @MockBean
    private WeightPctrCampaignServiceImpl weightPctrCampaignServiceImpl;

    @Test
    @DisplayName("사용자 아이디를 4로나눈 아이디값이 0인 경우")
    void campaignsTest_0() throws Exception {

        when(campaignService.get(0)).thenReturn(randomCampaignServiceImpl);

        when(campaignService.get(0).getCampaign(new BigInteger(String.valueOf(0)), "F", "JP")).thenReturn(List.of(CampaignDto.builder().id(6L).imageUrl("https://image.buzzvil.com/image_6.jpg").landingUrl("https://landing.buzzvil.com/landing_6").name("campaign name 6").reward(4L).weight(8700L).build(),
                CampaignDto.builder().id(44L).imageUrl("https://image.buzzvil.com/image_44.jpg").landingUrl("https://landing.buzzvil.com/landing_44").name("campaign name 44").reward(6L).weight(7700L).build(),
                CampaignDto.builder().id(129L).imageUrl("https://image.buzzvil.com/image_129.jpg").landingUrl("https://landing.buzzvil.com/landing_129").name("campaign name 129").reward(8L).weight(2000L).build()));

        mockMvc.perform(get("/campaign/4/F/JP")).andExpect(status().isOk());

        verify(campaignService.get(0), atLeastOnce()).getCampaign(new BigInteger(String.valueOf(4)), "F", "JP");

    }

    @Test
    @DisplayName("사용자 아이디를 4로나눈 아이디값이 1인 경우")
    void campaignsTest_1() throws Exception {
        when(campaignService.get(1)).thenReturn(weightCampaignServiceImpl);

        when(campaignService.get(1).getCampaign(new BigInteger(String.valueOf(1)), "F", "JP")).thenReturn(List.of(CampaignDto.builder().id(6L).imageUrl("https://image.buzzvil.com/image_6.jpg").landingUrl("https://landing.buzzvil.com/landing_6").name("campaign name 6").reward(4L).weight(8700L).build(),
                CampaignDto.builder().id(44L).imageUrl("https://image.buzzvil.com/image_44.jpg").landingUrl("https://landing.buzzvil.com/landing_44").name("campaign name 44").reward(6L).weight(7700L).build(),
                CampaignDto.builder().id(129L).imageUrl("https://image.buzzvil.com/image_129.jpg").landingUrl("https://landing.buzzvil.com/landing_129").name("campaign name 129").reward(8L).weight(2000L).build()));

        mockMvc.perform(get("/campaign/1/F/JP")).andExpect(status().isOk());

        verify(campaignService.get(1), atLeastOnce()).getCampaign(new BigInteger(String.valueOf(1)), "F", "JP");
    }

    @Test
    @DisplayName("사용자 아이디를 4로나눈 아이디값이 2인 경우")
    void campaignsTest_2() throws Exception {
        when(campaignService.get(2)).thenReturn(pctrCampaignServiceImpl);

        when(campaignService.get(2).getCampaign(new BigInteger(String.valueOf(2)), "F", "JP")).thenReturn(List.of(CampaignDto.builder().id(6L).imageUrl("https://image.buzzvil.com/image_6.jpg").landingUrl("https://landing.buzzvil.com/landing_6").name("campaign name 6").reward(4L).weight(8700L).build(),
                CampaignDto.builder().id(44L).imageUrl("https://image.buzzvil.com/image_44.jpg").landingUrl("https://landing.buzzvil.com/landing_44").name("campaign name 44").reward(6L).weight(7700L).build(),
                CampaignDto.builder().id(129L).imageUrl("https://image.buzzvil.com/image_129.jpg").landingUrl("https://landing.buzzvil.com/landing_129").name("campaign name 129").reward(8L).weight(2000L).build()));

        mockMvc.perform(get("/campaign/2/F/JP")).andExpect(status().isOk());

        verify(campaignService.get(2), atLeastOnce()).getCampaign(new BigInteger(String.valueOf(2)), "F", "JP");
    }

    @Test
    @DisplayName("사용자 아이디를 4로나눈 아이디값이 3인 경우")
    void campaignsTest_3() throws Exception {
        when(campaignService.get(3)).thenReturn(weightPctrCampaignServiceImpl);

        when(campaignService.get(3).getCampaign(new BigInteger(String.valueOf(3)), "F", "JP")).thenReturn(List.of(CampaignDto.builder().id(6L).imageUrl("https://image.buzzvil.com/image_6.jpg").landingUrl("https://landing.buzzvil.com/landing_6").name("campaign name 6").reward(4L).weight(8700L).build(),
                CampaignDto.builder().id(44L).imageUrl("https://image.buzzvil.com/image_44.jpg").landingUrl("https://landing.buzzvil.com/landing_44").name("campaign name 44").reward(6L).weight(7700L).build(),
                CampaignDto.builder().id(129L).imageUrl("https://image.buzzvil.com/image_129.jpg").landingUrl("https://landing.buzzvil.com/landing_129").name("campaign name 129").reward(8L).weight(2000L).build()));

        mockMvc.perform(get("/campaign/3/F/JP")).andExpect(status().isOk());

        verify(campaignService.get(3), atLeastOnce()).getCampaign(new BigInteger(String.valueOf(3)), "F", "JP");
    }

}