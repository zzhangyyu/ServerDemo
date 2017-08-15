package com.yoler.test;


import com.yoler.util.ImportExcelUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * 测试导入Excel
 * Created by zhangyu on 2017/6/19.
 */
public class TestImportExcel {
    public static void main(String[] args) {
        try {
            // 对读取Excel表格标题测试
            InputStream is = new FileInputStream("E:\\a.xls");
            List<List<String>> result = ImportExcelUtil.importExcel(is, "xls");
            for (int i = 0; i < result.size(); i++) {
                List<String> rowContent = result.get(i);
                for (String s : rowContent) {
                    System.out.print(s);
                    System.out.print(",");
                }
                System.out.println("");
            }
        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        }
    }
}
