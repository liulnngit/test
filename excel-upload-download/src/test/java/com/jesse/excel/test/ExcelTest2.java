package com.jesse.excel.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import com.springmvc.pojo.LabelInfo;
import com.springmvc.pojo.ReportTemple;

public class ExcelTest2 {

	// 上传模板
	@Test
	public void testRead() throws FileNotFoundException {
		File file = new File("H:\\mytest\\20181231-G0100-181-境内汇总数据-月-人民币.xls");
		InputStream inputStream = getInputStream(file);

		Workbook workbook = null;
		InputStream fileInputStream = null;
		//模板表
		ReportTemple reportTemple = new ReportTemple();
		//指标表
		List<LabelInfo> labelInfos = new ArrayList<LabelInfo>();
		
		try {
			fileInputStream = inputStream;
			workbook = new HSSFWorkbook(fileInputStream);
			// System.out.println("sheet表的个数：" + workbook.getNumberOfSheets());
			// 获取第一个sheet内容
			Sheet sheet = workbook.getSheetAt(0);
			String tableCode = sheet.getSheetName();
			reportTemple.setTableCode(tableCode);
			
			List<String> attrs = new ArrayList<String>();
			List<String> colNames = new ArrayList<String>();
			
			//System.out.println("sheet表中的行数：" + sheet.getLastRowNum());
			for (int rowNum = 0; rowNum <= sheet.getLastRowNum();rowNum++) {
				Row row = sheet.getRow(rowNum);
				//忽略空行
				if (row == null) { continue;}
				//System.out.println("每一行中的列数：" + row.getLastCellNum());
				for (int ColumnNum = 0; ColumnNum <= row.getLastCellNum(); ColumnNum++) {
					Cell cell = row.getCell(ColumnNum);
					//忽略空列
					if (cell == null ) { continue;}
					//统统先设置为1 CELL_TYPE_STRING = 1; 字符串型数值 
					cell.setCellType(1);
					CellStyle cellStyle = cell.getCellStyle();
					short pat = cellStyle.getFillPattern();
					Color color = cellStyle.getFillForegroundColorColor();
					//System.out.println("pat:"+pat+",color:"+color);
					
					String cellVal = cell.getStringCellValue();
					System.err.println(cellVal);
					
					if(rowNum==0&&ColumnNum==0){
						//第一行第一列
						reportTemple.setTableName(cellVal);
					}
					
					if(rowNum==1){
						//第二行
						if(StringUtils.isNotBlank(cellVal)){
							String s = cellVal.split("：")[1];
							attrs.add(s);
						}
					}
					if(rowNum==3){
						//第四行
						colNames.add(cellVal);
					}
					
					//通过底色标黄的区域，来确定要打标签的区域（0未，1标） 特别注意：白色也是底色，而是无底色
					if(pat==1){
						//获取行名称  向左找行名称，向上找列名称
						int c = cell.getAddress().getColumn();
						String rowName = null;
						//找到行向前遍历列“首个”找出包含中文的表格
						for(int i = c;i>=0 ;i--){
							Cell cel = row.getCell(i);
							cel.setCellType(1);
							Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
							Matcher mat = pattern.matcher(cel.getStringCellValue());
							if(mat.find()){
								Pattern p = Pattern.compile("[0-9.]");
								Matcher m = p.matcher(cel.getStringCellValue());
								if(rowName ==null){
									rowName = m.replaceAll("").trim();
								}
							}
						}
						
						String columnName = null;
						int r = cell.getAddress().getRow();
						//找到列向上遍历列“首个”找出包含中文的表格
						for(int i = r;i>=0 ;i--){
							Row ro = sheet.getRow(i);
							Cell cel = ro.getCell(c);
							cel.setCellType(1);
							Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
							Matcher mat = pattern.matcher(cel.getStringCellValue());
							if(mat.find()){
								Pattern p = Pattern.compile("[0-9.]");
								Matcher m = p.matcher(cel.getStringCellValue());
								if(columnName == null){
									columnName = m.replaceAll("").trim();
								}
								
							}
						}
						System.out.println(rowName+"-"+columnName);
						int code =0;
						LabelInfo labelInfo = new LabelInfo();
						labelInfo.setTableCode(reportTemple.getTableCode());
						labelInfo.setLabelCode("000"+code);
						labelInfo.setLabelName(rowName+"-"+columnName);
						labelInfo.setColumnVal(String.valueOf(c));;
						labelInfo.setRowVal(String.valueOf(r));
						labelInfo.setVersion("V1");
						/*labelInfo.setStartDate(new Date(2018, 1, 1));
						labelInfo.setEndDate(new Date(2018, 12, 31));*/
						labelInfos.add(labelInfo);
						
					}
				}

			}
			/*reportTemple.setRptChannel(attrs.get(0));
			//reportTemple.setRptDate(attrs.get(1));
			reportTemple.setRptDate(new Date());
			reportTemple.setCurrencyUnit(attrs.get(2));
			reportTemple.setOrganization("平安银行");
*/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private InputStream getInputStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
