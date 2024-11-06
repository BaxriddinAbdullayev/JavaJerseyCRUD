package com.crudcrud.jersey.crud.service;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public interface ReportService {

    void getReport();

    void applyCellStyleToMergedRegion(Sheet sheet, CellRangeAddress region, CellStyle cellStyle);
}
