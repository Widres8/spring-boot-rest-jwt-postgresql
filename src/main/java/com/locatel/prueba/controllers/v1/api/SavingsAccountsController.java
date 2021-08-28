package com.locatel.prueba.controllers.v1.api;

import javax.validation.Valid;

import com.locatel.prueba.controllers.v1.request.SavingsAccountBalanceRequest;
import com.locatel.prueba.controllers.v1.request.SavingsAccountRequest;
import com.locatel.prueba.dtos.model.SavingsAccountDto;
import com.locatel.prueba.dtos.model.user.UserDto;
import com.locatel.prueba.dtos.response.Response;
import com.locatel.prueba.services.interfaces.ISavingsAccountService;
import com.locatel.prueba.services.interfaces.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/api/v1/accounts")
@Api(value = "locatel-application")
public class SavingsAccountsController {

    @Autowired
    private ISavingsAccountService service;
    @Autowired
    private IUserService serviceUser;

    @GetMapping("account-number/{number}")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
    public Response getByNumber(@PathVariable String number) {
        try {
            return Response.ok().setPayload(service.findByNumber(number));
        } catch (Exception e) {
            return Response.badRequest().setErrors(e.getMessage());
        }
    }

    @GetMapping("user/{userId}")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
    public Response getByUserId(@PathVariable Long userId) {
        try {
            return Response.ok().setPayload(service.findByUserId(userId));
        } catch (Exception e) {
            return Response.badRequest().setErrors(e.getMessage());
        }
    }

    @PostMapping()
    @ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
    public Response create(@RequestBody @Valid SavingsAccountRequest request) {
        try {
            UserDto user = serviceUser.find(request.getUserId());
            SavingsAccountDto item = service
                    .create(new SavingsAccountDto().setCurrentBalance(request.getCurrentBalance())
                            .setLastBalance(request.getLastBalance()).setNumber(request.getNumber()).setUser(user));
            return Response.ok().setPayload(item);
        } catch (Exception e) {
            return Response.badRequest().setErrors(e.getMessage());
        }
    }

    @PutMapping()
    @ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
    public Response update(@RequestBody @Valid SavingsAccountBalanceRequest request) {
        try {
            SavingsAccountDto item = service.findByNumber(request.getNumber());

            double currentBlance = request.isAddBalance() ? (item.getCurrentBalance() + request.getValue())
                    : (item.getCurrentBalance() - request.getValue());

            item.setLastBalance(item.getCurrentBalance()).setCurrentBalance(currentBlance);
            SavingsAccountDto itemUpdate = service.update(item.getId(), item);
            return Response.ok().setPayload(itemUpdate);
        } catch (Exception e) {
            return Response.badRequest().setErrors(e.getMessage());
        }
    }

}
