package com.example.hometestnew.models;


import io.swagger.v3.oas.annotations.media.Schema;

public class TopUpRequest {

    @Schema(description = "top_up_amount", example = "1000000")
    private double topUpAmount;

    public double getTopUpAmount() {
        return topUpAmount;
    }

    public void setTopUpAmount(double topUpAmount) {
        this.topUpAmount = topUpAmount;
    }
}
