package com.example.hometestnew.models;

import io.swagger.v3.oas.annotations.media.Schema;

public class TopUpRequest {

    @Schema(description = "The amount to top up", example = "1000000")
    private double top_up_amount;

    public double getTop_up_amount() {
        return top_up_amount;
    }

    public void setTop_up_amount(double top_up_amount) {
        this.top_up_amount = top_up_amount;
    }
}
