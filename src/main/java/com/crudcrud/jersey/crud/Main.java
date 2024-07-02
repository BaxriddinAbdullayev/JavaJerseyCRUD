package com.crudcrud.jersey.crud;

import com.crudcrud.jersey.crud.domain.model.AppSession;
import com.crudcrud.jersey.crud.factory.AppSessionFactory;
import com.crudcrud.jersey.crud.factory.DatabaseSessionFactory;
import com.crudcrud.jersey.crud.service.UnicornService;
import com.crudcrud.jersey.crud.service.UserService;
import com.crudcrud.jersey.crud.service.impl.UnicornServiceImpl;
import com.crudcrud.jersey.crud.service.impl.UserServiceImpl;
import com.crudcrud.jersey.crud.utils.CryptoUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class Main extends ResourceConfig {

    public Main() {

        register(new AbstractBinder() {
            @Override
            protected void configure() {

                bindFactory(AppSessionFactory.class)
                        .to(AppSession.class)
                        .in(RequestScoped.class);

                bindFactory(DatabaseSessionFactory.class)
                        .to(SqlSessionFactory.class)
                        .in(Singleton.class);

                bind(UserServiceImpl.class)
                        .to(UserService.class)
                        .in(Singleton.class);

                bind(UnicornServiceImpl.class)
                        .to(UnicornService.class)
                        .in(Singleton.class);

                bindAsContract(CryptoUtils.class)
                        .in(Singleton.class);

            }
        });

        packages("com.crudcrud.jersey.crud");

        // Jackson JSON texnologiyasini qo'shish
        register(JacksonFeature.class);
    }
}
