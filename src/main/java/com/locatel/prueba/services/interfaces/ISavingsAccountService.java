package com.locatel.prueba.services.interfaces;

import com.locatel.prueba.dtos.model.SavingsAccountDto;

public interface ISavingsAccountService extends IService<SavingsAccountDto, Long> {

    /**
     * Search an existing entity
     *
     * @param number
     * @return
     */
    SavingsAccountDto findByNumber(String number);

    /**
     * Search an existing entity
     *
     * @param userId
     * @return
     */
    SavingsAccountDto findByUserId(Long userId);
}
