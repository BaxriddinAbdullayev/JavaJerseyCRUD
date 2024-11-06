package com.crudcrud.jersey.crud.exceptions;

public class ParamNotFoundException extends RuntimeException {

    public ParamNotFoundException(String message) {
        super(message);
    }
}
