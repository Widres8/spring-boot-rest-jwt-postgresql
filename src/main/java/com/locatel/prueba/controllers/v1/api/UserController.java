package com.locatel.prueba.controllers.v1.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import com.locatel.prueba.controllers.v1.request.UserSignupRequest;
import com.locatel.prueba.dtos.model.user.UserDto;
import com.locatel.prueba.dtos.response.Response;
import com.locatel.prueba.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/user")
@Api(value = "locatel-application")
public class UserController {
    @Autowired
    private UserServiceImpl service;

    /**
     * Handles the incoming POST API "/v1/user/signup"
     *
     * @param userSignupRequest
     * @return
     */
    @PostMapping("/signup")
    public Response signup(@RequestBody @Valid UserSignupRequest userSignupRequest) {
        return Response.ok().setPayload(registerUser(userSignupRequest, false));
    }

    /**
     * Register a new user in the database
     *
     * @param userSignupRequest
     * @return
     */
    private UserDto registerUser(UserSignupRequest userSignupRequest, boolean isAdmin) {
        UserDto userDto = new UserDto().setEmail(userSignupRequest.getEmail())
                .setPassword(userSignupRequest.getPassword()).setFirstName(userSignupRequest.getFirstName())
                .setLastName(userSignupRequest.getLastName()).setMobileNumber(userSignupRequest.getMobileNumber())
                .setAdmin(isAdmin);

        return service.signup(userDto);
    }
}
