package com.buzzvil.adserver.campaign.repository;


import com.buzzvil.adserver.campaign.repository.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findAllByTargetGenderAndTargetCountry(@Param("gender") String gender, @Param("country") String country);

    List<Campaign> findTop3ByTargetGenderAndTargetCountryOrderByWeightDesc(@Param("gender") String gender, @Param("country") String country);

    List<Campaign> findTop2ByTargetGenderAndTargetCountryAndIdNotOrderByWeightDesc(@Param("id") Long id, @Param("gender") String gender, @Param("country") String country);

    @Query(value = "SELECT * FROM ad_campaign WHERE target_gender = :gender AND target_country = :country order by RAND() limit 3",nativeQuery = true)
    List<Campaign> findRandomTop3ByTargetGenderAndTargetCountryOrderByWeightDesc(@Param("gender") String gender, @Param("country") String country);

}