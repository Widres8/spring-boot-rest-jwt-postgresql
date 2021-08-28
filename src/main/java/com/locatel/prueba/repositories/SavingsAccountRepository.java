package com.locatel.prueba.repositories;

import com.locatel.prueba.models.SavingsAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {

    SavingsAccount findByNumber(String number);

    SavingsAccount findByUserId(Long userId);

}
