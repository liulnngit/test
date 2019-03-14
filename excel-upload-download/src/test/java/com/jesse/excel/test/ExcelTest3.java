package com.jesse.excel.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
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

public class ExcelTest3 {

	// 达标下载模板
	@Test
	public void testRead() throws FileNotFoundException {
		File file = new File("H:\\mytest\\20181231-G0100-181-境内汇总数据-月-人民币.xls");
		OutputStream out = new FileOutputStream("H:\\mytest\\20181231-G0100-181-境内汇总数据-月-人民币-out.xls");
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
					//通过底色标黄的区域，来确定要打标签的区域（0未，1标） 特别注意：白色也是底色，而是无底色
					if(pat==1){
						cell.setCellValue("001");
					}
				}

			}
			workbook.write(out);
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
