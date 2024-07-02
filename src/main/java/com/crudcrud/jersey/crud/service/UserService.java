package com.crudcrud.jersey.crud.service;

import com.crudcrud.jersey.crud.entity.UserEntity;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public interface UserService {

    UserEntity getUserById(int id);

    List<UserEntity> getAllUser(SqlSessionFactory db);

    String createUser(UserEntity userEntity);

    String updateUser(UserEntity user,Integer id);

    String deleteUser(Integer id);

    UserEntity getUserByUsername(String username);

    boolean isValidUser(String username, String password);


//    public UserEntity getUserById(int id){
//        try (SqlSession sqlSession= DbUtils.getSqlSession()){
//            Map<String, Object> params = new HashMap<>();
//            params.put("id",id);
//            return sqlSession.selectOne("getUser",params);
//        }
//    }
//
//    public List<UserEntity> getAllUser(){
//        try (SqlSession sqlSession=DbUtils.getSqlSession()){
//            return sqlSession.selectList("getUser");
//        }
//    }
//
//    public String createUser(UserEntity userEntity){
//        try (SqlSession sqlSession=DbUtils.getSqlSession()){
//            sqlSession.insert("insertUserEntity",userEntity);
//        }
//        return "User muvaffaqqiyati qo'shildi!";
//    }
//
//    public String updateUser(UserEntity user,Integer id){
//        try (SqlSession sqlSession = DbUtils.getSqlSession()) {
//            Map<String, Object> param = new HashMap<>();
//            param.put("user_id", id);
//            param.put("username", user.getUsername());
//            param.put("password", user.getPassword());
//            sqlSession.update("updateUserEntity", param);
//        }catch (Throwable th){
//            th.printStackTrace();
//        }
//        return "User muaffaqqiyati o'zgartirildi!";
//    }
//
//    public String deleteUser(Integer id){
//        try (SqlSession sqlSession = DbUtils.getSqlSession()) {
//            Map<String, Object> param = new HashMap<>();
//            param.put("id", id);
//            sqlSession.delete("deleteUserEntity", param);
//        }
//        return "User Muvaffaqqiyati o'chirildi!";
//    }
//
//    public UserEntity getUserByUsername(String username){
//        try (SqlSession sqlSession= DbUtils.getSqlSession()){
//            Map<String, Object> params = new HashMap<>();
//            params.put("username",username);
//            return sqlSession.selectOne("getUserByUsername",params);
//        }
//    }
//
//    public boolean isValidUser(String username, String password) {
//        try (SqlSession sqlSession = DbUtils.getSqlSession()) {
//            Map<String, Object> param = new HashMap<>();
//            param.put("username", username);
//            UserEntity userEntity = sqlSession.selectOne("getUserByUsername", param);
//
//            if (userEntity != null &&
//                    userEntity.getUsername().equals(username) &&
//                    userEntity.getPassword().equals(password)) {
//                return true;
//            }
//            return false;
//        }
//    }
}
