package com.springmvc.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springmvc.mapper.LabelInfoMapper;
import com.springmvc.mapper.ReportTempleMapper;
import com.springmvc.pojo.LabelInfo;
import com.springmvc.pojo.ReportTemple;
import com.springmvc.pojo.ResultObject;
import com.springmvc.service.TempleService;
import com.springmvc.util.Constant;
import com.springmvc.util.ExcelUtil;
import com.springmvc.util.FileUtil;
import com.springmvc.util.RegExpUtil;

@Service
public class TempleServiceImpl implements TempleService {

	private static final Log logger = LogFactory.getLog(TempleServiceImpl.class);

	@Autowired
	private ReportTempleMapper reportTempleMapper;

	@Autowired
	private LabelInfoMapper labelInfoMapper;

	public LabelInfo findByRowCloumn(String tableCode, String version, int row, int column) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tableCode", tableCode);
			map.put("version", version);
			map.put("row", row);
			map.put("column", column);
			return labelInfoMapper.selectByRowColumn(map);
		} catch (Exception e) {
			e.printStackTrace();
			// 抛出自定义异常用于事务回滚
		}
		return null;

	}

	/**
	 * 根据表单号查找模板列表
	 */
	public List<ReportTemple> selectTempleListByTableCode(String tableCode) {
		try {
			return reportTempleMapper.selectListTemple(tableCode);
		} catch (Exception e) {
			e.printStackTrace();
			// 抛出自定义异常用于事务回滚
		}
		return null;
	}
	
	/**
	 * 根据Id查找对应的模板
	 */
	public ReportTemple selectTempleById(Integer tableId) {
		try {
			return reportTempleMapper.selectByPrimaryKey(tableId);
		} catch (Exception e) {
			e.printStackTrace();
			// 抛出自定义异常用于事务回滚
		}
		return null;
	}

	/**
	 * 解析处理Excel模板
	 */
	public ResultObject AnalysisAndSolveExcel(ReportTemple temple, MultipartFile file) {
		ResultObject resultObject = ExcelUtil.CheckExcel(file);
		String originalFilename = file.getOriginalFilename();
		String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
		Workbook workbook = null;
		InputStream fileInputStream = null;
		// 指标表
		List<LabelInfo> labelInfos = new ArrayList<LabelInfo>();
		int code = 0;
		try {
			fileInputStream = file.getInputStream();
			workbook = new HSSFWorkbook(fileInputStream);
			logger.debug("sheet表的个数：" + workbook.getNumberOfSheets());
			// 获取第一个sheet内容
			Sheet sheet = workbook.getSheetAt(0);

			logger.debug("sheet表中的行数：" + sheet.getLastRowNum());
			for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				// 忽略空行
				if (row == null) {
					continue;
				}
				logger.debug("每一行中的列数：" + row.getLastCellNum());
				for (int ColumnNum = 0; ColumnNum <= row.getLastCellNum(); ColumnNum++) {
					Cell cell = row.getCell(ColumnNum);
					// 忽略空列
					if (cell == null) {
						continue;
					}
					// 统统先设置为1 CELL_TYPE_STRING = 1; 字符串型数值
					cell.setCellType(1);
					CellStyle cellStyle = cell.getCellStyle();
					short pat = cellStyle.getFillPattern();
					// Color color = cellStyle.getFillForegroundColorColor();

					String cellVal = cell.getStringCellValue();
					// 通过底色标黄的区域，来确定要打标签的区域（0未，1标） 特别注意：白色也是底色，而是无底色
					if (pat == 1) {
						// 获取行名称 向左找行名称，向上找列名称
						int c = cell.getAddress().getColumn();
						String rowName = null;
						// 找到行向前遍历列“首个”找出包含中文的表格
						for (int i = c; i >= 0; i--) {
							Cell cel = row.getCell(i);
							cel.setCellType(1);
							short fillPat = cel.getCellStyle().getFillPattern();
							String cellValue = cel.getStringCellValue();
							if (fillPat == 1) {
								continue;
							}
							// 找到中文的Cell,并删除数字和特殊字符
							String exp = RegExpUtil.FindAndReplace(cellValue);
							if (rowName == null) {
								rowName = exp;
							}
						}

						String columnName = null;
						int r = cell.getAddress().getRow();
						// 找到列向上遍历列“首个”找出包含中文的表格,并且不在标注区域
						for (int i = r; i >= 0; i--) {
							Row ro = sheet.getRow(i);
							Cell cel = ro.getCell(c);
							cel.setCellType(1);
							short fillPat = cel.getCellStyle().getFillPattern();
							String cellValue = cel.getStringCellValue();
							if (fillPat == 1) {
								continue;
							}
							String exp = RegExpUtil.FindAndReplace(cellValue);
							if (columnName == null) {
								columnName = exp;
							}
						}
						logger.info(rowName + "-" + columnName);
						LabelInfo labelInfo = new LabelInfo();
						labelInfo.setTableCode(temple.getTableCode());
						labelInfo.setLabelName(rowName + "-" + columnName);
						if (StringUtils.isBlank(cellVal)) {
							labelInfo.setLabelCode(
									temple.getTableCode() + "-" + temple.getVersion() + "-000" + (code++));
						} else {
							// 如果是包含中文，说明是需要设置指标名称，并生成指标代码
							Matcher mat = RegExpUtil.MatchChinese(cellVal);
							if (mat.find()) {
								labelInfo.setLabelName(cellVal);
								labelInfo.setLabelCode(
										temple.getTableCode() + "-" + temple.getVersion() + "-000" + (code++));
							} else {
								labelInfo.setLabelCode(cellVal);
							}
						}

						labelInfo.setColumnVal(String.valueOf(c + 1));
						;
						labelInfo.setRowVal(String.valueOf(r + 1));
						labelInfo.setVersion(temple.getVersion());
						labelInfos.add(labelInfo);

					}
				}

			}

			String fileName = temple.getTableCode() + "-" + temple.getTableName() + "-" + temple.getVersion()
					+ fileType;

			temple.setFilePath(fileName);
			// 拷贝文件
			String filePath = Constant.DOWNLOAD_PATH + fileName;
			File oldFile = FileUtil.inputStreamToFile(file.getInputStream(), new File(originalFilename));
			FileUtils.copyFile(oldFile, new File(filePath));

			reportTempleMapper.insert(temple);
			labelInfoMapper.insertLabelList(labelInfos);

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
		return resultObject;
	}
	
	/**
	 * 根据日期查找对应的模板
	 */
	public ReportTemple selectTempleByDate(Date fileDate) {
		try {
			return reportTempleMapper.selectByDate(fileDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
