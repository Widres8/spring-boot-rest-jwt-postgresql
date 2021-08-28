package com.locatel.prueba.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.locatel.prueba.dtos.mapper.SavingsAccountMapper;
import com.locatel.prueba.dtos.model.SavingsAccountDto;
import com.locatel.prueba.exception.CustomException;
import com.locatel.prueba.exception.EntityType;
import com.locatel.prueba.exception.ExceptionType;
import com.locatel.prueba.models.SavingsAccount;
import com.locatel.prueba.repositories.SavingsAccountRepository;
import com.locatel.prueba.services.interfaces.ISavingsAccountService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SavingsAccountServiceImpl implements ISavingsAccountService {

    @Autowired
    private SavingsAccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SavingsAccountDto findByNumber(String number) {
        Optional<SavingsAccount> item = Optional.ofNullable(repository.findByNumber(number));
        if (item.isPresent()) {
            return modelMapper.map(item.get(), SavingsAccountDto.class);
        }
        throw exception(EntityType.SAVINGSACCOUNT, ExceptionType.ENTITY_NOT_FOUND, number);
    }

    @Override
    public SavingsAccountDto findByUserId(Long userId) {
        Optional<SavingsAccount> item = Optional.ofNullable(repository.findByUserId(userId));
        if (item.isPresent()) {
            return modelMapper.map(item.get(), SavingsAccountDto.class);
        }
        throw exception(EntityType.SAVINGSACCOUNT, ExceptionType.ENTITY_NOT_FOUND, userId.toString());
    }

    @Override
    public List<SavingsAccountDto> all() {
        return repository.findAll().stream().map(item -> new ModelMapper().map(item, SavingsAccountDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SavingsAccountDto find(Long id) {
        Optional<SavingsAccount> item = Optional.ofNullable(repository.findById(id)).get();
        if (item.isPresent()) {
            return modelMapper.map(item.get(), SavingsAccountDto.class);
        }
        throw exception(EntityType.SAVINGSACCOUNT, ExceptionType.ENTITY_NOT_FOUND, id.toString());
    }

    @Override
    public SavingsAccountDto create(SavingsAccountDto model) {
        try {
            SavingsAccount item = SavingsAccountMapper.toSavingsAccount(model);
            return SavingsAccountMapper.toSavingsAccountDto(repository.save(item));
        } catch (Exception e) {
            throw exception(EntityType.SAVINGSACCOUNT, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }
    }

    @Override
    public SavingsAccountDto update(Long id, SavingsAccountDto model) {
        Optional<SavingsAccount> item = Optional.ofNullable(repository.findById(id)).get();
        if (item.isPresent()) {
            SavingsAccount itemToUpdate = item.get();
            itemToUpdate.setLastBalance(itemToUpdate.getCurrentBalance()).setCurrentBalance(model.getCurrentBalance());
            return SavingsAccountMapper.toSavingsAccountDto(repository.save(itemToUpdate));
        }
        throw exception(EntityType.SAVINGSACCOUNT, ExceptionType.ENTITY_NOT_FOUND, model.getId().toString());
    }

    @Override
    public Object delete(Long id) {
        try {
            Optional<SavingsAccount> item = Optional.ofNullable(repository.findById(id)).get();
            if (item.isPresent()) {
                repository.delete(item.get());
            }
        } catch (Exception e) {
            throw exception(EntityType.SAVINGSACCOUNT, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }
        return id;
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return CustomException.throwException(entityType, exceptionType, args);
    }
}
