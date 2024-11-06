package com.crudcrud.jersey.crud.utils;

import com.crudcrud.jersey.crud.domain.model.AppSession;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthUtils {

    private static final String AUTHENTICATION_SCHEME = "Basic";

    public static boolean checkAuthentication(AppSession appSession, ContainerRequestContext context) {
        String authzHeader = context.getHeaderString(HttpHeaders.AUTHORIZATION);
        String base64Credentials = authzHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
        final String[] values = credentials.split(":", 2);

        String username = values[0];
        String password = values[1];

        if (appSession.getUsername().equals(username) &&
                appSession.getPassword().equals(CryptoUtils.hashPassword(password))) {
            return true;
        }
        return false;
    }
}
