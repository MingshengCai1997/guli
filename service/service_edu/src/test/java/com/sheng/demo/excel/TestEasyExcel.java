package com.sheng.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        // 实现excel写的操作
        // 1. 设置写入文件夹的地址和excel文件名称
        String filename = "F:\\write.xlsx";
        // 2. 调用方法写
        EasyExcel.write(filename, DemoData.class).sheet("写入方法1").doWrite(getData());

        // 3. 实现excel的读操作
        String filename2 = "F:\\write.xlsx";
        EasyExcel.read(filename2, DemoData.class, new ExcelListener()).sheet().doRead();

    }

    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            DemoData data = new DemoData();
            data.setSname("lucy" + i);
            data.setSno(i);
            list.add(data);
        }
        return list;
    }
}
