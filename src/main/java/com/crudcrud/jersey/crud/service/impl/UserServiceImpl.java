package com.crudcrud.jersey.crud.service.impl;

import com.crudcrud.jersey.crud.entity.UserEntity;
import com.crudcrud.jersey.crud.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    @Inject
    private SqlSessionFactory db;

    public UserEntity getUserById(int id) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            return sqlSession.selectOne("getUser", params);
        }
    }

    public List<UserEntity> getAllUser(SqlSessionFactory db) {
        try (SqlSession sqlSession = db.openSession(true)) {
            return sqlSession.selectList("getUser");
        }
    }

    public String createUser(UserEntity userEntity) {
        try (SqlSession sqlSession = db.openSession(true)) {
            sqlSession.insert("insertUserEntity", userEntity);
        }
        return "User muvaffaqqiyati qo'shildi!";
    }

    public String updateUser(UserEntity user, Integer id) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> param = new HashMap<>();
            param.put("user_id", id);
            param.put("username", user.getUsername());
            param.put("password", user.getPassword());
            sqlSession.update("updateUserEntity", param);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return "User muaffaqqiyati o'zgartirildi!";
    }

    public String deleteUser(Integer id) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> param = new HashMap<>();
            param.put("id", id);
            sqlSession.delete("deleteUserEntity", param);
        }
        return "User Muvaffaqqiyati o'chirildi!";
    }

    public UserEntity getUserByUsername(String username) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> params = new HashMap<>();
            params.put("username", username);
            return sqlSession.selectOne("getUserByUsername", params);
        }
    }

    @Override
    public Integer getRoleIdByUsername(String username) {
        try (SqlSession sqlSession = db.openSession(true)) {
            Map<String, Object> params = new HashMap<>();
            params.put("username", username);
            return sqlSession.selectOne("getRoleIdByUsername", params);
        }
    }

    public boolean isValidUser(String username, String password) {
        UserEntity userEntity = getUserByUsername(username);
        if (userEntity != null &&
                userEntity.getUsername().equals(username) &&
                userEntity.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
