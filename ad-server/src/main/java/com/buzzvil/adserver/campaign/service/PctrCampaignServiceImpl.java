package com.buzzvil.adserver.campaign.service;

import com.buzzvil.adserver.campaign.controller.CampaignService;
import com.buzzvil.adserver.campaign.repository.entity.Campaign;
import com.buzzvil.adserver.campaign.repository.CampaignRepository;
import com.buzzvil.adserver.external.api.component.PctrComponent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.reverseOrder;

@Slf4j
@Service(value = "pctrCampaignServiceImpl")
@AllArgsConstructor
@Order(2)
public class PctrCampaignServiceImpl implements CampaignService {

    protected final CampaignRepository campaignRepository;

    protected final PctrComponent pctrComponentImpl;

    // todo 정렬 관련 로직을 파라마터로 받을 수 있도록 작업해야한다.
    protected List<Campaign> highWeightCampaignByLimitCnt(List<Campaign> targetCampaign, List<Double> pctr, int limitCnt) {
        final List<Campaign> highWeightCampaign = IntStream.range(0, targetCampaign.size())
                .boxed()
                .collect(Collectors.toMap(pctr::get, targetCampaign::get)).entrySet().stream()
                .sorted(getComparator())
                .limit(limitCnt).map(Map.Entry::getValue)
                .collect(Collectors.toList());
        return highWeightCampaign;
    }

    protected int getLimitCnt() {
        return 3;
    }

    protected Comparator<Map.Entry<Double, Campaign>> getComparator(){
        return Map.Entry.comparingByKey();
    }

    @Override
    public List<CampaignDto> getCampaign(BigInteger userId, String gender, String country) {

        final List<Campaign> targetCampaign = this.campaignRepository.findAllByTargetGenderAndTargetCountry(gender, country);

        final String campaignIds = targetCampaign.stream().map(Campaign::getId).map(String::valueOf).collect(Collectors.joining(","));

        final List<Double> pctr = pctrComponentImpl.getPctr(userId, campaignIds).getBody().getPctr();

        final List<Campaign> campaigns = highWeightCampaignByLimitCnt(targetCampaign, pctr, this.getLimitCnt());

        return extracted(campaigns);
    }


}
