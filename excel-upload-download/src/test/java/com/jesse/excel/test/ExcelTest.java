package com.jesse.excel.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.springmvc.pojo.Cell;
import com.springmvc.pojo.LabelInfo;
import com.springmvc.pojo.ReportTemple;


public class ExcelTest {

	
	//上传模板
	@Test
	public void testRead() throws FileNotFoundException {
		InputStream inputStream = getInputStream("H:\\mytest\\20181231-G0100-181-境内汇总数据-月-人民币.xls");
		try {
			final ReportTemple reportTemple = new ReportTemple();
			final LabelInfo labelInfo = new LabelInfo();
			
			final List<String> colNames = new ArrayList<String>();
			final Map<Cell,List<BigDecimal>> values  = new HashMap<Cell,List<BigDecimal>>();
			ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, new AnalysisEventListener() {
				@Override
				public void invoke(Object o, AnalysisContext analysisContext) {
					Sheet sheet = analysisContext.getCurrentSheet();
					System.out.println("当前sheet" + analysisContext.getCurrentSheet().getSheetNo() + " 当前行："
							+ analysisContext.getCurrentRowNum() + " data:" + o);
					String tableCode = analysisContext.getCurrentSheet().getSheetName();
					if(analysisContext.getCurrentRowNum()==0){
						System.out.println(analysisContext.getCurrentSheet().getSheetName());
						List<Object> obj = (List<Object>)o;
						System.out.println(obj.get(0));
						reportTemple.setTableCode(tableCode);
						reportTemple.setTableName(obj.get(0).toString());
					}else if(analysisContext.getCurrentRowNum()==1){
						List<String> attrs = new ArrayList<String>();
						List<Object> objs = (List<Object>)o;
						for (Object object : objs) {
							if(object !=null&&StringUtils.isNotBlank(object.toString())){
								String s = object.toString().split("：")[1];
								attrs.add(s);
							}
						}
					}else if(analysisContext.getCurrentRowNum()==3){
						//列名称
						List<Object> obj = (List<Object>)o;
						for (Object object : obj) {
							if(object !=null&&StringUtils.isNotBlank(object.toString())){
								colNames.add(object.toString());
							}
						}
						
					}
					List<Object> objs = (List<Object>)o;
					if(!objs.contains("")){
						int rowNum = analysisContext.getCurrentRowNum();
						labelInfo.setTableCode(tableCode);
						//只存放值 通过HashMap 行标签——values
						System.out.println(objs.get(1).toString());
						String regEx = "[0-9.]";
						Pattern pat = Pattern.compile(regEx);
						Matcher mat = pat.matcher(objs.get(1).toString());
						String key = mat.replaceAll("").trim();
						System.out.println();
						Cell cell = new Cell();
						cell.setName(key);
						cell.setRow(String.valueOf(rowNum));
						cell.setColumn("2");
						List<BigDecimal> vals = new ArrayList<BigDecimal>();
						for (int i = 2; i < objs.size(); i++) {
							vals.add(new BigDecimal(objs.get(i).toString()));
						}
						values.put(cell, vals);
					}
				}

				@Override
				public void doAfterAllAnalysed(AnalysisContext analysisContext) {

				}
			});
			reader.read();
			System.out.println("--");
			for (String col : colNames) {
				System.err.println("col:"+col);
			}
			System.out.println(values);
			
			int code = 0;
			for (Cell cell : values.keySet()) {
				List<BigDecimal> vals = values.get(cell);
				for (int i=0;i< colNames.size();i++) {
					String labelName = cell.getName()+"-"+colNames.get(i);
					BigDecimal val = vals.get(i);
					System.err.println("label_name:"+labelName+",val:"+val);
					code++;
					labelInfo.setLabelCode("000"+code);
					labelInfo.setLabelName(labelName);
					labelInfo.setColumnVal(cell.getColumn());;
					labelInfo.setRowVal(cell.getRow());
					labelInfo.setVersion("V1");
				}
			}
			
			System.err.println(reportTemple.toString());
			System.err.println(labelInfo.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void copyFileTest() throws IOException{
		String file="H:\\mytest\\20181231-G0100-181-境内汇总数据-月-人民币.xls";
		String destDir="H:\\mytest\\dir";
		FileUtils.copyFileToDirectory(new File(file), new File(destDir));
	}

	private InputStream getInputStream(String fileName) {
		try {
			return new FileInputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
