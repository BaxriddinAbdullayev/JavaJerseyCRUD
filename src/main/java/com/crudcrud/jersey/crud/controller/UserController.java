package com.crudcrud.jersey.crud.controller;

import com.crudcrud.jersey.crud.entity.UserEntity;
import com.crudcrud.jersey.crud.service.UserService;
import com.crudcrud.jersey.crud.service.impl.UserServiceImpl;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/crud")
public class UserController {

    @Inject
    private SqlSessionFactory db;

    @Inject
    private UserService userService;

    @GET
    @Path("/resource")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserEntity> getAll() {
        return userService.getAllUser(db);
    }

    @GET
    @Path("/resource/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserEntity getById(@PathParam("id") Integer id) {
        return userService.getUserById(id);
    }

    @POST
    @Path("/resource")
    @Produces(MediaType.APPLICATION_JSON)
    public String create(UserEntity userEntity) {
        return userService.createUser(userEntity);
    }

    @PUT
    @Path("/resource/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String update(@PathParam("id") Integer id, UserEntity user) {
        return userService.updateUser(user, id);
    }

    @DELETE
    @Path("/resource/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String delete(@PathParam("id") int id) {
        return userService.deleteUser(id);
    }
}
