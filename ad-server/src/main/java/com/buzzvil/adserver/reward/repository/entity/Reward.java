package com.buzzvil.adserver.reward.repository.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
@DynamicUpdate
@Table(name = "ad_reward", catalog = "test")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Reward {

    @Id
    @NonNull
    private BigInteger userId;

    @NonNull
    private BigInteger reserveFund;

    @JsonManagedReference
    @OneToMany(mappedBy = "reward", cascade = CascadeType.ALL)
    private List<RewardHistory> rewardHistories = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    @Version
    private Long version;

    @Builder
    public Reward(BigInteger userId, BigInteger reserveFund, ArrayList<RewardHistory> rewardHistories) {
        this.userId = userId;
        this.reserveFund = reserveFund;
        this.rewardHistories = rewardHistories;
    }
}
