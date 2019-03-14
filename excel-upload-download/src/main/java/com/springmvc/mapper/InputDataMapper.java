package com.springmvc.mapper;

import java.util.List;

import com.springmvc.pojo.InputData;

public interface InputDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InputData record);

    int insertSelective(InputData record);

    InputData selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InputData record);

    int updateByPrimaryKey(InputData record);
    
    //
    void insertDataList(List<InputData> datas);
}