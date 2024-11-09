package com.example.hometestnew.models;

import io.swagger.v3.oas.annotations.media.Schema;

public class TransactionRequest {
    public String getService_code() {
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    @Schema(description = "The service code for the transaction", example = "ZAKAT")
    private String service_code;

}
