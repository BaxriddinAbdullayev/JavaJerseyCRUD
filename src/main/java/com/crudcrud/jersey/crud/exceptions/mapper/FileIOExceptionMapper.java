package com.crudcrud.jersey.crud.exceptions.mapper;

import com.crudcrud.jersey.crud.domain.model.Result;
import com.crudcrud.jersey.crud.exceptions.FileIOException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class FileIOExceptionMapper implements ExceptionMapper<FileIOException> {
    @Override
    public Response toResponse(FileIOException exception) {
        return Response.ok(new Result<>(1,
                        exception.getMessage(),
                        null))
                .build();
    }
}
