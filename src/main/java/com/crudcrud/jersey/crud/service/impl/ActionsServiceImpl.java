package com.crudcrud.jersey.crud.service.impl;

import com.crudcrud.jersey.crud.entity.ActionsEntity;
import com.crudcrud.jersey.crud.service.ActionsService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionsServiceImpl implements ActionsService {

    @Inject
    private SqlSessionFactory db;

    @Override
    public Integer getActionsByShortname(String shortname) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> params = new HashMap<>();
            params.put("shortname", shortname);
            return sqlSession.selectOne("getActionByShortname", params);
        }
    }

    @Override
    public ActionsEntity getActionsById(int id) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            return sqlSession.selectOne("getActions", params);
        }
    }

    @Override
    public List<ActionsEntity> getAllActions() {
        try (SqlSession sqlSession = db.openSession(true)) {
            return sqlSession.selectList("getActions");
        }
    }

    @Override
    public int createActions(ActionsEntity actions) {
        try (SqlSession sqlSession = db.openSession(true)) {
            return sqlSession.insert("insertActions", actions);
        }
    }

    @Override
    public int updateActions(ActionsEntity actions, Integer id) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> param = new HashMap<>();
            param.put("id", id);
            param.put("name", actions.getName());
            param.put("shortname", actions.getShortname());
            param.put("state", actions.getState());
            return sqlSession.update("updateActions", param);
        }
    }

    @Override
    public int deleteActions(Integer id) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> param = new HashMap<>();
            param.put("id", id);
            return sqlSession.delete("deleteActions", param);
        }
    }
}
