package com.crudcrud.jersey.crud.exceptions.mapper;

import com.crudcrud.jersey.crud.exceptions.MyCustomException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyCustomExceptionMapper implements ExceptionMapper<MyCustomException> {
    @Override
    public Response toResponse(MyCustomException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .build();
    }
}