package com.xueyuan.edu.Utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
public class PoiReadAndWriteUtils {

    public void write() throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("text");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("测试");
        OutputStream outputStream = new FileOutputStream("E:/text.xlsx");
        workbook.write(outputStream);
        outputStream.close();
    }


    public Sheet read(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        return sheet;
    }
}
           
