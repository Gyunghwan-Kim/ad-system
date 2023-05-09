package com.buzzvil.adserver.reward.service;

import com.buzzvil.adserver.reward.repository.RewardHistoryRepository;
import com.buzzvil.adserver.reward.repository.RewardRepository;
import com.buzzvil.adserver.reward.repository.entity.Reward;
import com.buzzvil.adserver.reward.repository.entity.RewardHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @InjectMocks
    private RewardServiceImpl rewardServiceImpl;

    @Mock
    private RewardRepository rewardRepository;

    @Mock
    private RewardHistoryRepository rewardHistoryRepository;


    @Test
    @DisplayName("이미 지급한 리워드 일 경우.")
    void reserves_0() {
        final BigInteger adId = new BigInteger(String.valueOf(6L));
        when(rewardRepository.existsByUserIdAndRewardHistoriesAdId(BigInteger.ONE, adId)).thenReturn(Boolean.TRUE);
        final RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            rewardServiceImpl.reserves(BigInteger.ONE, new BigInteger(String.valueOf(10000)), adId);
        });
        String message = runtimeException.getMessage();
        assertEquals("이미 지급 된 리워드 입니다.", message);
        verify(rewardRepository, atLeastOnce()).existsByUserIdAndRewardHistoriesAdId(BigInteger.ONE, adId);
    }

    @Test
    @DisplayName("처음 지급하는 리워드 일 경우")
    void reserves_1() {
        final BigInteger adId = new BigInteger(String.valueOf(6L));
        final BigInteger reserveFund = new BigInteger(String.valueOf(10000));

        when(rewardRepository.existsByUserIdAndRewardHistoriesAdId(BigInteger.ONE, adId)).thenReturn(Boolean.FALSE);

        final Reward mockReward = Reward.builder().userId(BigInteger.ONE).reserveFund(reserveFund).rewardHistories(new ArrayList<>()).build();

        when(rewardRepository.findByUserId(BigInteger.ONE)).thenReturn(mockReward);
        when(rewardRepository.save(any())).thenReturn(mockReward);

        rewardServiceImpl.reserves(BigInteger.ONE, reserveFund, adId);

        verify(rewardRepository, atLeastOnce()).existsByUserIdAndRewardHistoriesAdId(BigInteger.ONE, adId);
        verify(rewardRepository, atLeastOnce()).findByUserId(BigInteger.ONE);
        verify(rewardRepository, atLeastOnce()).save(any());

    }

    @Test
    @DisplayName("추가 지급하는 리워드 일 경우")
    void reserves_2() {

        final BigInteger adId = new BigInteger(String.valueOf(6L));
        final BigInteger reserveFund = new BigInteger(String.valueOf(10000));

        when(rewardRepository.existsByUserIdAndRewardHistoriesAdId(BigInteger.ONE, adId)).thenReturn(Boolean.FALSE);

        when(rewardRepository.findByUserId(BigInteger.ONE)).thenReturn(null);


        final Reward mockSaveReward = Reward.builder().userId(BigInteger.ONE).reserveFund(reserveFund).build();
        final RewardHistory mockSaveRewardHistory = RewardHistory.builder().reserveFund(reserveFund).reward(mockSaveReward).build();

        when(rewardHistoryRepository.save(any())).thenReturn(mockSaveRewardHistory);

        rewardServiceImpl.reserves(BigInteger.ONE, reserveFund, adId);

        verify(rewardRepository, atLeastOnce()).existsByUserIdAndRewardHistoriesAdId(BigInteger.ONE, adId);
        verify(rewardRepository, atLeastOnce()).findByUserId(BigInteger.ONE);
        verify(rewardHistoryRepository, atLeastOnce()).save(any());
    }


    @Test
    @DisplayName("리워드가 음수 일 경우.")
    void subtraction_0() {
        final BigInteger adId = new BigInteger(String.valueOf(6L));
        final BigInteger reserveFund = new BigInteger(String.valueOf(1000));

        when(rewardRepository.existsByUserIdAndRewardHistoriesAdId(BigInteger.ONE, adId)).thenReturn(Boolean.FALSE);

        final Reward mockReward = Reward.builder().userId(BigInteger.ONE).reserveFund(reserveFund).rewardHistories(new ArrayList<>()).build();

        when(rewardRepository.findByUserId(BigInteger.ONE)).thenReturn(mockReward);

        final RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            rewardServiceImpl.subtraction(BigInteger.ONE, new BigInteger(String.valueOf(10000)), adId);
        });
        String message = runtimeException.getMessage();
        assertEquals("적립금은 음수가 될 수 없습니다.", message);

        verify(rewardRepository, atLeastOnce()).existsByUserIdAndRewardHistoriesAdId(BigInteger.ONE, adId);
        verify(rewardRepository, atLeastOnce()).findByUserId(BigInteger.ONE);

    }

    @Test
    void history() {
        final BigInteger reserveFund = new BigInteger(String.valueOf(10000));
        final Reward mockReward = Reward.builder().userId(BigInteger.ONE).reserveFund(reserveFund).rewardHistories(new ArrayList<>()).build();
        final LocalDateTime localDateTime = LocalDateTime.now().minusDays(7);

        when(rewardRepository.findByUserIdAndRewardHistoriesCreateAtGreaterThan(BigInteger.ONE, localDateTime)).thenReturn(mockReward);

        rewardServiceImpl.history(BigInteger.ONE, localDateTime);

        verify(rewardRepository, atLeastOnce()).findByUserIdAndRewardHistoriesCreateAtGreaterThan(BigInteger.ONE, localDateTime);
    }

    @Test
    void balance() {

        final BigInteger reserveFund = new BigInteger(String.valueOf(10000));
        final Reward mockReward = Reward.builder().userId(BigInteger.ONE).reserveFund(reserveFund).rewardHistories(new ArrayList<>()).build();

        when(rewardRepository.findByUserId(BigInteger.ONE)).thenReturn(mockReward);

        rewardServiceImpl.balance(BigInteger.ONE);

        verify(rewardRepository, atLeastOnce()).findByUserId(BigInteger.ONE);
    }
}