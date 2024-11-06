package com.crudcrud.jersey.crud.service.impl;

import com.crudcrud.jersey.crud.entity.RolesEntity;
import com.crudcrud.jersey.crud.service.RolesService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RolesServiceImpl implements RolesService {

    @Inject
    private SqlSessionFactory db;

    @Override
    public RolesEntity getRolesById(int id) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            return sqlSession.selectOne("getRoles", params);
        }
    }

    @Override
    public List<RolesEntity> getAllRoles() {
        try (SqlSession sqlSession = db.openSession(true)) {
            return sqlSession.selectList("getRoles");
        }
    }

    @Override
    public int createRoles(RolesEntity roles) {
        try (SqlSession sqlSession = db.openSession(true)) {
            return sqlSession.insert("insertRoles", roles);
        }
    }

    @Override
    public int updateRoles(RolesEntity roles, Integer id) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> param = new HashMap<>();
            param.put("id", id);
            param.put("name", roles.getName());
            return sqlSession.update("updateRoles", param);
        }
    }

    @Override
    public int deleteRoles(Integer id) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> param = new HashMap<>();
            param.put("id", id);
            return sqlSession.delete("deleteRoles", param);
        }
    }
}
