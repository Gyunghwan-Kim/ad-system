package com.buzzvil.adserver.reward.repository;

import com.buzzvil.adserver.reward.repository.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Repository
public interface RewardRepository extends JpaRepository<Reward, BigInteger> {

    Reward findByUserId(BigInteger userId);

    Reward findByUserIdAndRewardHistoriesCreateAtGreaterThan(BigInteger userID, LocalDateTime localDateTime);

    Boolean existsByUserIdAndRewardHistoriesAdId(BigInteger userID, BigInteger adId);

}
