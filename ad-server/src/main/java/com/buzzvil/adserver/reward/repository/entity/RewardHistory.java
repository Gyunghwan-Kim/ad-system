package com.buzzvil.adserver.reward.repository.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "ad_reward_history", catalog = "test")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class RewardHistory {

    @Id
    @GeneratedValue
    private BigInteger id;

    @NonNull
    private BigInteger reserveFund;

    @NonNull
    private BigInteger adId;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Reward reward;

    @CreatedDate
    private LocalDateTime createAt;

    @Builder
    public RewardHistory(BigInteger reserveFund, BigInteger adId, Reward reward) {
        this.reserveFund = reserveFund;
        this.adId = adId;
        this.reward =reward;
    }
}
