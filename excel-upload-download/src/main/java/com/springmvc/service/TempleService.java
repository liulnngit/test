package com.springmvc.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springmvc.pojo.LabelInfo;
import com.springmvc.pojo.ReportTemple;
import com.springmvc.pojo.ResultObject;

public interface TempleService {
	
	 //解析处理Excel模板
	 ResultObject AnalysisAndSolveExcel(ReportTemple temple,MultipartFile file);
	 
	 //根据表单号，版本，行，列 查找指标
	 LabelInfo findByRowCloumn(String tableCode,String version,int row,int column);
	 
	 //根据表单号查找模板
	 List<ReportTemple> selectTempleListByTableCode(String tableCode);
	 
	 //根据ID查找模板
	 ReportTemple selectTempleById(Integer tableId);
	 
	 //根据日期查找模板
	 ReportTemple selectTempleByDate(Date fileDate);
	 
}
