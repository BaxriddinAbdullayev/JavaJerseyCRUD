package com.crudcrud.jersey.crud.service;

import com.crudcrud.jersey.crud.entity.UnicornEntity;
import com.crudcrud.jersey.crud.entity.UserEntity;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public interface UnicornService {

    UnicornEntity getUnicornById(int id);

    List<UnicornEntity> getAllUnicorn();

    int createUnicorn(UnicornEntity unicornEntity);

    int updateUnicorn(UnicornEntity unicorn,Integer id);

    int deleteUnicorn(Integer id);
}
