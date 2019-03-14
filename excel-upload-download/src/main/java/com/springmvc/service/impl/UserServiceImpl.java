package com.springmvc.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springmvc.mapper.UserMapper;
import com.springmvc.pojo.ResultObject;
import com.springmvc.pojo.User;
import com.springmvc.service.UserService;

/**
 * ClassName:UserServiceImpl
 * Package:com.xincheng.excelupload.service.impl
 * Description:
 *
 * @Date:2018/11/17 20:36
 * @Author: 郑军
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Log logger = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    
    //@Override
    public ResultObject uploadExcel(MultipartFile file) {

        ResultObject resultObject = new ResultObject();

        /**
         * 判断文件是否为空
         */
        boolean isNull = file.isEmpty()||file.getSize()<=0;
        if(isNull){
            logger.error("上传的excel文件为空");
            resultObject.setMessage("上传的excel文件为空");

            return resultObject;
        }


        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));

        /**
         * 判断是否上传的是excel文件
         */
        boolean isExcel = ".xls".equals(fileType) || ".xlsx".equals(fileType);

        if (!isExcel) {
            resultObject.setMessage("请上传excel文件");
            return  resultObject;
        }

        /**
         * 是excel文件，进行excel文件数据的读取
         */
        Workbook workbook = null;
        InputStream fileInputStream = null;

        try {
            fileInputStream = file.getInputStream();

            if (isExcel) {
                if (".xls".equals(fileType)) {
                    workbook = new HSSFWorkbook(fileInputStream);
                } else if (".xlsx".equals(fileType)) {
                    workbook = new XSSFWorkbook(fileInputStream);
                }

            }

            logger.info("sheet表的个数："+workbook.getNumberOfSheets());

            for (int i = 0; i < workbook.getNumberOfSheets() ; i++) {

                Sheet sheet = workbook.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }

                List<User> userList = new ArrayList<User>();
                logger.info("sheet表中的行数："+sheet.getLastRowNum());

                for (int j = 1; j <= sheet.getLastRowNum() ; j++) {
                    Row row = sheet.getRow(j);

                    if (row == null) {
                        continue;
                    }

                    logger.info("每一行中的列数："+row.getLastCellNum());


                    /**
                     *  给excel单元格中的数据，设定数值类型，
                     *  int CELL_TYPE_NUMERIC = 0; 数值型数据
                     *  int CELL_TYPE_STRING = 1;   字符串型数值
                     *  int CELL_TYPE_FORMULA = 2;  公式
                     *  int CELL_TYPE_BLANK = 3;    空白
                     *  int CELL_TYPE_BOOLEAN = 4;  boolean
                     *  int CELL_TYPE_ERROR = 5;    错误
                     */
                    row.getCell(0).setCellType(1);
                    System.err.println(row.getCell(0).getCellStyle().getFillBackgroundColor());
                    // 因为单元格第一列中数据是 在excel表中填写的数值样式，
                    // 在以String形式读取的时候，会出现错误，所以需要设定 单元格数据格式为 1
                    String id = row.getCell(0).getStringCellValue();
                    String name = row.getCell(1).getStringCellValue();
                    Integer age = (int)row.getCell(2).getNumericCellValue();
                    Date date = row.getCell(3).getDateCellValue();

                    User user = new User();
                    user.setId(id);
                    user.setAge(age);
                    user.setName(name);
                    user.setDate(date);

                    userList.add(user);
                }
                for (User user : userList) {
					System.out.println(user.getName());
				}
                //userMapper.insertUserExcel(userList);
                resultObject.setMessage("上传文件成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("异常情况："+e.getMessage());
        } finally {

            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return resultObject;
    }

	//@Override
	public ResultObject uploadEasyExcel(MultipartFile file) {
		 ResultObject resultObject = new ResultObject();
	        /**
	         * 判断文件是否为空
	         */
	        boolean isNull = file.isEmpty()||file.getSize()<=0;
	        if(isNull){
	            logger.error("上传的excel文件为空");
	            resultObject.setMessage("上传的excel文件为空");
	            return resultObject;
	        }

	        String originalFilename = file.getOriginalFilename();
	        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
	        /**
	         * 判断是否上传的是excel文件
	         */
	        boolean isExcel = ".xls".equals(fileType) || ".xlsx".equals(fileType);

	        if (!isExcel) {
	            resultObject.setMessage("请上传excel文件");
	            return  resultObject;
	        }
	        
	        InputStream inputStream = null;
	        final List<User> userList = new ArrayList<User>();
			try {
				inputStream = file.getInputStream();
				// 解析每行结果在listener中处理
				ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, new AnalysisEventListener() {
					@Override
					public void invoke(Object o, AnalysisContext analysisContext) {
						System.out.println("当前sheet" + analysisContext.getCurrentSheet().getSheetNo() + " 当前行："
								+ analysisContext.getCurrentRowNum() + " data:" + o);
						if(analysisContext.getCurrentRowNum()!=0){
							List<Object> objs = (List<Object>) o;
							User user = new User();
							 user.setId(String.valueOf(objs.get(0)));
							 user.setName(String.valueOf(objs.get(1)));
		                     user.setAge(Integer.valueOf(objs.get(2).toString()));
		                     
		                     /*try {
								user.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2019-05-21"));
							} catch (ParseException e) {
								e.printStackTrace();
							}*/
		                     userList.add(user);
						}
						
						
					}

					@Override
					public void doAfterAllAnalysed(AnalysisContext analysisContext) {

					}
				});
				for (User user : userList) {
					System.out.println(user.getName());
				}
                userMapper.insertUserExcel(userList);
                resultObject.setMessage("上传文件成功");
				reader.read();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	        return resultObject;
	}

}
