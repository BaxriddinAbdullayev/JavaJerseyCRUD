package com.crudcrud.jersey.crud.service;

import com.crudcrud.jersey.crud.entity.ActionsEntity;
import com.crudcrud.jersey.crud.entity.RolesEntity;

import java.util.List;

public interface ActionsService {

    Integer getActionsByShortname(String shortname);

    ActionsEntity getActionsById(int id);

    List<ActionsEntity> getAllActions();

    int createActions(ActionsEntity actions);

    int updateActions(ActionsEntity actions,Integer id);

    int deleteActions(Integer id);
}
