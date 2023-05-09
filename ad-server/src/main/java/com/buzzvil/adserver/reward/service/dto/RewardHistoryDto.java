package com.buzzvil.adserver.reward.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardHistoryDto {

    private BigInteger reserveFund;

    private LocalDateTime createAt;
}
