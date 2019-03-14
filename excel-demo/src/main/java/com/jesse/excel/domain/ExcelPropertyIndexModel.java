package com.jesse.excel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @ExcelProperty(value = "姓名",index = 0) 
 * 	value是表头数据，默认会写在excel的表头位置，index代表第几列。
 * @author jesse
 *
 */
public class ExcelPropertyIndexModel extends BaseRowModel {

    @ExcelProperty(value = "姓名" ,index = 0)
    private String name;

    @ExcelProperty(value = "年龄",index = 1)
    private String age;

    @ExcelProperty(value = "邮箱",index = 2)
    private String email;

    @ExcelProperty(value = "地址",index = 3)
    private String address;

    @ExcelProperty(value = "性别",index = 4)
    private String sax;

    @ExcelProperty(value = "高度",index = 5)
    private String heigh;

    @ExcelProperty(value = "备注",index = 6)
    private String last;
}

