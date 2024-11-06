package com.crudcrud.jersey.crud.domain.model;

import org.glassfish.jersey.process.internal.RequestScoped;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

public class AppSession {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
