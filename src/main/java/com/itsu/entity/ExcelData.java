package com.itsu.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ExcelData {

    @ExcelProperty("ID")
    private int id;
    @ExcelProperty("公司名")
    private String name;
    @ExcelProperty("企查查公司链接")
    private String href;
    @ExcelProperty("子公司名")
    private String sonName;
    @ExcelProperty("企查查子公司链接")
    private String sonHref;
    @ExcelProperty("孙公司名")
    private String grandsonName;
    @ExcelProperty("企查查孙公司链接")
    private String grandsonHref;
}
