package com.buzzvil.adserver.reward.controller;

import com.buzzvil.adserver.reward.service.dto.RewardDto;
import com.buzzvil.adserver.reward.service.dto.RewardHistoryDto;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public interface RewardService {

    RewardDto reserves(BigInteger userId, BigInteger reserveFund, BigInteger adId);

    RewardDto subtraction(BigInteger userId, BigInteger reserveFund, BigInteger adId);

    List<RewardHistoryDto> history(BigInteger userId, LocalDateTime localDateTime);

    RewardDto balance(BigInteger userId);
}
