package com.crudcrud.jersey.crud.controller;

import com.crudcrud.jersey.crud.annotations.PublicAll;
import com.crudcrud.jersey.crud.domain.model.AppSession;
import com.crudcrud.jersey.crud.domain.transport.LoginRequest;
import com.crudcrud.jersey.crud.entity.UserEntity;
import com.crudcrud.jersey.crud.service.UserService;
import com.crudcrud.jersey.crud.utils.CryptoUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/public")
@PublicAll
public class PublicController {

    @Inject
    private UserService userService;

    @Context
    private HttpServletRequest servletRequest;

    @Inject
    private AppSession appSession;

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserEntity userEntity) {
        UserEntity currentUser = userService.getUserByUsername(userEntity.getUsername());

        if (currentUser != null) {
            return Response.status(Response.Status.FOUND)
                    .entity("You are already registered!")
                    .build();
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(userEntity.getUsername());
        newUser.setPassword(CryptoUtils.hashPassword(userEntity.getPassword()));

        userService.createUser(newUser);
        return Response.ok("You have successfully registered!").build();
    }

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(LoginRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        if (userService.isValidUser(username, CryptoUtils.hashPassword(password))) {
            HttpSession session = servletRequest.getSession(true);
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            return Response.ok("Login was successful!").build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("User Not Found!")
                .build();
    }
}
