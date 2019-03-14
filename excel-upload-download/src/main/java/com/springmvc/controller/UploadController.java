package com.springmvc.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.springmvc.mapper.InputDataMapper;
import com.springmvc.pojo.InputData;
import com.springmvc.pojo.LabelInfo;
import com.springmvc.pojo.ReportTemple;
import com.springmvc.pojo.ResultObject;
import com.springmvc.service.TempleService;
import com.springmvc.util.ExcelUtil;

/**
 * 上传Excel数据
 * 
 * @author jesse
 *
 */
@Controller
public class UploadController {

	private static Log log = LogFactory.getLog(UploadController.class);

	@Autowired
	TempleService templeService;
	
	@Autowired
	InputDataMapper inputDataMapper;

	// 下载打标模板
	@RequestMapping("/dataUpload")
	public String dataUpload(MultipartFile file) throws IOException, ParseException {
		
		ResultObject resultObject = ExcelUtil.CheckExcel(file);
		log.info("dataUpload invoked..."+resultObject.getMessage());
		String originalFilename = file.getOriginalFilename();
        
        //根据文件名,以时间，获取时间，然后获取导入的模板的版本
        String tableCode="G0100";
        String fileDate = originalFilename.split("-")[0];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date fDate = sdf.parse(fileDate);
        
        ReportTemple temple = templeService.selectTempleByDate(fDate);
        String version = temple.getVersion();
        
        //遍历文件根据行列值，表号，版本获取对应标签，然后和excel值进行对应进行保存。
        
		Workbook workbook = null;
		InputStream fileInputStream = null;
		List<InputData> datas = new ArrayList<InputData>();
		try {
			fileInputStream = file.getInputStream();
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
					String cellVal = cell.getStringCellValue();
					LabelInfo label = templeService.findByRowCloumn(tableCode, version,
							rowNum + 1, columnNum + 1);
					if(label!=null){
						System.err.println(cellVal);
						InputData data = new InputData();
						data.setLabelCode(label.getLabelCode());
						data.setValue(new BigDecimal(cellVal));
						data.setVersion(version);
						data.setInputDate(fDate);
						datas.add(data);
					}
					
				}
			}
			inputDataMapper.insertDataList(datas);
			
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
		return  "success";
	}
}
