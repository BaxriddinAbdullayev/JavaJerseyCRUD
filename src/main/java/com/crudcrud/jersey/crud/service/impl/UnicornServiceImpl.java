package com.crudcrud.jersey.crud.service.impl;

import com.crudcrud.jersey.crud.entity.UnicornEntity;
import com.crudcrud.jersey.crud.exceptions.FileIOException;
import com.crudcrud.jersey.crud.exceptions.NotFoundException;
import com.crudcrud.jersey.crud.service.UnicornService;
import com.crudcrud.jersey.crud.utils.Localization;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.inject.Inject;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnicornServiceImpl implements UnicornService {

    private static final String PATH_EXCEL_FILE="/Users/bahriddin/Desktop/windows works/upload/print-files/prinst-excel/unicorn.xlsx";

    @Inject
    private SqlSessionFactory db;

    @Inject
    private Localization localization;

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
    public List<UnicornEntity> getAllUnicorn(RowBounds rowBounds) {
        try (SqlSession sqlSession = db.openSession(true)) {
            return sqlSession.selectList("getUnicorn", null, rowBounds);
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

    @Override
    public int printUnicornToExcel() {
        List<UnicornEntity> unicornEntityList = getAllUnicorn();

        if(unicornEntityList.isEmpty()){
            throw new NotFoundException(localization.getMessage("exception.unicorns-not-found"));
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Unicorns");
        Row headerRow = sheet.createRow(0);

        List<String> columnNames = new ArrayList<>();
        columnNames.add("id");
        columnNames.add("name");
        columnNames.add("age");
        columnNames.add("colour");

        for (int i = 0; i < columnNames.size(); i++) {
            headerRow.createCell(i).setCellValue(columnNames.get(i));
        }

        int rowNumber = 1;
        for (UnicornEntity unicornEntity : unicornEntityList) {
            Row row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(unicornEntity.getId());
            row.createCell(1).setCellValue(unicornEntity.getName());
            row.createCell(2).setCellValue(unicornEntity.getAge());
            row.createCell(3).setCellValue(unicornEntity.getColour());
        }

        try (OutputStream outputStream=new FileOutputStream(PATH_EXCEL_FILE)){
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            throw new FileIOException(localization.getMessage("exeption.file-not-found"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }

    @Override
    public int writingUnicornsToDatabase() {

        try (InputStream inputStream=new FileInputStream(PATH_EXCEL_FILE)){
            Workbook workbook=new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            UnicornEntity unicornEntity=new UnicornEntity();

            for (Row row : sheet) {
                if(row.getRowNum()==0){
                    continue;
                }

                unicornEntity.setId((int) row.getCell(0).getNumericCellValue());
                unicornEntity.setName(row.getCell(1).getStringCellValue());
                unicornEntity.setAge((int) row.getCell(2).getNumericCellValue());
                unicornEntity.setColour(row.getCell(3).getStringCellValue());

                try (SqlSession sqlSession = db.openSession(true)) {
                    sqlSession.insert("insertUnicornEntityInUnicornsTestDatabase",
                            unicornEntity);
                }
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return 1;

    }
}
