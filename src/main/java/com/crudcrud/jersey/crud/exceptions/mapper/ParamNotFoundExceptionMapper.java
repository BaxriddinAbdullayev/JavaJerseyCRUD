package com.crudcrud.jersey.crud.exceptions.mapper;

import com.crudcrud.jersey.crud.domain.model.Result;
import com.crudcrud.jersey.crud.exceptions.NotFoundException;
import com.crudcrud.jersey.crud.exceptions.ParamNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ParamNotFoundExceptionMapper implements ExceptionMapper<ParamNotFoundException> {
    @Override
    public Response toResponse(ParamNotFoundException exception) {
        String param = exception.getMessage();
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new Result<>(1,
                        "Param not found: " + param,
                        null))
                .build();
    }
}
