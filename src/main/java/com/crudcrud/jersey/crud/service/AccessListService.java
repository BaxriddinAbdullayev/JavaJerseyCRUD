package com.crudcrud.jersey.crud.service;

import com.crudcrud.jersey.crud.entity.AccessListEntity;

public interface AccessListService {

    AccessListEntity getAccessList(Integer roles_id,Integer actions_id);
}
