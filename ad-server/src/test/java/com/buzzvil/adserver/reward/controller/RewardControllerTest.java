package com.buzzvil.adserver.reward.controller;

import com.buzzvil.adserver.reward.service.RewardServiceImpl;
import com.buzzvil.adserver.reward.service.dto.RewardDto;
import com.buzzvil.adserver.reward.service.dto.RewardHistoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardController.class)
@MockBean(JpaMetamodelMappingContext.class)
class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardServiceImpl rewardServiceImpl;

    @Test
    void reserves() throws Exception {

        final BigInteger adId = new BigInteger(String.valueOf(6L));
        final BigInteger reserveFund = new BigInteger(String.valueOf(10000));

        when(rewardServiceImpl.reserves(BigInteger.ONE, reserveFund, adId)).thenReturn(RewardDto.builder().reserveFund(reserveFund).updateAt(LocalDateTime.now()).build());

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("userId", "1");
        info.add("reserveFund", "10000");
        info.add("adId", "6");

        mockMvc.perform(post("/reward").params(info)).andExpect(status().isOk());

        verify(rewardServiceImpl, atLeastOnce()).reserves(BigInteger.ONE, reserveFund, adId);

    }

    @Test
    void subtraction() throws Exception {

        final BigInteger adId = new BigInteger(String.valueOf(6L));
        final BigInteger reserveFund = new BigInteger(String.valueOf(10000));

        when(rewardServiceImpl.reserves(BigInteger.ONE, reserveFund, adId)).thenReturn(RewardDto.builder().reserveFund(reserveFund).updateAt(LocalDateTime.now()).build());

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("userId", "1");
        info.add("reserveFund", "10000");
        info.add("adId", "6");

        mockMvc.perform(delete("/reward").params(info)).andExpect(status().isOk());

        verify(rewardServiceImpl, atLeastOnce()).subtraction(BigInteger.ONE, reserveFund, adId);
    }

    @Test
    void history() throws Exception {

        final BigInteger reserveFund = new BigInteger(String.valueOf(10000));

        final List<RewardHistoryDto> build = List.of(RewardHistoryDto.builder().reserveFund(reserveFund).createAt(LocalDateTime.now()).build());
        final LocalDateTime now = LocalDateTime.now();

        when(rewardServiceImpl.history(BigInteger.ONE, now)).thenReturn(build);

        mockMvc.perform(get("/reward/1")).andExpect(status().isOk());

        verify(rewardServiceImpl, atLeastOnce()).history(BigInteger.ONE, now);

    }

    @Test
    void balance() throws Exception {

        final BigInteger reserveFund = new BigInteger(String.valueOf(10000));

        when(rewardServiceImpl.balance(BigInteger.ONE)).thenReturn(RewardDto.builder().reserveFund(reserveFund).updateAt(LocalDateTime.now()).build());

        mockMvc.perform(get("/reward/balance/1")).andExpect(status().isOk());

        verify(rewardServiceImpl, atLeastOnce()).balance(BigInteger.ONE);
    }
}