package com.buzzvil.adserver.reward.service;

import com.buzzvil.adserver.reward.controller.RewardService;
import com.buzzvil.adserver.reward.repository.entity.Reward;
import com.buzzvil.adserver.reward.repository.entity.RewardHistory;
import com.buzzvil.adserver.reward.repository.RewardHistoryRepository;
import com.buzzvil.adserver.reward.repository.RewardRepository;
import com.buzzvil.adserver.reward.service.dto.RewardDto;
import com.buzzvil.adserver.reward.service.dto.RewardHistoryDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class RewardServiceImpl implements RewardService {

    private RewardRepository rewardRepository;

    private RewardHistoryRepository rewardHistoryRepository;

    protected Reward calculateReward(BigInteger userId, BigInteger reserveFund, BigInteger adId) {

        final Boolean exists = this.rewardRepository.existsByUserIdAndRewardHistoriesAdId(userId, adId);

        if (!exists) {
            final Reward reward = this.rewardRepository.findByUserId(userId);

            if (reward != null) {

                final BigInteger sumReserveFund = reward.getReserveFund().add(reserveFund);

                if (sumReserveFund.compareTo(BigInteger.ZERO) < 0) {
                    throw new RuntimeException("적립금은 음수가 될 수 없습니다.");
                }

                reward.setReserveFund(sumReserveFund);
                reward.getRewardHistories().add(RewardHistory.builder().reward(reward).reserveFund(reserveFund).build());
                return this.rewardRepository.save(reward);

            } else {

                final Reward saveReward = Reward.builder().userId(userId).reserveFund(reserveFund).build();
                final RewardHistory saveRewardHistory = RewardHistory.builder().reserveFund(reserveFund).reward(saveReward).build();
                return this.rewardHistoryRepository.save(saveRewardHistory).getReward();
            }

        } else {
            throw new RuntimeException("이미 지급 된 리워드 입니다.");
        }
    }

    protected RewardDto convertRewardDto(Reward reward) {
        return RewardDto.builder().reserveFund(reward.getReserveFund()).updateAt(reward.getUpdateAt()).build();
    }

    @Override
    @Transactional
    public RewardDto reserves(BigInteger userId, BigInteger reserveFund, BigInteger adId) {
        final Reward reward = calculateReward(userId, reserveFund, adId);
        return convertRewardDto(reward);
    }

    @Override
    @Transactional
    public RewardDto subtraction(BigInteger userId, BigInteger reserveFund, BigInteger adId) {
        final Reward reward = calculateReward(userId, reserveFund.negate(), adId);
        return convertRewardDto(reward);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RewardHistoryDto> history(BigInteger userId, LocalDateTime localDateTime) {
        final Reward reward = this.rewardRepository.findByUserIdAndRewardHistoriesCreateAtGreaterThan(userId, localDateTime);
        return reward.getRewardHistories().stream().map(rh -> RewardHistoryDto.builder().reserveFund(rh.getReserveFund()).createAt(rh.getCreateAt()).build()).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RewardDto balance(BigInteger userId) {
        final Reward reward = this.rewardRepository.findByUserId(userId);
        return convertRewardDto(reward);
    }
}
