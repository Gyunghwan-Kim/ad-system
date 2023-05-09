package com.buzzvil.adserver.external.api.component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;

@Slf4j
@AllArgsConstructor
@Component
public class PctrComponentImpl implements PctrComponent {

    protected RestTemplate restTemplate;

    // TODO timeout retry 로직을 추가
    @Override
    public ResponseEntity<ResponseVo> getPctr(BigInteger userId, String campaignIds) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://predict-ctr-pmj4td4sjq-du.a.run.app")
                .queryParam("user_id", userId).queryParam("ad_campaign_ids", campaignIds);

        HttpEntity request = new HttpEntity(null);


        final ResponseEntity<ResponseVo> exchange = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                ResponseVo.class
        );

        return exchange;
    }
}
