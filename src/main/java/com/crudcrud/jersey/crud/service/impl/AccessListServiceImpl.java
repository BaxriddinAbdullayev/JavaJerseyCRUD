package com.crudcrud.jersey.crud.service.impl;

import com.crudcrud.jersey.crud.entity.AccessListEntity;
import com.crudcrud.jersey.crud.service.AccessListService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class AccessListServiceImpl implements AccessListService {

    @Inject
    private SqlSessionFactory db;

    @Override
    public AccessListEntity getAccessList(Integer roles_id, Integer actions_id) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> params = new HashMap<>();
            params.put("roles_id", roles_id);
            params.put("actions_id", actions_id);
            return sqlSession.selectOne("getAccessList", params);
        }
    }
}
