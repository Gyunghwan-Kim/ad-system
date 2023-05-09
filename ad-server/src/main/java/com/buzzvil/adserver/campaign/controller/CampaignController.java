package com.buzzvil.adserver.campaign.controller;

import com.buzzvil.adserver.campaign.repository.entity.Campaign;
import com.buzzvil.adserver.campaign.service.CampaignDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/campaign")
@AllArgsConstructor
public class CampaignController {

    private final List<CampaignService> campaignService;

    @GetMapping("{userId}/{gender}/{country}")
    public List<CampaignDto> campaigns(@PathVariable("userId") BigInteger userId, @PathVariable("gender") String gender, @PathVariable("country") String country) {
        final int index = userId.intValue() % 4;
        return this.campaignService.get(index).getCampaign(userId, gender, country);
    }
}
