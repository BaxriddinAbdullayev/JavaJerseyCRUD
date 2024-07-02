package com.crudcrud.jersey.crud.service.impl;

import com.crudcrud.jersey.crud.entity.UnicornEntity;
import com.crudcrud.jersey.crud.service.UnicornService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnicornServiceImpl implements UnicornService {

    @Inject
    private SqlSessionFactory db;

    @Override
    public UnicornEntity getUnicornById(int id) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            return sqlSession.selectOne("getUnicorn", params);
        }
    }

    @Override
    public List<UnicornEntity> getAllUnicorn() {
        try (SqlSession sqlSession = db.openSession(true)) {
            return sqlSession.selectList("getUnicorn");
        }
    }

    @Override
    public int createUnicorn(UnicornEntity unicornEntity) {
        try (SqlSession sqlSession = db.openSession(true)) {
            return sqlSession.insert("insertUnicornEntity", unicornEntity);
        }
    }

    @Override
    public int updateUnicorn(UnicornEntity unicorn, Integer id) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> param = new HashMap<>();
            param.put("id", id);
            param.put("name", unicorn.getName());
            param.put("age", unicorn.getAge());
            param.put("colour", unicorn.getColour());
            return sqlSession.update("updateUnicornEntity", param);
        }
    }

    @Override
    public int deleteUnicorn(Integer id) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> param = new HashMap<>();
            param.put("id", id);
            return sqlSession.delete("deleteUnicornEntity", param);
        }
    }
}
