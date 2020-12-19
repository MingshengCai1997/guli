package com.sheng.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {
    // 一行一行的读取excel的内容,下面的data就是每一行的内容
    @Override
    public void invoke(DemoData data, AnalysisContext analysisContext) {
        System.out.println("*******"+data);
    }

    // 读取表头的方法
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("888888"+headMap);
    }
    // 读取完成之后的操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
