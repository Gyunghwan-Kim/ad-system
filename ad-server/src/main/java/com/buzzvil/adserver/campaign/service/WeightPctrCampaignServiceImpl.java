package com.buzzvil.adserver.campaign.service;

import com.buzzvil.adserver.campaign.repository.entity.Campaign;
import com.buzzvil.adserver.campaign.repository.CampaignRepository;
import com.buzzvil.adserver.external.api.component.PctrComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Collections.reverseOrder;

@Slf4j
@Service(value = "weightPctrCampaignServiceImpl")
@Order(3)
public class WeightPctrCampaignServiceImpl extends PctrCampaignServiceImpl {

    public WeightPctrCampaignServiceImpl(CampaignRepository campaignRepository, PctrComponent pctrComponentImpl) {
        super(campaignRepository, pctrComponentImpl);
    }

    protected int getLimitCnt() {
        return 1;
    }

    protected Comparator<Map.Entry<Double, Campaign>> getComparator() {
        return reverseOrder(super.getComparator());
    }

    @Override
    public List<CampaignDto> getCampaign(BigInteger userId, String gender, String country) {

        final List<CampaignDto> campaign = super.getCampaign(userId, gender, country);

        final Long id = campaign.stream().findFirst().get().getId();

        campaign.addAll(extracted(super.campaignRepository.findTop2ByTargetGenderAndTargetCountryAndIdNotOrderByWeightDesc(id, gender, country)));

        return campaign;

    }
}
