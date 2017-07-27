package com.tax.core.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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
	 * @param fileName	     文件名,不包含后缀
	 * @param sheetName   sheet名
	 * @param title		     标题 
	 * @param cellTitles  每一列的标题	
	 * @param data		     数据
	 * @param response	  HttpServletResponse对象
	 * @throws Exception 
	 */
	public static void exportExcel(String fileName, String sheetName, String title, String cellTitles[],
			List<ArrayList<Object>> data, HttpServletResponse response) throws Exception {
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
		XSSFCellStyle cellStyle01 = createCellStyle(workbook,"Courier New",(short)16,true);
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
		XSSFCellStyle cellStyle03 = createCellStyle(workbook,"宋体",(short)10,false);
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
}
