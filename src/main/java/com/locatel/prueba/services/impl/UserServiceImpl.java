package com.locatel.prueba.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.locatel.prueba.dtos.mapper.UserMapper;
import com.locatel.prueba.dtos.model.user.*;
import com.locatel.prueba.exception.CustomException;
import com.locatel.prueba.exception.EntityType;
import com.locatel.prueba.exception.ExceptionType;
import com.locatel.prueba.models.user.*;
import com.locatel.prueba.repositories.user.*;
import com.locatel.prueba.services.interfaces.IUserService;

@Component
public class UserServiceImpl implements IUserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto signup(UserDto userDto) {
        Role userRole;
        User user = repository.findByEmail(userDto.getEmail());
        if (user == null) {
            if (userDto.isAdmin()) {
                userRole = roleRepository.findByName(UserRoles.ADMIN.name());
            } else {
                userRole = roleRepository.findByName(UserRoles.CLIENT.name());
            }
            user = new User().setEmail(userDto.getEmail())
                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .setRoles(new HashSet<>(Arrays.asList(userRole))).setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName()).setMobileNumber(userDto.getMobileNumber());
            return UserMapper.toUserDto(repository.save(user));
        }
        throw exception(EntityType.USER, ExceptionType.DUPLICATE_ENTITY, userDto.getEmail());
    }

    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    public UserDto findUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(repository.findByEmail(email));
        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDto.class);
        }
        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, email);
    }

    /**
     * Update User Profile
     *
     * @param userDto
     * @return
     */
    @Override
    public UserDto updateProfile(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(repository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setFirstName(userDto.getFirstName()).setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber());
            return UserMapper.toUserDto(repository.save(userModel));
        }
        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
    }

    /**
     * Change Password
     *
     * @param userDto
     * @param newPassword
     * @return
     */
    @Override
    public UserDto changePassword(UserDto userDto, String newPassword) {
        Optional<User> user = Optional.ofNullable(repository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setPassword(bCryptPasswordEncoder.encode(newPassword));
            return UserMapper.toUserDto(repository.save(userModel));
        }
        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
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

    @Override
    public List<UserDto> all() {
        return repository.findAll().stream().map(item -> new ModelMapper().map(item, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto find(Long id) {
        Optional<User> item = Optional.ofNullable(repository.findById(id)).get();
        if (item.isPresent()) {
            return modelMapper.map(item.get(), UserDto.class);
        }
        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, id.toString());
    }

    @Override
    public UserDto create(UserDto model) {
        try {
            User item = UserMapper.toUser(model);
            return UserMapper.toUserDto(repository.save(item));
        } catch (Exception e) {
            throw exception(EntityType.USER, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }
    }

    @Override
    public UserDto update(Long id, UserDto model) {
        Optional<User> item = Optional.ofNullable(repository.findById(id)).get();
        if (item.isPresent()) {
            User itemToUpdate = item.get();
            itemToUpdate.setFirstName(model.getFirstName()).setLastName(model.getLastName())
                    .setMobileNumber(model.getMobileNumber());
            return UserMapper.toUserDto(repository.save(itemToUpdate));
        }
        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, model.getId().toString());
    }

    @Override
    public Object delete(Long id) {
        try {
            Optional<User> item = Optional.ofNullable(repository.findById(id)).get();
            if (item.isPresent()) {
                repository.delete(item.get());
            }
        } catch (Exception e) {
            throw exception(EntityType.USER, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }
        return id;
    }
}
