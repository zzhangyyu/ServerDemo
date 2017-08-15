package com.yoler.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * poi导出excel功能
 * Created by zhangyu on 2017/6/10.
 */
public class ExportExcelUtil<T> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void exportExcel(Collection<T> dataset, OutputStream out) throws Exception {
        exportExcel("导出EXCEL文档", null, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String[] headers, Collection<T> dataset, OutputStream out) throws Exception {
        exportExcel("错误票上报信息", headers, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String[] headers, Collection<T> dataset, OutputStream out, String pattern) throws Exception {
        exportExcel("导出EXCEL文档", headers, dataset, out, pattern);
    }

    /**
     * 通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符合一定条件的数据以EXCEL的形式输出到指定IO设备上
     *
     * @param title   表格标题名
     * @param headers 表格属性列名数组
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    public void exportExcel(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern) throws Exception {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle headerStyle = createHeaderStyle(workbook);
        // 生成一个字体
        createFont(workbook, headerStyle);
        // 生成并设置另一个样式
        HSSFCellStyle dataStyle = createDataStyle(workbook);
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容,可以在POI中添加注释！
        comment.setString(new HSSFRichTextString(""));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("");
        // 产生表格标题行
        createHead(headers, sheet, headerStyle);
        // 遍历集合数据，产生数据行
        createRow(dataset, pattern, workbook, sheet, dataStyle, patriarch);
        closeWorkbook(out, workbook);
    }

    private void createFont(HSSFWorkbook workbook, HSSFCellStyle style) {
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
    }

    private void createHead(String[] headers, HSSFSheet sheet, HSSFCellStyle style) {
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell((int) i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
    }

    private void closeWorkbook(OutputStream out, HSSFWorkbook workbook) throws Exception {
        try {
            workbook.write(out);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new Exception(e);
        }
    }

    private HSSFCellStyle createHeaderStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        return style;
    }

    private HSSFCellStyle createDataStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style2.setFont(font);
        style2.setWrapText(true);
        return style2;
    }

    private void createRow(Collection<T> dataset, String pattern, HSSFWorkbook workbook, HSSFSheet sheet,
                           HSSFCellStyle style2, HSSFPatriarch patriarch) throws Exception {
        HSSFRow row;
        Iterator<T> iterator = dataset.iterator();
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLUE.index);
        int index = 0;
        while (iterator.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) iterator.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            int j = 0;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Class<? extends Object> tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    List<String> valueList = new ArrayList<String>();
                    if (value == null) {
                        value = "";
                    }
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    boolean isList = false;
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof byte[]) {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6,
                                index);
                        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_DONT_RESIZE);
                        patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else if (value instanceof List) {
                        isList = true;
                        valueList = (List<String>) value;
                        for (String val : valueList) {
                            textValue = val.toString();
                            // 设置表格宽度自适应；
                            if ((sheet.getColumnWidth(j) < textValue.getBytes().length * 2 * 256))
                                if (textValue.getBytes().length * 2 * 256 < 255 * 256)
                                    sheet.setColumnWidth(j, (short) textValue.getBytes().length * 2 * 256);
                                else
                                    sheet.setColumnWidth(j, (short) 255 * 256);
                        }
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                        // 设置表格宽度自适应；
                        if ((sheet.getColumnWidth(i) < textValue.getBytes().length * 2 * 256))
                            if (textValue.getBytes().length * 2 * 256 < 255 * 256)
                                sheet.setColumnWidth(i, (short) textValue.getBytes().length * 2 * 256);
                            else
                                sheet.setColumnWidth(i, (short) 255 * 256);
                    }
                    if (!isList) {
                        HSSFCell cell = row.createCell((int) j);
                        cell.setCellStyle(style2);
                        j++;
                        // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                        if (textValue != null) {
                            Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                            Matcher matcher = p.matcher(textValue);
                            if (matcher.matches()) {
                                // 是数字当作double处理
                                cell.setCellValue(Double.parseDouble(textValue));
                            } else if (!isList) {
                                HSSFRichTextString richString = new HSSFRichTextString(textValue);

                                richString.applyFont(font);
                                cell.setCellValue(richString);
                            }
                        }
                    } else {
                        valueList = (List<String>) value;
                        for (String val : valueList) {
                            HSSFCell cell = row.createCell((int) j);
                            cell.setCellStyle(style2);
                            j++;
                            // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                            if (textValue != null) {
                                Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                                Matcher matcher = p.matcher(textValue);
                                if (matcher.matches()) {
                                    // 是数字当作double处理
                                    cell.setCellValue(Double.parseDouble(textValue));
                                } else {
                                    HSSFRichTextString richString = new HSSFRichTextString(val);

                                    richString.applyFont(font);
                                    cell.setCellValue(richString);
                                }
                            }
                        }
                    }
                } catch (SecurityException e) {
                    logger.error(e.getMessage(), e);
                    throw new Exception(e);
                } catch (NoSuchMethodException e) {
                    logger.error(e.getMessage(), e);
                    throw new Exception(e);
                } catch (IllegalArgumentException e) {
                    logger.error(e.getMessage());
                    throw new Exception(e);
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage());
                    throw new Exception(e);
                } catch (InvocationTargetException e) {
                    logger.error(e.getMessage());
                    throw new Exception(e);
                } finally {
                    try {
                        workbook.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                        throw new Exception(e);
                    }
                }
            }
        }
    }
}
