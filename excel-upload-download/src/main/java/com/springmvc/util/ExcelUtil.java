package com.springmvc.util;

import org.springframework.web.multipart.MultipartFile;

import com.springmvc.pojo.ResultObject;

public class ExcelUtil {
	
	/**
	 * 检查上传Excel文件
	 * @param file
	 * @return
	 */
	public static ResultObject CheckExcel(MultipartFile file){
		ResultObject resultObject = new ResultObject();

        boolean isNull = file.isEmpty()||file.getSize()<=0;
        if(isNull){
            resultObject.setMessage("上传的excel文件为空");
            return resultObject;
        }

        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));

        //判断是否上传的是excel文件
        boolean isExcel = ".xls".equals(fileType) || ".xlsx".equals(fileType);
        if (!isExcel) {
            resultObject.setMessage("请上传excel文件");
            return  resultObject;
        }
        
		return resultObject;
	}
	
}
