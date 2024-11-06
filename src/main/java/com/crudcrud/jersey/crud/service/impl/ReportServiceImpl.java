package com.crudcrud.jersey.crud.service.impl;

import com.crudcrud.jersey.crud.entity.RegionsEntity;
import com.crudcrud.jersey.crud.service.ReportService;
import com.crudcrud.jersey.crud.utils.ExcelUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportServiceImpl implements ReportService {

    private static final String PATH_PRINT_EXCEL_FILE = "/Users/bahriddin/Desktop/windows works/upload/tasks/task10/example.xlsx";

    @Inject
    private SqlSessionFactory db;

    @Override
    public void getReport() {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Task10");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Viloyat kesimida hisobot");

        CellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(workbook);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        defaultCellStyle.setFont(font);

        applyCellStyleToMergedRegion(sheet, new CellRangeAddress(0, 1, 0, 3), defaultCellStyle);

        defaultCellStyle = ExcelUtils.getDefaultCellStyle(workbook);
        font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        defaultCellStyle.setFont(font);

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("N");

        cell = row.createCell(1);
        cell.setCellValue("Viloyat");

        cell = row.createCell(2);
        cell.setCellValue("Tuman");

        row = sheet.createRow(3);

        cell = row.createCell(2);
        cell.setCellValue("soni");
        cell.setCellStyle(defaultCellStyle);

        cell = row.createCell(3);
        cell.setCellValue("summa");
        cell.setCellStyle(defaultCellStyle);

        applyCellStyleToMergedRegion(sheet, new CellRangeAddress(2, 3, 0, 0), defaultCellStyle);
        applyCellStyleToMergedRegion(sheet, new CellRangeAddress(2, 3, 1, 1), defaultCellStyle);
        applyCellStyleToMergedRegion(sheet, new CellRangeAddress(2, 2, 2, 3), defaultCellStyle);

        List<RegionsEntity> getRegions = new ArrayList<>();
        try (SqlSession sqlSession = db.openSession(true)) {
            getRegions = sqlSession.selectList("getRegions");
        }

        List<Integer> numberOfAreas = new ArrayList<>();
        try (SqlSession sqlSession = db.openSession(true)) {
            numberOfAreas = sqlSession.selectList("getNumberOfAreasInRegion");
        }

        List<Float> regionsBudget = new ArrayList<>();
        try (SqlSession sqlSession = db.openSession(true)) {
            regionsBudget = sqlSession.selectList("getRegionsBudget");
        }

        CellStyle horizontalRightCellStyle = ExcelUtils.getDefaultCellStyle(workbook);
        horizontalRightCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        horizontalRightCellStyle.setFont(font);

        CellStyle horizontalLeftCellStyle = ExcelUtils.getDefaultCellStyle(workbook);
        horizontalLeftCellStyle.setAlignment(HorizontalAlignment.LEFT);
        horizontalLeftCellStyle.setFont(font);

        int rowIndex = 4;
        for (RegionsEntity getRegion : getRegions) {
            row = sheet.createRow(rowIndex);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(rowIndex - 3);
            cell0.setCellStyle(horizontalRightCellStyle);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(getRegion.getName());
            cell1.setCellStyle(horizontalLeftCellStyle);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(numberOfAreas.get(rowIndex-4));
            cell2.setCellStyle(horizontalRightCellStyle);

            Cell cell3 = row.createCell(3);
            try {
                cell3.setCellValue(regionsBudget.get(rowIndex-4));
            }catch (NullPointerException e){
                cell3.setCellValue(0.f);
            }
            cell3.setCellStyle(horizontalRightCellStyle);
            rowIndex++;
        }

        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0,
                sheet.getRow(2).getLastCellNum()-1));

        sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 3));
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(3, 5000);


        try (FileOutputStream fileOutputStream = new FileOutputStream(PATH_PRINT_EXCEL_FILE)) {
            workbook.write(fileOutputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void applyCellStyleToMergedRegion(Sheet sheet, CellRangeAddress region, CellStyle cellStyle) {
        for (int rowNum = region.getFirstRow(); rowNum <= region.getLastRow(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
            for (int colNum = region.getFirstColumn(); colNum <= region.getLastColumn(); colNum++) {
                Cell cell = row.getCell(colNum);
                if (cell == null) {
                    cell = row.createCell(colNum);
                }
                cell.setCellStyle(cellStyle);
            }
        }
    }
}
