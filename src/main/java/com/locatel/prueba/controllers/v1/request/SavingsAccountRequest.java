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
public class SavingsAccountRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String number;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private double currentBalance;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private double lastBalance;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private Long userId;
}
