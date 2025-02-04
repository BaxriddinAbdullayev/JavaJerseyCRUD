package com.crudcrud.jersey.crud.exceptions.mapper;

import com.crudcrud.jersey.crud.domain.model.Result;
import com.crudcrud.jersey.crud.exceptions.NotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new Result<>(1,
                        exception.getMessage(),
                        null))
                .build();
    }
}
