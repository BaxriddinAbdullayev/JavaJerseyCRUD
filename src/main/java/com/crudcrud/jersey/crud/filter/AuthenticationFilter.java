package com.crudcrud.jersey.crud.filter;

import com.crudcrud.jersey.crud.annotations.Public;
import com.crudcrud.jersey.crud.annotations.PublicAll;
import com.crudcrud.jersey.crud.annotations.SystemAction;
import com.crudcrud.jersey.crud.domain.model.AppSession;
import com.crudcrud.jersey.crud.domain.model.Result;
import com.crudcrud.jersey.crud.entity.AccessListEntity;
import com.crudcrud.jersey.crud.service.AccessListService;
import com.crudcrud.jersey.crud.service.ActionsService;
import com.crudcrud.jersey.crud.service.UserService;
import com.crudcrud.jersey.crud.utils.AuthUtils;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTHENTICATION_SCHEME = "Basic";

    @Context
    private ResourceInfo resourceInfo;

    @Context
    private HttpServletRequest servletRequest;

    @Inject
    private ActionsService actionsService;

    @Inject
    private AccessListService accessListService;

    @Inject
    private UserService userService;

    @Inject
    private AppSession appSession;

    @Override
    public void filter(ContainerRequestContext context) throws IOException {

        PublicAll publicAll = resourceInfo.getResourceClass().getAnnotation(PublicAll.class);
        if (publicAll != null) {
            return;
        }

        Public annotation = resourceInfo.getResourceMethod().getAnnotation(Public.class);
        if (annotation != null) {
            return;
        }

        SystemAction systemAction = resourceInfo.getResourceMethod().getAnnotation(SystemAction.class);
        if (systemAction == null) {
            abortWithUnauthorized(context, "Access Denied!");
            return;
        }

        if (appSession.getUsername() == null && appSession.getPassword() == null) {
            abortWithUnauthorized(context, "Access Denied!");
            return;
        }

        Integer actions_id = actionsService.getActionsByShortname(systemAction.value());
        Integer role_id = userService.getRoleIdByUsername(appSession.getUsername());

        AccessListEntity accessList = accessListService.getAccessList(role_id, actions_id);
        if (accessList == null) {
            abortWithUnauthorized(context, "This role is not authorized.");
            return;
        }

        if (AuthUtils.checkAuthentication(appSession, context)) {
            return;
        }
        abortWithUnauthorized(context, "Access Denied!");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext, String message) {
        requestContext.abortWith(
                Response.ok(new Result<>(1,
                        message,
                        null))
                        .header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME + " realm=\"example\"")
                        .build());
    }
}
