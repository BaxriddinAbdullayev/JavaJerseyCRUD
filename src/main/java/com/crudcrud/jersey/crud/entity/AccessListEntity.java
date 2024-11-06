package com.crudcrud.jersey.crud.entity;

public class AccessListEntity {

    private Integer id;
    private Integer roles_id;
    private Integer actions_id;
    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoles_id() {
        return roles_id;
    }

    public void setRoles_id(Integer roles_id) {
        this.roles_id = roles_id;
    }

    public Integer getActions_id() {
        return actions_id;
    }

    public void setActions_id(Integer actions_id) {
        this.actions_id = actions_id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
