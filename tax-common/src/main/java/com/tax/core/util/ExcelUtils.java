package com.tax.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel工具类
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年7月27日 下午3:02:22
 * @version  v1.0
 */

public class ExcelUtils {
	
	/**
	 * 导出数据到Excel
	 * @param fileName	     		文件名,不包含后缀
	 * @param sheetName   			sheet名
	 * @param title		     		标题 
	 * @param cellTitles  			每一列的标题	
	 * @param autoSizeColumnNum    	需要自动调整列宽的列的编号(从0开始)的数组,该参数可以为null
	 * @param data		   			数据: 一行数据封装成一个ArrayList,最后将所有ArrayList封装到一个List
	 * @param response	  			HttpServletResponse对象
	 * @throws Exception 
	 */
	public static void exportExcel(String fileName, String sheetName, String title, String[] cellTitles,
			int[] autoSizeColumnNum, List<ArrayList<Object>> data, HttpServletResponse response) throws Exception {
		
		/** 创建空的工作簿 */
		XSSFWorkbook workbook = new XSSFWorkbook();
		/** 创建工作表 */
		XSSFSheet sheet = workbook.createSheet(sheetName);
		
		/** 第一行标题处理  */
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cellTitles.length-1)); 
		XSSFRow row01 = sheet.createRow(0);
		XSSFCell cell01 = row01.createCell(0);
		// 设置单元格内容
		cell01.setCellValue(title);
		// 创建单元格样式
		XSSFCellStyle cellStyle01 = createCellStyle(workbook,"Courier New",(short)18,true);
		// 设置单元格样式
		cell01.setCellStyle(cellStyle01);
		
		/** 第二行列标题的处理  */
		// 创建单元格样式
		XSSFCellStyle cellStyle02 = createCellStyle(workbook,"Courier New",(short)13,true);
		XSSFRow row02 = sheet.createRow(1);
		// for循环创建单元格并赋值和设置样式
		for (int i = 0; i < cellTitles.length; i++) {
			XSSFCell titleCell = row02.createCell(i);
			titleCell.setCellValue(cellTitles[i]);
			titleCell.setCellStyle(cellStyle02);
		}
		
		/** 中间行的处理  */
		// 创建单元格样式
		XSSFCellStyle cellStyle03 = createCellStyle(workbook,null,(short)12,false);
		// for循环处理
		for (int i = 0; i < data.size(); i++) {
			XSSFRow row = sheet.createRow(i + 2);
			for(int j = 0; j < data.get(i).size(); j++) {
				XSSFCell cell = row.createCell(j);
				Object value = data.get(i).get(j);
				cell.setCellValue(value == null ? "" : value.toString());
				cell.setCellStyle(cellStyle03);
			}
		}
		
		/** 设置自动调整列宽  */
		// 某些情况下,有的列内容太长需要自动调整列宽
		for (int i = 0; i < autoSizeColumnNum.length; i++) {
			sheet.autoSizeColumn(autoSizeColumnNum[i]);
		}
		/** 下载时文件名为中文：乱码解决 */
		// 响应的过程：
		// 服务器： utf-8|gbk --> iso8859-1    浏览器: iso8859-1 --> utf-8|gbk (firefox、chrome)
		// 服务器： gbk --> iso8859-1    浏览器: iso8859-1 --> gbk (msie)
		fileName = new String(fileName.getBytes("gbk"), "iso8859-1");
		/** 设置下载的文件名 */
		response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xlsx");
        /** 向浏览器输出Excel文件 */
		workbook.write(response.getOutputStream());
		/** 关闭工作簿 */
		workbook.close();
	}

	
	/**
	 * 创建单元格样式方法
	 * @param workbook  工作簿
	 * @param fontName  字体名字
	 * @param fontSize	字体大小
	 * @param isBlod    是否加粗
	 * @return  单元格样式XSSFCellStyle
	 */
	private static XSSFCellStyle createCellStyle(XSSFWorkbook workbook, String fontName, short fontSize,
			boolean isBlod) {
		// 通过工作簿创建样式
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		// 设置水平和垂直居中
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 通过工作簿创建字体
		XSSFFont font = workbook.createFont();
		// 设置字体
		if (null != fontName && !"".equals(fontName)) {
			font.setFontName(fontName);
		}
		// 设置字体大小
		font.setFontHeightInPoints(fontSize);
		// 设置字体是否加粗
		font.setBold(isBlod);
		// 把字体set到样式中
		cellStyle.setFont(font);

		return cellStyle;
	}
	
	
	/**
	 * 解析Excel文件的方法
	 * @param  excelFile	 Excel文件
	 * @param  fileName	  文件名
	 * @return List<ArrayList<String>>
	 * @throws IOException
	 */
	public static List<ArrayList<String>> parseExcel(File excelFile,String fileName) throws IOException{
		// 判断文件格式
		boolean isExcel03 = fileName.matches(".+\\.(?i)(xls)");//这里的(?i)代表忽略大小写
		FileInputStream fis = new FileInputStream(excelFile);
		// 读取工作簿
		Workbook wb = isExcel03 ? new HSSFWorkbook(fis) : new XSSFWorkbook(fis);
		// 读取工作表
		Sheet sheet = wb.getSheetAt(0);	
		
		List<ArrayList<String>> resultData = new ArrayList<ArrayList<String>>();	//存放返回数据
		// 开始每行的读取
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			ArrayList<String> rowData = new ArrayList<>();
			short lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				Cell cell = row.getCell(j);
				String cellValue = null;
				if(null != cell){
					switch (cell.getCellType()) {
						case Cell.CELL_TYPE_BLANK:		// 空白
							cellValue = null;
							break;
						case Cell.CELL_TYPE_STRING:		// 文本
							cellValue = cell.getStringCellValue();
							break;
						case Cell.CELL_TYPE_NUMERIC:	// 数字或者日期
							if(DateUtil.isCellDateFormatted(cell)){		// 是否是日期
								Date date = cell.getDateCellValue();
								cellValue = date == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
							} else {
								//防止变成因为数字太长变成科学计数法
								cell.setCellType(CellType.STRING);
								if(isExcel03){
									cellValue = cell.getStringCellValue();
								} else {
									BigDecimal bd = new BigDecimal(cell.getStringCellValue());
									cellValue = bd.toPlainString();
								}
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:	// 布尔型
							cellValue = String.valueOf(cell.getBooleanCellValue());
							break;
						default:
							cellValue = null;
							break;
					}
				}
				rowData.add(cellValue); // 每个单元格数据存入行数据集合
			}
			resultData.add(rowData);	// 每行数据存入返回数据集合
		}
		// 关流
		wb.close();
		fis.close();
		return resultData;
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File("c:/test2.xlsx");
		List<ArrayList<String>> list = ExcelUtils.parseExcel(file, "test.xlsx");
		System.out.println(list);
	}
}
