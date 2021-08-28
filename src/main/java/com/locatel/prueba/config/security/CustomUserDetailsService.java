package com.locatel.prueba.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.locatel.prueba.dtos.model.user.*;
import com.locatel.prueba.services.interfaces.IUserService;

/**
 * Created by Widres8
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto userDto = userService.findUserByEmail(email);
        if (userDto != null) {
            List<GrantedAuthority> authorities = getUserAuthority(userDto.getRoles());
            return buildUserForAuthentication(userDto, authorities);
        } else {
            throw new UsernameNotFoundException("user with email " + email + " does not exist.");
        }
    }

    private List<GrantedAuthority> getUserAuthority(Set<RoleDto> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach(role -> roles.add(new SimpleGrantedAuthority(role.getName())));
        return new ArrayList<GrantedAuthority>(roles);
    }

    private UserDetails buildUserForAuthentication(UserDto user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
