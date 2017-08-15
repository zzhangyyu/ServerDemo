package com.yoler.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhangyu on 2017/6/19.
 */
public class ImportExcelUtil {
    /**
     * Excel 2003
     */
    private final static String XLS = "xls";
    /**
     * Excel 2007
     */
    private final static String XLSX = "xlsx";

    public static List<List<String>> importExcel(InputStream is, String excelType) {
        List<List<String>> result = new ArrayList<>();
        try {
            Workbook wb = null;
            if (excelType.toLowerCase().equals(XLS)) {
                wb = new HSSFWorkbook(is);
            } else if (excelType.toLowerCase().equals(XLSX)) {
                wb = new XSSFWorkbook(OPCPackage.open(is));
            }
            Iterator<Sheet> it = wb.iterator();//sheet迭代器
            while (it.hasNext()) {
                Sheet sheet = it.next();
                if (sheet != null) {
                    int startImportRowNum = sheet.getFirstRowNum();//从该行开始读取数据，从0开始
                    int endImportRowNum = sheet.getLastRowNum();//从该行结束读取数据，到length-1
                    for (int rowNum = startImportRowNum; rowNum <= endImportRowNum; rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }
                        short minColumnNum = row.getFirstCellNum();//从该列开始读取数据，从0开始
                        short maxColumnNum = row.getLastCellNum();//从该列结束读取数据，到length
                        List<String> rowContent = new ArrayList<>();
                        for (short i = minColumnNum; i < maxColumnNum; i++) {
                            rowContent.add(formatCell(row.getCell(i)));
                        }
                        result.add(rowContent);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 处理单元格格式
     *
     * @param cell
     * @return
     */
    public static String formatCell(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                //日期格式的处理
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                }
                return String.valueOf(cell.getNumericCellValue());
            //字符串
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            // 公式
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            // 空白
            case Cell.CELL_TYPE_BLANK:
                return "";
            // 布尔取值
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";
            //错误类型
            case Cell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue() + "";
        }
        return "";
    }


}
