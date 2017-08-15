package com.yoler.test;

import com.yoler.util.ExportExcelUtil;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 测试导出excel
 * Created by zhangyu on 2017/6/10.
 */
public class TestExportExcel {
    public static void main(String[] args) throws Exception {
        ExportExcelUtil<Student> ex = new ExportExcelUtil<Student>();
        String[] headers = {"学号", "姓名", "年龄", "性别", "出生日期"};
        List<Student> dataList = new ArrayList<Student>();
        dataList.add(new Student(10000001, "张三", 20, true, new Date()));
        dataList.add(new Student(20000002, "李四", 24, true, new Date()));
        dataList.add(new Student(30000003, "王五", 22, false, new Date()));
        OutputStream out = new FileOutputStream("E://a.xls");
        ex.exportExcel(headers, dataList, out);
        out.close();
    }


    static class Student {

        private long id;
        private String name;
        private int age;
        private boolean sex;
        private Date birthday;

        public Student() {
        }

        public Student(long id, String name, int age, boolean sex, Date birthday) {
            super();
            this.id = id;
            this.name = name;
            this.age = age;
            this.sex = sex;
            this.birthday = birthday;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean getSex() {
            return sex;
        }

        public void setSex(boolean sex) {
            this.sex = sex;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", sex=" + sex +
                    ", birthday=" + birthday +
                    '}';
        }
    }

}
