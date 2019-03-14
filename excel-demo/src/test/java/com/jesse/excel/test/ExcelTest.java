package com.jesse.excel.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.jesse.excel.ExcelListener;
import com.jesse.excel.domain.ExcelPropertyIndexModel;
import com.jesse.excel.domain.ImportInfo;
import com.jesse.excel.domain.MultiLineHeadExcelModel;

public class ExcelTest {

	@Test
	public void testRead() throws FileNotFoundException {
		InputStream inputStream = getInputStream("H:\\mytest\\20181231-G0100-181-境内汇总数据-月-人民币.xls");
		try {
			
			// 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();
			
			ExcelReader reader = EasyExcelFactory.getReader(inputStream, new AnalysisEventListener() {
				@Override
				public void invoke(Object o, AnalysisContext analysisContext) {
					Sheet sheet = analysisContext.getCurrentSheet();
					System.out.println(analysisContext.getCurrentRowNum());
					System.out.println("当前sheet" + analysisContext.getCurrentSheet().getSheetNo() + " 当前行："
							+ analysisContext.getCurrentRowNum() + " data:" + o);
				}

				@Override
				public void doAfterAllAnalysed(AnalysisContext analysisContext) {
					Sheet sheet = analysisContext.getCurrentSheet();
					//sheet.setTableStyle(tableStyle);
					System.out.println(sheet.getColumnWidthMap());
					System.out.println(sheet.getTableStyle());
				}
			});
			List<Sheet> sheets = reader.getSheets();
	        System.out.println("llll****"+sheets);
	        System.out.println();
	        for (Sheet sheet:sheets) {
	            if(sheet.getSheetNo() ==1) {
	                reader.read(sheet);
	            }
	        }
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
	public void testRead2() throws FileNotFoundException {
		InputStream inputStream = getInputStream("H:\\mytest\\test.xls");
		try {
			
			ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, new AnalysisEventListener() {
				@Override
				public void invoke(Object o, AnalysisContext analysisContext) {
					Sheet sheet = analysisContext.getCurrentSheet();
					System.out.println(analysisContext.getCurrentRowNum());
					System.out.println("当前sheet" + analysisContext.getCurrentSheet().getSheetNo() + " 当前行："
							+ analysisContext.getCurrentRowNum() + " data:" + o);
				}

				@Override
				public void doAfterAllAnalysed(AnalysisContext analysisContext) {

				}
			});
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
	}
	
	
	@Test
	public void testWriter() throws FileNotFoundException {
		OutputStream out = new FileOutputStream("H:\\mytest\\test-OUT.xls");
		try {
			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS);
			// 写第一个sheet
			Sheet sheet = new Sheet(2, 3, ImportInfo.class);
			writer.write(getData(), sheet);
			for (ImportInfo in : getData()) {
				System.out.println(in.getName());
			}
			writer.finish();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<ImportInfo> getData() {
		List<ImportInfo> list = new ArrayList<ImportInfo>();
		ImportInfo info = new ImportInfo();
		info.setAge(12);
		info.setName("zhangsan");
		info.setEmail("11111@qq.com");
		ImportInfo info1 = new ImportInfo();
		info1.setAge(12);
		info1.setName("zhangsan1");
		info1.setEmail("11111@qq.com");
		ImportInfo info2 = new ImportInfo();
		info2.setAge(12);
		info2.setName("zhangsan2");
		info2.setEmail("11111@qq.com");
		list.add(info);
		list.add(info1);
		list.add(info2);
		return list;
	}

	private InputStream getInputStream(String fileName) {
		try {
			return new BufferedInputStream(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Test
    public void test1() throws FileNotFoundException {
        OutputStream out = new FileOutputStream("H:\\mytest\\test-OUT.xls");
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0,ExcelPropertyIndexModel.class);
            writer.write(getData(), sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	@Test
    public void testMulti() throws FileNotFoundException {
        OutputStream out = new FileOutputStream("H:\\mytest\\test-multi.xls");
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0,MultiLineHeadExcelModel.class);
            writer.write(getData(), sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
}
