package com.crudcrud.jersey.crud.exceptions;

import java.io.IOException;

public class FileIOException extends RuntimeException {
    public FileIOException(String message) {
        super(message);
    }
}
