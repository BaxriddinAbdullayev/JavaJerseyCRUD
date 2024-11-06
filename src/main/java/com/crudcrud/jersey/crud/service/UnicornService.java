package com.crudcrud.jersey.crud.service;

import com.crudcrud.jersey.crud.entity.UnicornEntity;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface UnicornService {

    UnicornEntity getUnicornById(int id);

    List<UnicornEntity> getAllUnicorn();

    List<UnicornEntity> getAllUnicorn(RowBounds rowBounds);

    int createUnicorn(UnicornEntity unicornEntity);

    int updateUnicorn(UnicornEntity unicorn,Integer id);

    int deleteUnicorn(Integer id);

    int printUnicornToExcel();

    int writingUnicornsToDatabase();
}
