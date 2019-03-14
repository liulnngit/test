package com.springmvc.mapper;

import java.util.Date;
import java.util.List;

import com.springmvc.pojo.ReportTemple;

public interface ReportTempleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReportTemple record);

    int insertSelective(ReportTemple record);

    ReportTemple selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReportTemple record);

    int updateByPrimaryKey(ReportTemple record);
    
    //获取模板列表
    List<ReportTemple> selectListTemple(String tableCode);
    
    //根据文件时间获取版本号
    ReportTemple selectByDate(Date date);
    
}