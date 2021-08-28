package com.locatel.prueba.dtos.mapper;

import com.locatel.prueba.dtos.model.SavingsAccountDto;
import com.locatel.prueba.dtos.model.user.UserDto;
import com.locatel.prueba.models.SavingsAccount;
import com.locatel.prueba.models.user.User;

import org.modelmapper.ModelMapper;

public class SavingsAccountMapper {

    private SavingsAccountMapper() {
    }

    public static SavingsAccountDto toSavingsAccountDto(SavingsAccount item) {
        return new SavingsAccountDto().setId(item.getId()).setNumber(item.getNumber())
                .setCurrentBalance(item.getCurrentBalance()).setLastBalance(item.getLastBalance())
                .setUser(new ModelMapper().map(item.getUser(), UserDto.class));
    }

    public static SavingsAccount toSavingsAccount(SavingsAccountDto item) {
        return new SavingsAccount().setId(item.getId()).setNumber(item.getNumber())
                .setCurrentBalance(item.getCurrentBalance()).setLastBalance(item.getLastBalance())
                .setUser(new ModelMapper().map(item.getUser(), User.class));
    }
}
