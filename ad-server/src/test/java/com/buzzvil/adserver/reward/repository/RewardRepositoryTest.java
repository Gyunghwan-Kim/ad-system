package com.buzzvil.adserver.reward.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase
class RewardRepositoryTest {

    @Autowired
    private RewardRepository rewardRepository;

    @Test
    void findByUserId() {

    }

    @Test
    void findByUserIdAndRewardHistoriesCreateAtGreaterThan() {

    }

    @Test
    void existsByUserIdAndRewardHistoriesAdId() {

    }
}