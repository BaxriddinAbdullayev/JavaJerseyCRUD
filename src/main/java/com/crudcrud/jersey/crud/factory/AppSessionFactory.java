package com.crudcrud.jersey.crud.factory;

import com.crudcrud.jersey.crud.domain.model.AppSession;
import com.crudcrud.jersey.crud.entity.UserEntity;
import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.process.internal.RequestScoped;

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
            return new AppSession();
        }
        AppSession appSession = new AppSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

        appSession.setUsername(user.getUsername());
        appSession.setPassword(user.getPassword());
        return appSession;
    }

    @Override
    public void dispose(AppSession instance) {

    }
}
