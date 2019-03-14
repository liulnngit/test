package com.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.pojo.LabelInfo;
import com.springmvc.pojo.ReportTemple;
import com.springmvc.service.TempleService;
import com.springmvc.util.Constant;
import com.springmvc.util.FileUtil;

/**
 * 模板的管理维护
 * @author jesse
 *
 */
@Controller
public class TempleController {

	private static Log log = LogFactory.getLog(TempleController.class);

	@Autowired
	TempleService templeService;

	@RequestMapping(value = "/toAddTemple", method = RequestMethod.POST)
	public String toAddTemple() {
		return "addTemple";
	}

	@RequestMapping("/addTemple")
	public ModelAndView addTemple(ReportTemple temple, String startTime, String endTime,
			@RequestParam("file") MultipartFile file) {

		log.info("addTemple invoked...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date startDate = sdf.parse(startTime);
			Date endDate = sdf.parse(endTime);
			temple.setStartDate(startDate);
			temple.setEndDate(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		templeService.AnalysisAndSolveExcel(temple, file);

		return new ModelAndView("index");
	}

	@RequestMapping("/getListTemple")
	public String getListTemple(Model model, Integer page) throws Exception {
		return "listTemple";
	}

	@RequestMapping(value = "/listTemple", method = RequestMethod.POST)
	public ModelAndView listTemple() {
		ModelAndView model = new ModelAndView();
		try {
			log.info("listTemple invoked......");
			String tableCode = "G0100";
			List<ReportTemple> temples = templeService.selectTempleListByTableCode(tableCode);
			model.addObject("temples", temples);
			return model;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	// 下载打标模板
	@RequestMapping("/downloadExcel")
	public void downloadExcel(Integer tableId, HttpServletResponse response) throws IOException {

		ReportTemple temple = templeService.selectTempleById(tableId);

		File file = new File(Constant.DOWNLOAD_PATH + temple.getFilePath());

		InputStream inputStream = FileUtil.getInputStream(file);

		Workbook workbook = null;
		InputStream fileInputStream = null;
		try {
			fileInputStream = inputStream;
			workbook = new HSSFWorkbook(fileInputStream);
			// System.out.println("sheet表的个数：" + workbook.getNumberOfSheets());
			// 获取第一个sheet内容
			Sheet sheet = workbook.getSheetAt(0);
			// System.out.println("sheet表中的行数：" + sheet.getLastRowNum());
			for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				// 忽略空行
				if (row == null) {
					continue;
				}
				// System.out.println("每一行中的列数：" + row.getLastCellNum());
				for (int columnNum = 0; columnNum <= row.getLastCellNum(); columnNum++) {
					Cell cell = row.getCell(columnNum);
					// 忽略空列
					if (cell == null) {
						continue;
					}
					// 统统先设置为1 CELL_TYPE_STRING = 1; 字符串型数值
					cell.setCellType(1);
					CellStyle cellStyle = cell.getCellStyle();
					short pat = cellStyle.getFillPattern();
					Color color = cellStyle.getFillForegroundColorColor();
					// 通过底色标黄的区域，来确定要打标签的区域（0未，1标） 特别注意：白色也是底色，而是无底色
					if (pat == 1) {
						LabelInfo label = templeService.findByRowCloumn(sheet.getSheetName(), temple.getVersion(),
								rowNum + 1, columnNum + 1);
						cell.setCellValue(label.getLabelCode());
					}
				}

			}
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			String filename = temple.getFilePath();
			response.addHeader("Content-Disposition",
					"attachment; filename=" + new String(filename.getBytes("gbk"), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.close();
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
}
