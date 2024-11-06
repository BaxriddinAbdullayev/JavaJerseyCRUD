package com.crudcrud.jersey.crud.exceptions;

public class MyCustomException extends RuntimeException{
    public MyCustomException(String message){
        super(message);
    }
}
