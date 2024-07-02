package com.crudcrud.jersey.crud.filter;

import com.crudcrud.jersey.crud.annotations.Public;
import com.crudcrud.jersey.crud.annotations.PublicAll;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTHENTICATION_SCHEME = "Basic";

    @Context
    private ResourceInfo resourceInfo;

    @Context
    private HttpServletRequest servletRequest;

    @Override
    public void filter(ContainerRequestContext context) throws IOException {

        PublicAll publicAll = resourceInfo.getResourceClass().getAnnotation(PublicAll.class);
        if(publicAll != null) {
            return;
        }

        Public annotation = resourceInfo.getResourceMethod().getAnnotation(Public.class);
        if(annotation != null) {
            return;
        }

        HttpSession session = servletRequest.getSession(false);
        if (session == null) {
            abortWithUnauthorized(context, "Access Denied!");
            return;
        }
        Object sessionUsername = session.getAttribute("username");
        Object sessionPassword = session.getAttribute("password");
        if (sessionUsername == null || sessionPassword == null) {
            abortWithUnauthorized(context, "Access Denied!");
            return;
        }

        String authzHeader = context.getHeaderString(HttpHeaders.AUTHORIZATION);
        String base64Credentials = authzHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
        final String[] values = credentials.split(":", 2);

        String username = values[0];
        String password = values[1];

        if (sessionUsername.equals(username) && sessionPassword.equals(password)) {
            return;
        }
        abortWithUnauthorized(context, "Access Denied!");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext, String message) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME + " realm=\"example\"")
                        .entity(message)
                        .build());
    }
}
