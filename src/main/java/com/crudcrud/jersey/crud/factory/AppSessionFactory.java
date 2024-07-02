package com.crudcrud.jersey.crud.factory;

import com.crudcrud.jersey.crud.domain.model.AppSession;
import org.glassfish.hk2.api.Factory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

public class AppSessionFactory implements Factory<AppSession> {

    @Context
    private HttpServletRequest request;

    @Override
    public AppSession provide() {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        AppSession appSession = new AppSession();
        appSession.setUsername(session.getAttribute("username").toString());
        appSession.setPassword(session.getAttribute("password").toString());
        return appSession;
    }

    @Override
    public void dispose(AppSession instance) {

    }
}
