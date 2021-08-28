package com.locatel.prueba.controllers.v1.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.locatel.prueba.config.security.CustomUserDetailsService;
import com.locatel.prueba.config.security.api.JwtUtil;
import com.locatel.prueba.controllers.v1.request.AuthenticationRequest;
import com.locatel.prueba.dtos.model.user.AuthenticationDto;
import com.locatel.prueba.dtos.model.user.UserDto;
import com.locatel.prueba.dtos.response.Response;
import com.locatel.prueba.services.interfaces.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(value = "locatel-application")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/auth")
    public Response authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return Response.badRequest().setErrors(e.getMessage());
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        UserDto user = userRepository.findUserByEmail(userDetails.getUsername());
        user.setPassword(":)");

        Map<String, Object> claims = new HashMap<>();
        List<String> roles = new ArrayList<>();
        userDetails.getAuthorities().stream().forEach(authority -> roles.add(authority.getAuthority()));
        claims.put("roles", roles);

        String jwtToken = jwtUtil.generateToken(userDetails, claims);

        return Response.ok().setPayload(new AuthenticationDto().setJwt(jwtToken).setUser(user));
    }

}