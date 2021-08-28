package com.locatel.prueba.dtos.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

import com.locatel.prueba.dtos.model.user.RoleDto;
import com.locatel.prueba.dtos.model.user.UserDto;
import com.locatel.prueba.models.user.Role;
import com.locatel.prueba.models.user.User;

@Component
public class UserMapper {

    private UserMapper() {
    }

    public static UserDto toUserDto(User user) {
        return new UserDto().setEmail(user.getEmail()).setFirstName(user.getFirstName()).setLastName(user.getLastName())
                .setMobileNumber(user.getMobileNumber()).setRoles(new HashSet<RoleDto>(user.getRoles().stream()
                        .map(role -> new ModelMapper().map(role, RoleDto.class)).collect(Collectors.toSet())));
    }

    public static User toUser(UserDto user) {
        return new User().setEmail(user.getEmail()).setFirstName(user.getFirstName()).setLastName(user.getLastName())
                .setMobileNumber(user.getMobileNumber()).setRoles(new HashSet<Role>(user.getRoles().stream()
                        .map(role -> new ModelMapper().map(role, Role.class)).collect(Collectors.toSet())));
    }

}
