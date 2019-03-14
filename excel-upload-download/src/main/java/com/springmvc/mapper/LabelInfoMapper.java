package com.springmvc.mapper;

import java.util.List;
import java.util.Map;

import com.springmvc.pojo.LabelInfo;

public interface LabelInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LabelInfo record);

    int insertSelective(LabelInfo record);

    LabelInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LabelInfo record);

    int updateByPrimaryKey(LabelInfo record);
    
    LabelInfo selectByRowColumn(Map<String, Object> map);

	void insertLabelList(List<LabelInfo> labelInfos);
}