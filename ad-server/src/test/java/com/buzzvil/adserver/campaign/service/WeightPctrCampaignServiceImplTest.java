package com.buzzvil.adserver.campaign.service;

import com.buzzvil.adserver.campaign.repository.CampaignRepository;
import com.buzzvil.adserver.campaign.repository.entity.Campaign;
import com.buzzvil.adserver.external.api.component.PctrComponentImpl;
import com.buzzvil.adserver.external.api.component.ResponseVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeightPctrCampaignServiceImplTest {

    @InjectMocks
    private WeightPctrCampaignServiceImpl weightPctrCampaignServiceImpl;

    @Mock
    private PctrComponentImpl pctrComponentImpl;

    @Mock
    private CampaignRepository campaignRepository;


    @Test
    @DisplayName("예측된 CTR이 가장 높은 광고를 첫 번째에 위치하고 나머지 두 광고는 weight 기반으로 정렬하는 정책")
    void getCampaign() {

        List<Campaign> campaignsMockData = List.of(Campaign.builder().id(6L).imageUrl("https://image.buzzvil.com/image_6.jpg").landingUrl("https://landing.buzzvil.com/landing_6").name("campaign name 6").reward(4L).targetCountry("JP").targetGender("F").weight(8700L).build(),
                Campaign.builder().id(44L).imageUrl("https://image.buzzvil.com/image_44.jpg").landingUrl("https://landing.buzzvil.com/landing_44").name("campaign name 44").reward(6L).targetCountry("JP").targetGender("F").weight(7700L).build(),
                Campaign.builder().id(129L).imageUrl("https://image.buzzvil.com/image_129.jpg").landingUrl("https://landing.buzzvil.com/landing_129").name("campaign name 129").reward(8L).targetCountry("JP").targetGender("F").weight(2000L).build(),
                Campaign.builder().id(149L).imageUrl("https://image.buzzvil.com/image_149.jpg").landingUrl("https://landing.buzzvil.com/landing_149").name("campaign name 149").reward(7L).targetCountry("JP").targetGender("F").weight(8400L).build(),
                Campaign.builder().id(160L).imageUrl("https://image.buzzvil.com/image_160.jpg").landingUrl("https://landing.buzzvil.com/landing_160").name("campaign name 160").reward(9L).targetCountry("JP").targetGender("F").weight(3700L).build(),
                Campaign.builder().id(166L).imageUrl("https://image.buzzvil.com/image_166.jpg").landingUrl("https://landing.buzzvil.com/landing_166").name("campaign name 166").reward(4L).targetCountry("JP").targetGender("F").weight(2100L).build(),
                Campaign.builder().id(281L).imageUrl("https://image.buzzvil.com/image_281.jpg").landingUrl("https://landing.buzzvil.com/landing_281").name("campaign name 281").reward(2L).targetCountry("JP").targetGender("F").weight(2700L).build(),
                Campaign.builder().id(283L).imageUrl("https://image.buzzvil.com/image_283.jpg").landingUrl("https://landing.buzzvil.com/landing_283").name("campaign name 283").reward(9L).targetCountry("JP").targetGender("F").weight(4200L).build(),
                Campaign.builder().id(335L).imageUrl("https://image.buzzvil.com/image_335.jpg").landingUrl("https://landing.buzzvil.com/landing_335").name("campaign name 335").reward(3L).targetCountry("JP").targetGender("F").weight(2600L).build(),
                Campaign.builder().id(338L).imageUrl("https://image.buzzvil.com/image_338.jpg").landingUrl("https://landing.buzzvil.com/landing_338").name("campaign name 338").reward(5L).targetCountry("JP").targetGender("F").weight(2700L).build(),
                Campaign.builder().id(356L).imageUrl("https://image.buzzvil.com/image_356.jpg").landingUrl("https://landing.buzzvil.com/landing_356").name("campaign name 356").reward(5L).targetCountry("JP").targetGender("F").weight(400L).build());
        when(campaignRepository.findAllByTargetGenderAndTargetCountry("F", "JP")).thenReturn(campaignsMockData);

        final ResponseVo responseVo = new ResponseVo(List.of(0.048680115261289646, 0.010905115460975723, 0.01602321437131184, 0.04757238316180005, 0.04683507356127195, 0.043842060075951234, 0.03154007644427326, 0.002086951398959519, 0.03729563576930934, 0.03233522831139179, 0.014182692584450974));

        final ResponseEntity<ResponseVo> of = ResponseEntity.of(Optional.of(responseVo));

        when(pctrComponentImpl.getPctr(BigInteger.ONE, "6,44,129,149,160,166,281,283,335,338,356")).thenReturn(of);

        List<Campaign> weightMockData = List.of(Campaign.builder().id(149L).imageUrl("https://image.buzzvil.com/image_149.jpg").landingUrl("https://landing.buzzvil.com/landing_149").name("campaign name 149").reward(7L).targetCountry("JP").targetGender("F").weight(8400L).build(),
                Campaign.builder().id(44L).imageUrl("https://image.buzzvil.com/image_44.jpg").landingUrl("https://landing.buzzvil.com/landing_44").name("campaign name 44").reward(6L).targetCountry("JP").targetGender("F").weight(7700L).build());

        when(campaignRepository.findTop2ByTargetGenderAndTargetCountryAndIdNotOrderByWeightDesc(6L, "F", "JP")).thenReturn(weightMockData);

        final List<CampaignDto> campaign = weightPctrCampaignServiceImpl.getCampaign(BigInteger.ONE, "F", "JP");

        assertEquals(3, campaign.stream().map(cp -> cp.getId()).count());

        assertEquals(List.of(6L, 149L, 44L), campaign.stream().map(cp -> cp.getId()).collect(Collectors.toList()));

    }
}