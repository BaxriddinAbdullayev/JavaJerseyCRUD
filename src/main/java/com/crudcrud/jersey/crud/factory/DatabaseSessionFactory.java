package com.crudcrud.jersey.crud.factory;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.glassfish.hk2.api.Factory;

import java.io.IOException;
import java.io.Reader;

public class DatabaseSessionFactory implements Factory<SqlSessionFactory> {

    @Override
    public SqlSessionFactory provide() {
        return createSqlSessionFactory();
    }

    @Override
    public void dispose(SqlSessionFactory sqlSessionFactory) {
        // empty
    }


    private SqlSessionFactory createSqlSessionFactory() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            return new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
