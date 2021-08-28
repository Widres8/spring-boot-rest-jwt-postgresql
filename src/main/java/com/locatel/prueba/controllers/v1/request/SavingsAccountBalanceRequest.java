package com.locatel.prueba.controllers.v1.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SavingsAccountBalanceRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String number;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private double value;

    private boolean addBalance;
}
