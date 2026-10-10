package com.sdet.framework.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtility {
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    public static String getData(String sheetName , String colName , int rowNum){
        String cellVlue ="";
        try{
            FileInputStream fis = new FileInputStream("src/test/resources/testdata/DataSheet.xlsx");
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            XSSFRow headerRow = sheet.getRow(0);
            int col = -1;
            for(int i=0 ; i<headerRow.getLastCellNum();i++){
                if(headerRow.getCell(i).getStringCellValue().equalsIgnoreCase(colName)){
                    col=i;
                    break;
                }
            }
            if(col!=-1){
                XSSFRow rowdata = sheet.getRow(rowNum);
                XSSFCell cell = rowdata.getCell(col);

                DataFormatter formatter = new DataFormatter();
                cellVlue = formatter.formatCellValue(cell);
            }else {
                System.out.println("Column Name '" + colName + "' not found!");
            }
            workbook.close();
            fis.close();

        }catch (Exception e) {
            System.out.println("Excel Error: " + e.getMessage());
        }
        return cellVlue;
    }
}
