/*package com.springmvc.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.springmvc.pojo.Cell;
import com.springmvc.pojo.LabelInfo;
import com.springmvc.pojo.ReportTemple;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ExcelListener extends AnalysisEventListener {


    private List<Object>  data = new ArrayList<Object>();
    private ReportTemple reportTemple = new ReportTemple();
    private List<LabelInfo> labelInfos = new ArrayList<LabelInfo>();
    private List<String> colNames = new ArrayList<String>();
    private Map<Cell,List<BigDecimal>> values  = new HashMap<Cell,List<BigDecimal>>();
    
    @Override
	public void invoke(Object o, AnalysisContext analysisContext) {
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
			reportTemple.setRptChannel(attrs.get(0));
			//reportTemple.setRptDate(attrs.get(1));
			reportTemple.setRptDate(new Date());
			reportTemple.setCurrencyUnit(attrs.get(2));
			reportTemple.setOrganization("平安银行");
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
			//只存放值 通过HashMap 行标签——values
			String regEx = "[0-9.]";
			Pattern pat = Pattern.compile(regEx);
			Matcher mat = pat.matcher(objs.get(1).toString());
			String key = mat.replaceAll("").trim();
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

	public ReportTemple getReportTemple() {
		return reportTemple;
	}

	public void setReportTemple(ReportTemple reportTemple) {
		this.reportTemple = reportTemple;
	}

	public List<LabelInfo> getLabelInfos() {
		return labelInfos;
	}

	public void setLabelInfos(List<LabelInfo> labelInfos) {
		this.labelInfos = labelInfos;
	}

	public List<String> getColNames() {
		return colNames;
	}

	public void setColNames(List<String> colNames) {
		this.colNames = colNames;
	}

	public Map<Cell, List<BigDecimal>> getValues() {
		return values;
	}

	public void setValues(Map<Cell, List<BigDecimal>> values) {
		this.values = values;
	}
	
	
}
*/