package com.crudcrud.jersey.crud.service;

import com.crudcrud.jersey.crud.entity.RolesEntity;
import com.crudcrud.jersey.crud.entity.UnicornEntity;
import com.crudcrud.jersey.crud.entity.UserEntity;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public interface RolesService {

    RolesEntity getRolesById(int id);

    List<RolesEntity> getAllRoles();

    int createRoles(RolesEntity roles);

    int updateRoles(RolesEntity roles,Integer id);

    int deleteRoles(Integer id);
}
