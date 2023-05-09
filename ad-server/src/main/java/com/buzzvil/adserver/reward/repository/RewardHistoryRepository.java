package com.buzzvil.adserver.reward.repository;

import com.buzzvil.adserver.reward.repository.entity.RewardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface RewardHistoryRepository extends JpaRepository<RewardHistory, BigInteger> {
}
