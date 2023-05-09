package com.buzzvil.adserver.external.api.component;

import org.springframework.http.ResponseEntity;

import java.math.BigInteger;

public interface PctrComponent {

    ResponseEntity<ResponseVo> getPctr(BigInteger userId, String campaignIds);
}
