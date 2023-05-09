package com.buzzvil.adserver.reward.service.dto;


import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;


@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardDto{

    private BigInteger reserveFund;

    private LocalDateTime updateAt;
}
