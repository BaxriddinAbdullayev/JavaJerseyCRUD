package com.crudcrud.jersey.crud.controller;

import com.crudcrud.jersey.crud.annotations.PublicAll;
import com.crudcrud.jersey.crud.domain.model.Result;
import com.crudcrud.jersey.crud.domain.transport.LoginRequest;
import com.crudcrud.jersey.crud.entity.UserEntity;
import com.crudcrud.jersey.crud.exceptions.NotFoundException;
import com.crudcrud.jersey.crud.service.UserService;
import com.crudcrud.jersey.crud.service.impl.UserServiceImpl;
import com.crudcrud.jersey.crud.utils.CryptoUtils;
import com.crudcrud.jersey.crud.utils.Localization;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/public")
@PublicAll
public class PublicController {

    @Inject
    private UserService userService;

    @Context
    private HttpServletRequest servletRequest;

    @Context Response response;

    @Context
    private HttpHeaders headers;

    @Inject
    private Localization localization;

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Result<String> registerUser(UserEntity userEntity) {
        UserEntity currentUser = userService.getUserByUsername(userEntity.getUsername());

        if (currentUser != null) {
            return new Result<>(0,
                    "You are already registered!",
                    null);
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(userEntity.getUsername());
        newUser.setPassword(CryptoUtils.hashPassword(userEntity.getPassword()));
        newUser.setRoles_id(userEntity.getRoles_id());

        userService.createUser(newUser);
        return new Result<>(0,
                "You have successfully registered!",
                null);
    }

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Result<String> login(LoginRequest request) {

        if (userService.isValidUser(request.getUsername(),
                CryptoUtils.hashPassword(request.getPassword()))) {

            HttpSession session = servletRequest.getSession(true);
            UserEntity userByUsername = userService.getUserByUsername(request.getUsername());

            session.setAttribute("user", userByUsername);
            return new Result<>(0,
                    "Login was successful!",
                    null);
        }

        throw new NotFoundException(localization.getMessage("exception.user-not-found"));
    }
}
