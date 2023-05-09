package com.buzzvil.adserver.reward.controller;

import com.buzzvil.adserver.reward.service.dto.RewardDto;
import com.buzzvil.adserver.reward.service.dto.RewardHistoryDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reward")
@AllArgsConstructor
public class RewardController {

    private RewardService rewardServiceImpl;

    @PostMapping
    public RewardDto reserves(BigInteger userId, BigInteger reserveFund, BigInteger adId) {
        return this.rewardServiceImpl.reserves(userId, reserveFund, adId);
    }

    @DeleteMapping
    public RewardDto subtraction(BigInteger userId, BigInteger reserveFund, BigInteger adId) {
        return this.rewardServiceImpl.subtraction(userId, reserveFund, adId);
    }

    @GetMapping("/{userId}")
    public List<RewardHistoryDto> history(@PathVariable("userId") BigInteger userId) {
        return this.rewardServiceImpl.history(userId, LocalDateTime.now().minusDays(7));
    }

    @GetMapping("/balance/{userId}")
    public RewardDto balance(@PathVariable("userId") BigInteger userId) {
        return this.rewardServiceImpl.balance(userId);
    }
}
